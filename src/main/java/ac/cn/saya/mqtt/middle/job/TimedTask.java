package ac.cn.saya.mqtt.middle.job;
import ac.cn.saya.mqtt.middle.meta.Metadata;
import ac.cn.saya.mqtt.middle.repository.IotAppointmentDAO;
import ac.cn.saya.mqtt.middle.entity.IotAppointmentEntity;
import ac.cn.saya.mqtt.middle.entity.IotClientEntity;
import ac.cn.saya.mqtt.middle.entity.IotGatewayEntity;
import ac.cn.saya.mqtt.middle.tools.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @Title: 定时任务
 * @ProjectName mqtt-middle
 * @Author shmily
 * @Date: 2020/8/3 22:10
 * @Description: 定时任务 包括预约规则 和 元数据信息
 * /Users/saya/Library/Containers/com.tencent.QQMusicMac/Data/Library/Application Support/QQMusicMac/iTheme/10207
 */
@Service(value = "timedTask")
public class TimedTask {

    @Autowired
    private Metadata metadata;

    @Resource
    private IotAppointmentDAO iotAppointmentDAO;

    /**
     * @描述 刷新元数据 设备数据
     * @参数  []
     * @返回值  void
     * @创建人  shmily
     * @创建时间  2020/8/3
     * @修改人和其它信息 整点每小时刷新一次 0 0 0/1 * * ?
     */
    //@Scheduled(cron = "${backup.scheduled}")
    public void renewMetaData(){
        metadata.refresh();
    }

    /**
     * @描述 发送预约指令到设备
     * @参数  []
     * @返回值  void
     * @创建人  shmily
     * @创建时间  2020/8/3
     * @修改人和其它信息 每隔15分钟检查下发一次 0 0/30 * * * ?
     */
    //@Scheduled(cron = "${backup.scheduled}")
    public void sendAppointmentCommand(){
        LocalDateTime beginTime = LocalDateTime.now();
        LocalDateTime endTime = beginTime.plusMinutes(30);
        List<IotAppointmentEntity> list = iotAppointmentDAO.queryEnable(beginTime.format(DateUtils.dateFormat), endTime.format(DateUtils.dateFormat));
        if (list.isEmpty()){
            return;
        }
        for (IotAppointmentEntity item:list) {
            IotClientEntity client = item.getIotClient();
            if (null == client){
                continue;
            }
            IotGatewayEntity gateway = client.getGateway();
            if (null == gateway){
                continue;
            }
            // 校验网关是否在线
            if (!metadata.isOnlineGateway(gateway.getCode())){
                continue;
            }
            // 校验设备是否处于启用中
            if (client.getEnable() == 2 || client.getRemove() == 2 || gateway.getRemove() == 2){
                continue;
            }
            // 发送指令
        }
    }

}

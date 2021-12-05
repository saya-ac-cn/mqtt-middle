package ac.cn.saya.mqtt.middle.job;

import ac.cn.saya.mqtt.middle.meta.Metadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

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

    /**
     * @描述 刷新元数据 设备数据
     * @参数  []
     * @返回值  void
     * @创建人  shmily
     * @创建时间  2020/8/3
     * @修改人和其它信息 整点每小时刷新一次 0 0 0/1 * * ?
     */
    @Scheduled(cron = "${scheduled.metadata}")
    public void renewMetaData(){
        metadata.refresh();
    }

}

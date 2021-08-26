package ac.cn.saya.mqtt.middle.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @Title: IotHistoryExecuteEntity
 * @ProjectName mqtt-middle
 * @Author shmily
 * @Date: 2020/7/18 21:36
 * @Description: Iot历史执行命令
 */
@NoArgsConstructor
@Getter
@Setter
public class IotHistoryExecuteEntity extends BaseEntity{
    private static final long serialVersionUID = -625224990586671729L;
    /**
     * 序号
     */
    private Integer id;

    /**
     * 外键,终端id
     */
    private Integer clientId;

    /**
     * 命令
     */
    private Integer command;

    /**
     * 执行时间
     */
    private String excuteTime;

    /**
     * 所属设备信息，非数据库字段
     */
    private IotClientEntity iotClient;
}

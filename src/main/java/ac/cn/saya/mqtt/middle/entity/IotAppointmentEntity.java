package ac.cn.saya.mqtt.middle.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @Title: IotAppointmentEntity
 * @ProjectName mqtt-middle
 * @Author shmily
 * @Date: 2020/7/18 21:20
 * @Description: iot 终端规则预约表
 */
@NoArgsConstructor
@Getter
@Setter
public class IotAppointmentEntity extends BaseEntity{

    private static final long serialVersionUID = -8021656870371155403L;

    /**
     * 定时任务code
     */
    private String code;

    /**
     * 外键,终端id
     */
    private Integer clientId;

    /**
     * 任务名
     */
    private String name;

    /**
     * 物模型属性(外键)
     */
    private Integer abilityId;

    /**
     * 定时任务执行表达式
     */
    private String cron;

    /**
     * 命令
     */
    private Integer command;

    /**
     * 状态(1:已创建;2:已下发)
     */
    private Integer status;

    /**
     *
     */
    private String createTime;

    /**
     *
     */
    private String updateTime;

    /**
     * 所属设备信息，非数据库字段
     */
    private IotClientEntity iotClient;
}

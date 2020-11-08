package ac.cn.saya.mqtt.middle.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @Title: IotWarningRulesEntity
 * @ProjectName mqtt-middle
 * @Author shmily
 * @Date: 2020/7/18 21:40
 * @Description: Iot告警规则表
 */
@NoArgsConstructor
@Getter
@Setter
public class IotWarningRulesEntity extends BaseEntity{
    private static final long serialVersionUID = 3100385547207394513L;
    /**
     * 序号
     */
    private Integer id;

    /**
     * 告警名
     */
    private String name;

    /**
     * 外键,终端id
     */
    private Integer clientId;

    /**
     * 符号
     */
    private String symbol;

    /**
     * 阈值
     */
    private String value;

    /**
     * 是否启用
     */
    private Integer enable;

    /**
     *
     */
    private String createTime;

    /**
     * 修改时间
     */
    private String updateTime;

    /**
     * 所属设备信息，非数据库字段
     */
    private IotClientEntity iotClient;
}

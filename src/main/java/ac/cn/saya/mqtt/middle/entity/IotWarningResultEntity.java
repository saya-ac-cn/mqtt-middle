package ac.cn.saya.mqtt.middle.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @Title: IotWarningResultEntity
 * @ProjectName mqtt-middle
 * @Author shmily
 * @Date: 2020/7/18 21:38
 * @Description: Iot告警结果表
 */
@NoArgsConstructor
@Getter
@Setter
public class IotWarningResultEntity extends BaseEntity{
    private static final long serialVersionUID = -83182885226796806L;

    /**
     * 序号
     */
    private Integer id;

    /**
     * 外键,终端编号
     */
    private Integer clientId;

    /**
     * 触发规则id
     */
    private Integer ruleId;

    /**
     * 主题
     */
    private String topic;

    /**
     * 警告信息
     */
    private String content;

    /**
     * 生成时间
     */
    private String createTime;

    /**
     * 所属设备信息，非数据库字段
     */
    private IotClientEntity iotClient;

    /**
     * 触发规则。非数据库字段
     */
    private IotWarningRulesEntity iotRule;
}

package ac.cn.saya.mqtt.middle.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @Title: IotProductRulesEntity
 * @ProjectName mqtt-middle
 * @Description: TODO
 * @Author liunengkai
 * @Date: 6/5/21 21:39
 * @Description: 产品告警规则绑定表
 */

@NoArgsConstructor
@Getter
@Setter
public class IotProductRulesEntity extends BaseEntity{

    private static final long serialVersionUID = 6423083410584714078L;

    /**
     * 序号
     */
    private Integer id;

    /**
     * 产品id
     */
    private Integer productId;

    /**
     * 规则id
     */
    private Integer ruleId;

    /**
     * 设备绑定的具体规则（非数据库字段）
     */
    private IotWarningRulesEntity rule;

    /**
     *  是否启用（非数据库字段）(1:启用,2:关闭)
     */
    private Integer enable;


    public IotProductRulesEntity(Integer id, Integer productId, Integer ruleId) {
        this.id = id;
        this.productId = productId;
        this.ruleId = ruleId;
    }
}

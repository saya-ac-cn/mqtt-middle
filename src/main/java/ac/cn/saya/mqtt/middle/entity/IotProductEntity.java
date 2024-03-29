package ac.cn.saya.mqtt.middle.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * @Title: IotProductTypeEntity
 * @ProjectName mqtt-middle
 * @Description: TODO
 * @Author liunengkai
 * @Date: 7/31/21 10:12
 * @Description: iot物联网产品类型
 *
 */

@NoArgsConstructor
@Getter
@Setter
public class IotProductEntity extends BaseEntity{

    private static final long serialVersionUID = -212694266493076006L;


    /**
     * 产品id
     */
    private Integer id;

    /**
     * 产品名
     */
    private String name;

    /**
     * 产品状态（1：正常；2：删除）
     */
    private Integer status;

    /**
     * 非数据库字段（产品数据）
     */
    private List<IotAbilityEntity> properties;

    /**
     * 非数据库字段（产品告警规则）
     */
    private List<IotWarningRulesEntity> rules;
}

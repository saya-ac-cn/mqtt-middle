package ac.cn.saya.mqtt.middle.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @Title: IotCollectionEntity
 * @ProjectName mqtt-middle
 * @Author shmily
 * @Date: 2020/7/18 21:34
 * @Description: Iot采集表
 */
@NoArgsConstructor
@Getter
@Setter
public class IotCollectionEntity extends BaseEntity{

    private static final long serialVersionUID = 1994414779613387386L;

    /**
     * 序号
     */
    private Integer id;

    /**
     * 外键,终端id
     */
    private Integer clientId;

    /**
     * 物模型属性(外键)
     */
    private Integer abilityId;

    /**
     * 值
     */
    private String value;


    /**
     * 采集时间
     */
    private String collectTime;

    /**
     * 所属设备信息，非数据库字段
     */
    private IotClientEntity iotClient;

    /**
     * 所属的物模型信息，非数据字段
     */
    private IotAbilityEntity abilities;

    public IotCollectionEntity(Integer clientId, Integer abilityId, String value) {
        this.clientId = clientId;
        this.abilityId = abilityId;
        this.value = value;
    }
}

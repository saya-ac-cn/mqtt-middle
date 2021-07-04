package ac.cn.saya.mqtt.middle.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @Title: IotUnitsEntity
 * @ProjectName mqtt-middle
 * @Author shmily
 * @Date: 2020/7/18 20:55
 * @Description: iot设备认证信息
 */

@NoArgsConstructor
@Getter
@Setter
public class IotUnitsEntity extends BaseEntity{
    private static final long serialVersionUID = -1937868459071606171L;

    /**
     * 物理量唯一标识
     */
    private String symbol;

    /**
     * 物理量名
     */
    private String name;

    public IotUnitsEntity(String symbol, String name) {
        this.symbol = symbol;
        this.name = name;
    }
}

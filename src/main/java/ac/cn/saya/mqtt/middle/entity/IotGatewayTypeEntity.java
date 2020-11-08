package ac.cn.saya.mqtt.middle.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @Title: Iot_gateway_type
 * @ProjectName mqtt-middle
 * @Author shmily
 * @Date: 2020/7/18 21:17
 * @Description: iot 网关设备类型
 */
@NoArgsConstructor
@Getter
@Setter
public class IotGatewayTypeEntity extends BaseEntity{


    private static final long serialVersionUID = -9012309667725826022L;

    /**
     * 设备类型id
     */
    private Integer id;

    /**
     * 设备类型名
     */
    private String name;

}

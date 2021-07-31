package ac.cn.saya.mqtt.middle.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @Title: Iot_gateway_type
 * @ProjectName mqtt-middle
 * @Author shmily
 * @Date: 2020/7/18 21:17
 * @Description: iot 网关网关类型
 */
@NoArgsConstructor
@Getter
@Setter
public class IotGatewayTypeEntity extends BaseEntity{


    private static final long serialVersionUID = -9012309667725826022L;

    /**
     * 网关类型id
     */
    private Integer id;

    /**
     * 网关类型名
     */
    private String name;

}

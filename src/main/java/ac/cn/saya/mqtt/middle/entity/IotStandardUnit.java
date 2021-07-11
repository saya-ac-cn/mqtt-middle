package ac.cn.saya.mqtt.middle.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @Title: IotStandardUnit
 * @ProjectName mqtt-middle
 * @Description: TODO
 * @Author liunengkai
 * @Date: 7/11/21 17:13
 * @Description: 标准单位符号
 */
@Getter
@Setter
@NoArgsConstructor
public class IotStandardUnit {

    private Integer id;

    /**
     * 符号
     */
    private String name;

    /**
     * 符号
     */
    private String symbol;

}

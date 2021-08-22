package ac.cn.saya.mqtt.middle.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @Title: DataFlowEntity
 * @ProjectName mqtt-middle
 * @Description: TODO
 * @Author liunengkai
 * @Date: 7/5/21 23:56
 * @Description: 数据流
 */

@NoArgsConstructor
@Getter
@Setter
public class DataFlowEntity extends BaseEntity{

    private static final long serialVersionUID = -4236830940249221475L;

    private Integer id;

    /**
     * 字段名
     */
    private String fieldKey;

    /**
     * 字段标签
     */
    private String fieldValue;

    /**
     * 单位
     */
    private IotStandardUnit unit;

    /**
     * 类别【1：系统级（不允许删除修改），2：用户级别（可以操作）】
     */
    private Integer type;

}
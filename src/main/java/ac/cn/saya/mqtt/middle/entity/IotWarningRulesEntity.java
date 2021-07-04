package ac.cn.saya.mqtt.middle.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

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
     * 基本物理量
     */
    private String units;

    /**
     * 运算符
     */
    private String symbol;

    /**
     * 阈值1
     */
    private String value1;

    /**
     * 阈值1
     */
    private String value2;

    /**
     * 是否启用 是否启用(1:启用,2:关闭)
     */
    private Integer enable;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 修改时间
     */
    private String updateTime;

    /**
     * 物理量名称（非数据库字段）
     */
    private String unitsName;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IotWarningRulesEntity that = (IotWarningRulesEntity) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

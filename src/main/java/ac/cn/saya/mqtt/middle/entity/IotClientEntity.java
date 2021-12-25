package ac.cn.saya.mqtt.middle.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;

/**
 * @Title: IotClientEntity
 * @ProjectName mqtt-middle
 * @Author shmily
 * @Date: 2020/7/18 21:16
 * @Description: iot 终端设备表
 */
@NoArgsConstructor
@Getter
@Setter
public class IotClientEntity extends BaseEntity{

    @Serial
    private static final long serialVersionUID = 244387139326102332L;

    /**
     * 序号
     */
    private Integer id;

    /**
     * 产品id
     */
    private Integer productId;

    /**
     * 外键，关联物联网认证表uuid
     */
    private String identifyUuid;

    /**
     * 设备名
     */
    private String name;

    /**
     * 最后一次数据传送时间
     */
    private String lastLinkTime;

    /**
     * 是否禁用(1:启用;2:禁用)
     */
    private Integer enable;

    /**
     * 是否移除,1=正常;2=已移除
     */
    private Integer remove;

    /**
     * 所属用户
     */
    private String belongUser;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 修改时间
     */
    private String updateTime;

    /**
     * 非数据库字段，外键关联的产品名
     */
    private String productName;

    /**
     * 非数据库字段，外键关联的设备认证信息
     */
    private IotIdentifyEntity authenInfo;

    public IotClientEntity(Integer id) {
        this.id = id;
    }
}

package ac.cn.saya.mqtt.middle.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @Title: IotIdentifyEntity
 * @ProjectName mqtt-middle
 * @Author shmily
 * @Date: 2020/7/18 20:55
 * @Description: iot设备认证信息
 */

@NoArgsConstructor
@Getter
@Setter
public class IotIdentifyEntity extends BaseEntity{
    private static final long serialVersionUID = -5202367680250917805L;

    /**
     * 终端接入唯一id(也就是网关编码)
     */
    private String uuid;

    /**
     * 客户端连接时指定的用户名
     */
    private String username;

    /**
     * 为使用 salt 加密后的密文
     */
    private String password;

    /**
     * 加密方式
     */
    private String salt;

    /**
     * 设备是否启用(1:启用;2:禁用)
     */
    private Integer enable;

    /**
     * 是否为超级用户，用于控制 ACL，缺省为0；设置成 1 的时候为超级用户，可以跳过 ACL 检查
     */
    private Integer isSuperuser;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 修改时间
     */
    private String updateTime;

}

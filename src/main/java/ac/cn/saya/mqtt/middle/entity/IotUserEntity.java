package ac.cn.saya.mqtt.middle.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @Title: UserEntity
 * @ProjectName mqtt-middle
 * @Author shmily
 * @Date: 2018/9/19 22:54
 * @Description: 用户实体信息
 */
@NoArgsConstructor
@Getter
@Setter
public class IotUserEntity extends BaseEntity {

    private static final long serialVersionUID = 5355055389991833405L;

    /**
     *账户名
     */
    private String account;

    /**
     * 姓名
     */
    private String name;

    /**
     * 密码
     */
    private String password;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 电话
     */
    private String phone;

    /**
     * 修改时间
     */
    private String createTime;

    /**
     * 修改时间
     */
    private String updateTime;

    /**
     * 上次登录时间
     */
    private String loginTime;


}

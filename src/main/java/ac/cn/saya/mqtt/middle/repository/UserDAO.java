package ac.cn.saya.mqtt.middle.repository;

import ac.cn.saya.mqtt.middle.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Title: UserDAO
 * @ProjectName mqtt-middle
 * @Author shmily
 * @Date: 2020/11/15 20:46
 * @Description: TODO
 */
@Mapper
public interface UserDAO {

    /**
     * 查询用户信息
     * @param entity 查询条件参数
     * @return 单条用户信息
     */
    public UserEntity queryUser(UserEntity entity);

    /**
     * 修改用户信息
     * @param entity 期望修改后的信息
     * @return 修改结果
     */
    public int update(UserEntity entity);

}

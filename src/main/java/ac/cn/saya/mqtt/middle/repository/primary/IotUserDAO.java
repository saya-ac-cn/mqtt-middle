package ac.cn.saya.mqtt.middle.repository.primary;

import ac.cn.saya.mqtt.middle.entity.IotUserEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Title: UserDAO
 * @ProjectName mqtt-middle
 * @Author shmily
 * @Date: 2020/11/15 20:46
 * @Description: TODO
 */
@Mapper
public interface IotUserDAO {

    /**
     * 查询用户信息
     * @param entity 查询条件参数
     * @return 单条用户信息
     */
    public IotUserEntity queryUser(IotUserEntity entity);

    /**
     * 修改用户信息
     * @param entity 期望修改后的信息
     * @return 修改结果
     */
    public int update(IotUserEntity entity);

}

package ac.cn.saya.mqtt.middle.repository;

import ac.cn.saya.mqtt.middle.entity.IotClientRulesEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Title: IotClientRulesDAO
 * @ProjectName mqtt-middle
 * @Description: TODO
 * @Author liunengkai
 * @Date: 6/5/21 21:43
 * @Description: 设备告警规则绑定
 */

@Mapper
public interface IotClientRulesDAO {

    /**
     * 查询设备绑定的告警规则
     *
     * @param entity
     * @return 实例对象
     */
    public IotClientRulesEntity query(IotClientRulesEntity entity);

    /**
     * 分页查询设备绑定的告警规则
     *
     * @param entity
     * @return 对象列表
     */
    public List<IotClientRulesEntity> queryPage(IotClientRulesEntity entity);


    /**
     * 查询设备绑定的告警规则数量
     *
     * @param entity 实例对象
     * @return 对象列表
     */
    public Long queryCount(IotClientRulesEntity entity);

    /**
     * 绑定设备告警规则
     *
     * @param list 实例对象
     * @return 影响行数
     */
    public int insert(List<IotClientRulesEntity> list);

    /**
     * 修改设备绑定的告警规则
     *
     * @param list 实例对象
     * @return 影响行数
     */
    public int update(List<IotClientRulesEntity> list);

    /**
     * 解除绑定的告警规则
     *
     * @param list 主键
     * @return 影响行数
     */
    public int deleteById(List<Integer> list);

}

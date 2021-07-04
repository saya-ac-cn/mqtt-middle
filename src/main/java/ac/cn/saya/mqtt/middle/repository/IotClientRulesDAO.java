package ac.cn.saya.mqtt.middle.repository;

import ac.cn.saya.mqtt.middle.entity.IotClientRulesEntity;
import ac.cn.saya.mqtt.middle.entity.IotWarningRulesEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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
     * 查询指定设备关联的规则列表
     * @param clientId
     * @param enable
     * @return
     */
    public List<IotWarningRulesEntity> queryByClient(@Param("clientId") int clientId,@Param("enable") Integer enable);

    /**
     * 查询指定规则关联的设备列表
     * @param ruleId
     * @return
     */
    public List<Integer> queryByRule(int ruleId);

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
     * @param clientId 终端id
     * @param list 规则id
     * @return 影响行数
     */
    public void insert(@Param("clientId") int clientId, @Param("list") List<Integer> list);

    /**
     * 修改设备绑定的告警规则
     *
     * @param entity 实例对象
     * @return 影响行数
     */
    public int update(IotClientRulesEntity entity);

    /**
     * 解除绑定的告警规则
     *
     * @param list 主键
     * @return 影响行数
     */
    public int deleteById(List<Integer> list);

    /**
     * 解除绑定的告警规则
     *
     * @param ruleId 规则id
     * @return 影响行数
     */
    public int deleteByRule(@Param("ruleId") Integer ruleId);

}

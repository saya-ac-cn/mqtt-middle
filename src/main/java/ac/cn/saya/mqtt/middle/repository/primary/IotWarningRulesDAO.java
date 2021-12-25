package ac.cn.saya.mqtt.middle.repository.primary;

import ac.cn.saya.mqtt.middle.entity.IotWarningRulesEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Iot告警规则表(IotWarningRules)表数据库访问层
 *
 * @author saya
 * @since 2020-07-26 09:26:34
 */
@Mapper
public interface IotWarningRulesDAO {


    public List<IotWarningRulesEntity> selector();

    /**
     * 查询Iot告警规则
     *
     * @param entity
     * @return 实例对象
     */
    public IotWarningRulesEntity query(IotWarningRulesEntity entity);

    /**
     * 分页查询Iot告警规则
     *
     * @param entity
     * @return 对象列表
     */
    public List<IotWarningRulesEntity> queryPage(IotWarningRulesEntity entity);


    /**
     * 查询Iot告警规则数量
     *
     * @param entity 实例对象
     * @return 对象列表
     */
    public Long queryCount(IotWarningRulesEntity entity);

    /**
     * 新增Iot告警规则
     *
     * @param rule 实例对象
     * @return 影响行数
     */
    public int insert(IotWarningRulesEntity rule);

    /**
     * 修改告警规则
     *
     * @param rule 实例对象
     * @return 影响行数
     */
    public int update(IotWarningRulesEntity rule);

    /**
     * 删除告警规则
     *
     * @param ruleId 主键
     * @return 影响行数
     */
    public int deleteById(@Param("ruleId") int ruleId);

}
package ac.cn.saya.mqtt.middle.repository;

import ac.cn.saya.mqtt.middle.entity.IotWarningResultEntity;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

/**
 * Iot告警结果表(IotWarningResult)表数据库访问层
 *
 * @author saya
 * @since 2020-07-26 10:07:57
 */
@Mapper
public interface IotWarningResultDAO {

    /**
     * 查询Iot告警结果
     *
     * @param entity
     * @return 实例对象
     */
    public IotWarningResultEntity query(IotWarningResultEntity entity);

    /**
     * 分页查询Iot告警结果
     *
     * @param entity
     * @return 对象列表
     */
    public List<IotWarningResultEntity> queryPage(IotWarningResultEntity entity);


    /**
     * 查询Iot告警结果数量
     *
     * @param entity 实例对象
     * @return 对象列表
     */
    public Long queryCount(IotWarningResultEntity entity);

    /**
     * 新增Iot告警结果
     *
     * @param entity 实例对象
     * @return 影响行数
     */
    public int insert(IotWarningResultEntity entity);

}
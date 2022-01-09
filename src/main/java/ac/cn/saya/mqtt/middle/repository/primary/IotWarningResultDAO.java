package ac.cn.saya.mqtt.middle.repository.primary;

import ac.cn.saya.mqtt.middle.entity.IotWarningResultEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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
     * 查询最近的n条告警
     *
     * @param size 要抓取的数量
     * @return 实例对象
     */
    public List<IotWarningResultEntity> queryLatestN(@Param("size") int size);

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
    public long queryCount(IotWarningResultEntity entity);

    /**
     * 新增Iot告警结果
     *
     * @param entity 实例对象
     * @return 影响行数
     */
    public int insert(IotWarningResultEntity entity);


    /**
     * 批量写入数据
     */
    public int batchInsert(@Param("list") List<IotWarningResultEntity> list);

}
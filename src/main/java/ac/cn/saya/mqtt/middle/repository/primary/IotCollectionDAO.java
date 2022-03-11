package ac.cn.saya.mqtt.middle.repository.primary;
import ac.cn.saya.mqtt.middle.entity.IotCollectionEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Iot终端表(IotCollection)表数据库访问层
 *
 * @author saya
 * @since 2020-07-23 13:24:33
 */
@Mapper
public interface IotCollectionDAO {

    /**
     * 查询Iot采集信息
     *
     * @param entity
     * @return 实例对象
     */
    public IotCollectionEntity query(IotCollectionEntity entity);

    /**
     * 分页查询Iot采集信息
     *
     * @param entity
     * @return 对象列表
     */
    public List<IotCollectionEntity> queryPage(IotCollectionEntity entity);


    /**
     * 查询Iot采集信息数量
     *
     * @param entity 实例对象
     * @return 对象列表
     */
    public Long queryCount(IotCollectionEntity entity);

    /**
     * 新增Iot采集信息
     *
     * @param iotClient 实例对象
     * @return 影响行数
     */
    public int insert(IotCollectionEntity iotClient);

    /**
     * 批量写入数据
     * @param list
     */
    public void batchInsert(@Param("list") List<IotCollectionEntity> list);

    /**
     * 查询指定设备下各种指标的最新数据
     * @param clientId 设备id
     * @return 该设备下最新的指标数据
     */
    public List<IotCollectionEntity> queryByClientAndLatest(@Param("clientId")int clientId);

}
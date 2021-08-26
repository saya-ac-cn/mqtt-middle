package ac.cn.saya.mqtt.middle.repository;

import ac.cn.saya.mqtt.middle.entity.IotGatewayTypeEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Iot网关类型(IotClientTypeEntity)表数据库访问层
 *
 * @author saya
 * @since 2020-07-19 13:08:16
 */
@Mapper
public interface IotGatewayTypeDAO {

    /**
     * 查询网关类别
     *
     * @param IotGatewayTypeEntity 实例对象
     * @return 对象列表
     */
    public List<IotGatewayTypeEntity> query(IotGatewayTypeEntity IotGatewayTypeEntity);

    /**
     * 查询所有网关类别
     *
     * @return 对象列表
     */
    public List<IotGatewayTypeEntity> queryAll();

    /**
     * 新增网关类别
     *
     * @param IotGatewayTypeEntity 实例对象
     * @return 影响行数
     */
    public int insert(IotGatewayTypeEntity IotGatewayTypeEntity);

    /**
     * 修改网关类别
     *
     * @param IotGatewayTypeEntity 实例对象
     * @return 影响行数
     */
    public int update(IotGatewayTypeEntity IotGatewayTypeEntity);

    /**
     * 删除网关类别
     *
     * @param id 主键
     * @return 影响行数
     */
    public int delete(@Param("id") int id);

}
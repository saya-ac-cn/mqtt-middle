package ac.cn.saya.mqtt.middle.repository;

import ac.cn.saya.mqtt.middle.entity.IotGatewayTypeEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Iot设备类型(IotClientTypeEntity)表数据库访问层
 *
 * @author saya
 * @since 2020-07-19 13:08:16
 */
@Mapper
public interface IotGatewayTypeDAO {

    /**
     * 查询设备类别
     *
     * @param IotGatewayTypeEntity 实例对象
     * @return 对象列表
     */
    public List<IotGatewayTypeEntity> query(IotGatewayTypeEntity IotGatewayTypeEntity);

    /**
     * 查询所有设备类别
     *
     * @return 对象列表
     */
    public List<IotGatewayTypeEntity> queryAll();

    /**
     * 新增设备类别
     *
     * @param IotGatewayTypeEntity 实例对象
     * @return 影响行数
     */
    public int insert(IotGatewayTypeEntity IotGatewayTypeEntity);

    /**
     * 修改设备类别
     *
     * @param IotGatewayTypeEntity 实例对象
     * @return 影响行数
     */
    public int update(IotGatewayTypeEntity IotGatewayTypeEntity);

    /**
     * 删除设备类别
     *
     * @param id 主键
     * @return 影响行数
     */
    public int delete(@Param("id") int id);

}
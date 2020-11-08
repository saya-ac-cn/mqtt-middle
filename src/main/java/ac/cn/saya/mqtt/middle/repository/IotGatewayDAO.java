package ac.cn.saya.mqtt.middle.repository;

import ac.cn.saya.mqtt.middle.entity.IotGatewayEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Title: IotGatewayDAO
 * @ProjectName mqtt-middle
 * @Author shmily
 * @Date: 2020/7/19 10:56
 * @Description:
 */
@Mapper
public interface IotGatewayDAO {

    /**
     * 添加网关信息
     * @param entity
     * @return
     */
    public Integer insert(IotGatewayEntity entity);

    /**
     * 修改网关信息
     * @param entity
     * @return
     */
    public Integer update(IotGatewayEntity entity);

    /**
     * 获取网关详细信息
     * @param entity
     * @return
     */
    public IotGatewayEntity query(IotGatewayEntity entity);

    /**
     * 删除网关信息
     * @param id
     * @return
     */
    public Integer delete(Integer id);

    /**
     * 分页查询网关信息
     * @param entity
     * @return
     */
    public List<IotGatewayEntity> queryPage(IotGatewayEntity entity);

    /**
     * 查询网关信息总数
     * @param entity
     * @return
     */
    public Long queryCount(IotGatewayEntity entity);

    /**
     * 获取网关下拉列表
     * @param entity
     * @return
     */
    public List<IotGatewayEntity> queryList(IotGatewayEntity entity);

}

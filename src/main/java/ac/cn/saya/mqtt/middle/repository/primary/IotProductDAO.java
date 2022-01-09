package ac.cn.saya.mqtt.middle.repository.primary;

import ac.cn.saya.mqtt.middle.entity.IotProductEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @Title: IotProductTypeDAO
 * @ProjectName mqtt-middle
 * @Description: TODO
 * @Author liunengkai
 * @Date: 8/14/21 18:47
 * @Description: 产品类型
 */
@Mapper
public interface IotProductDAO {

    /**
     * 获取产品下拉列表
     * @param entity
     * @return
     */
    public List<IotProductEntity> queryList(IotProductEntity entity);

    /**
     * 获取产品物模型详情
     * @param entity
     * @return
     */
    public List<IotProductEntity> queryProductProperties(IotProductEntity entity);

    /**
     * 获取产品物模型详情
     * @param entity
     * @return
     */
    public List<IotProductEntity> queryProductRules(IotProductEntity entity);

    /**
     * 添加产品信息
     * @param entity
     * @return
     */
    public Integer insert(IotProductEntity entity);

    /**
     * 修改产品信息
     * @param entity
     * @return
     */
    public Integer update(IotProductEntity entity);

    /**
     * 删除产品信息
     * @param id
     * @return
     */
    public Integer delete(Integer id);

    /**
     * 分页查询产品信息
     * @param entity
     * @return
     */
    public List<IotProductEntity> queryPage(IotProductEntity entity);

    /**
     * 查询产品信息总数
     * @param entity
     * @return
     */
    public Long queryCount(IotProductEntity entity);

    /**
     * 统计各个产品下的设备数量
     * @return
     */
    public List<Map> totalProductClient();

}

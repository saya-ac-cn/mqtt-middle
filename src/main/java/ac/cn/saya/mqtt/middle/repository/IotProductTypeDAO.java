package ac.cn.saya.mqtt.middle.repository;

import ac.cn.saya.mqtt.middle.entity.IotProductTypeEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Title: IotProductTypeDAO
 * @ProjectName mqtt-middle
 * @Description: TODO
 * @Author liunengkai
 * @Date: 8/14/21 18:47
 * @Description: 产品类型
 */
@Mapper
public interface IotProductTypeDAO {

    /**
     * 获取产品下拉列表
     * @param entity
     * @return
     */
    public List<IotProductTypeEntity> queryList(IotProductTypeEntity entity);

    /**
     * 获取产品物模型详情
     * @param entity
     * @return
     */
    public List<IotProductTypeEntity> queryProductProperties(IotProductTypeEntity entity);

    /**
     * 获取产品物模型详情
     * @param entity
     * @return
     */
    public List<IotProductTypeEntity> queryProductRules(IotProductTypeEntity entity);

    /**
     * 添加产品信息
     * @param entity
     * @return
     */
    public Integer insert(IotProductTypeEntity entity);

    /**
     * 修改产品信息
     * @param entity
     * @return
     */
    public Integer update(IotProductTypeEntity entity);

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
    public List<IotProductTypeEntity> queryPage(IotProductTypeEntity entity);

    /**
     * 查询产品信息总数
     * @param entity
     * @return
     */
    public Long queryCount(IotProductTypeEntity entity);

}

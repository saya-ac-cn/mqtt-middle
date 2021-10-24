package ac.cn.saya.mqtt.middle.repository;

import ac.cn.saya.mqtt.middle.entity.IotAbilityEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Title: IotAbilityDAO
 * @ProjectName mqtt-middle
 * @Description: TODO
 * @Author liunengkai
 * @Date: 8/14/21 18:55
 * @Description: 物模型属性
 */
@Mapper
public interface IotAbilityDAO {

    /**
     * 批量添加物模型属性信息
     * @param list
     * @return
     */
    public void batchInsert(@Param("list") List<IotAbilityEntity> list);

    /**
     * 修改物模型属性信息
     * @param entity
     * @return
     */
    public Integer update(IotAbilityEntity entity);

    /**
     * 删除物模型属性信息
     * @param id
     * @return
     */
    public Integer delete(Integer id);

    /**
     * 根据产品id查看相关的属性
     * @param productId
     * @return
     */
    public List<IotAbilityEntity> queryAbilityByProductId(@Param("productId") int productId);


}

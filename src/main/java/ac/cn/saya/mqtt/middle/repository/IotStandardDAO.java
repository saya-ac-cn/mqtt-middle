package ac.cn.saya.mqtt.middle.repository;

import ac.cn.saya.mqtt.middle.entity.IotStandardUnitEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Title: IotStandardDAO
 * @ProjectName mqtt-middle
 * @Description: TODO
 * @Author liunengkai
 * @Date: 10/19/21 22:42
 * @Description:标准物理量
 */

@Mapper
public interface IotStandardDAO {

    /**
     * 查询所有的物理量
     * @return
     */
    public List<IotStandardUnitEntity> findAll();

}

package ac.cn.saya.mqtt.middle.repository.primary;

import ac.cn.saya.mqtt.middle.entity.IotHistoryExecuteEntity;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

/**
 * Iot历史执行命令(IotHistoryExecute)表数据库访问层
 *
 * @author saya
 * @since 2020-07-26 09:10:10
 */
@Mapper
public interface IotHistoryExecuteDAO {

    /**
     * 查询Iot历史执行命令
     *
     * @param entity
     * @return 实例对象
     */
    public IotHistoryExecuteEntity query(IotHistoryExecuteEntity entity);

    /**
     * 分页查询Iot历史执行命令
     *
     * @param entity
     * @return 对象列表
     */
    public List<IotHistoryExecuteEntity> queryPage(IotHistoryExecuteEntity entity);


    /**
     * 查询Iot历史执行命令数量
     *
     * @param entity 实例对象
     * @return 对象列表
     */
    public Long queryCount(IotHistoryExecuteEntity entity);

    /**
     * 新增Iot历史执行命令
     *
     * @param entity 实例对象
     * @return 影响行数
     */
    public int insert(IotHistoryExecuteEntity entity);

}
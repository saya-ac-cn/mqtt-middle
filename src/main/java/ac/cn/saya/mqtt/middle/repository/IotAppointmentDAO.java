package ac.cn.saya.mqtt.middle.repository;

import ac.cn.saya.mqtt.middle.entity.IotAppointmentEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * Iot预约表(IotAppointment)表数据库访问层
 *
 * @author shmily
 * @since 2020-08-02 09:53:02
 */
@Mapper
public interface IotAppointmentDAO {

    /**
     * 查询Iot预约
     *
     * @param entity
     * @return 实例对象
     */
    public IotAppointmentEntity query(IotAppointmentEntity entity);

    /**
     * 分页查询Iot预约
     *
     * @param entity
     * @return 对象列表
     */
    public List<IotAppointmentEntity> queryPage(IotAppointmentEntity entity);


    /**
     * 查询Iot预约总数
     *
     * @param entity 实例对象
     * @return 对象列表
     */
    public Long queryCount(IotAppointmentEntity entity);

    /**
     * 新增Iot预约
     *
     * @param iotClient 实例对象
     * @return 影响行数
     */
    public int insert(IotAppointmentEntity iotClient);

    /**
     * 修改预约
     *
     * @param iotWarningRules 实例对象
     * @return 影响行数
     */
    public int update(IotAppointmentEntity iotWarningRules);

    /**
     * 删除预约
     *
     * @param id 主键
     * @return 影响行数
     */
    public int deleteById(@Param(value = "id") Integer id);

    /**
     * 查询即将要下发的指令
     *
     * @param beginTime 开始时间
     * @param endTime 开始时间
     * @return 对象列表
     */
    public List<IotAppointmentEntity> queryEnable(@Param("beginTime") String beginTime,@Param("endTime") String endTime);

}
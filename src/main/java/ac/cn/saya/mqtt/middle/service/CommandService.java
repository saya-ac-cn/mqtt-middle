package ac.cn.saya.mqtt.middle.service;

import ac.cn.saya.mqtt.middle.entity.IotAppointmentEntity;
import ac.cn.saya.mqtt.middle.entity.IotHistoryExecuteEntity;
import ac.cn.saya.mqtt.middle.tools.Result;

/**
 * @Title: CommandService
 * @ProjectName mqtt-middle
 * @Description: TODO
 * @Author shmily
 * @Date: 2020/8/1 16:56
 * @Description: 指令相关业务集，数据方向-> 下行到终端设备
 */

public interface CommandService {

    /**
     * @描述 分页查看指令下发历史
     * @参数  [entity]
     * @返回值  ac.cn.saya.mqtt.middle.tools.Result<java.lang.Object>
     * @创建人  shmily
     * @创建时间  2020/8/1
     * @修改人和其它信息
     */
    public Result<Object> getIotHistoryExecutePage(IotHistoryExecuteEntity entity);

    /**
     * @描述 添加终端规则预约信息
     * @参数
     * @返回值
     * @创建人  shmily
     * @创建时间  2020/7/29
     * @修改人和其它信息
     */
    public Result<Integer> addIotAppointment(IotAppointmentEntity entity);

    /**
     * @描述 修改终端规则预约信息
     * @参数
     * @返回值
     * @创建人  shmily
     * @创建时间  2020/7/29
     * @修改人和其它信息
     */
    public Result<Integer> editIotAppointment(IotAppointmentEntity entity);

    /**
     * @描述 删除终端规则预约信息
     * @参数  [code]
     * @返回值  ac.cn.saya.mqtt.middle.tools.Result<java.lang.Integer>
     * @创建人  shmily
     * @创建时间  2020/8/1
     * @修改人和其它信息
     */
    public Result<Integer> deleteIotAppointment(String code);

    /**
     * @描述 终端规则预约信息分页
     * @参数  [entity]
     * @返回值  ac.cn.saya.mqtt.middle.tools.Result<java.lang.Object>
     * @创建人  shmily
     * @创建时间  2020/8/1
     * @修改人和其它信息
     */
    public Result<Object> getIotAppointmentPage(IotAppointmentEntity entity);

}

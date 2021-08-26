package ac.cn.saya.mqtt.middle.service;

import ac.cn.saya.mqtt.middle.entity.IotClientEntity;
import ac.cn.saya.mqtt.middle.entity.IotGatewayEntity;
import ac.cn.saya.mqtt.middle.entity.IotGatewayTypeEntity;
import ac.cn.saya.mqtt.middle.tools.Result;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Title: DeviceService
 * @ProjectName mqtt-middle
 * @Description: TODO
 * @Author shmily
 * @Date: 2020/7/29 20:43
 * @Description: 设备相关操作接口
 */

public interface DeviceService {

    /**
     * @描述 获取Iot设备类型
     * @参数
     * @返回值 IotGatewayTypeEntity 集合
     * @创建人  shmily
     * @创建时间  2020/7/29
     * @修改人和其它信息
     */
    public Result<List<IotGatewayTypeEntity>> getIotGatewayType();

    /**
     * @描述 添加网关
     * @参数
     * @返回值
     * @创建人  shmily
     * @创建时间  2020/7/29
     * @修改人和其它信息
     */
    public Result<Integer> addIotGateway(IotGatewayEntity entity, HttpServletRequest request);

    /**
     * @描述 修改网关
     * @参数
     * @返回值
     * @创建人  shmily
     * @创建时间  2020/7/29
     * @修改人和其它信息
     */
    public Result<Integer> editIotGateway(IotGatewayEntity entity,HttpServletRequest request);

    /**
     * @描述 删除网关信息
     * @参数  [id]
     * @返回值  ac.cn.saya.mqtt.middle.tools.Result<java.lang.Integer>
     * @创建人  shmily
     * @创建时间  2020/8/1
     * @修改人和其它信息
     */
    public Result<Integer> deleteIotGateway(Integer id);

    /**
     * @描述 网关分页
     * @参数  [entity]
     * @返回值  ac.cn.saya.mqtt.middle.tools.Result<java.lang.Object>
     * @创建人  shmily
     * @创建时间  2020/8/1
     * @修改人和其它信息
     */
    public Result<Object> getIotGatewayPage(IotGatewayEntity entity);

    /**
     * @描述 获取单个网关详情
     * @参数  [id]
     * @返回值  ac.cn.saya.mqtt.middle.tools.Result<ac.cn.saya.mqtt.middle.entity.IotGatewayEntity>
     * @创建人  shmily
     * @创建时间  2020/8/23
     * @修改人和其它信息
     */
    public Result<IotGatewayEntity> getIotGatewayEntity(Integer id);

    /**
     * @描述 获取网关列表-用于添加设备时的下拉选框
     * @参数  [entity]
     * @返回值  ac.cn.saya.mqtt.middle.tools.Result<java.util.List<ac.cn.saya.mqtt.middle.entity.IotGatewayEntity>>
     * @创建人  shmily
     * @创建时间  2020/8/23
     * @修改人和其它信息
     */
    public Result<List<IotGatewayEntity>> getIotGatewayList(HttpServletRequest request);


    /**
     * @描述 添加设备
     * @参数  [entity]
     * @返回值  ac.cn.saya.mqtt.middle.tools.Result<java.lang.Integer>
     * @创建人  shmily
     * @创建时间  2020/8/1
     * @修改人和其它信息
     */
    public Result<Integer> addIotClient(IotClientEntity entity);

    /**
     * @描述 修改设备
     * @参数  [entity]
     * @返回值  ac.cn.saya.mqtt.middle.tools.Result<java.lang.Integer>
     * @创建人  shmily
     * @创建时间  2020/8/1
     * @修改人和其它信息
     */
    public Result<Integer> editIotClient(IotClientEntity entity);

    /**
     * @描述 删除设备
     * @参数  [id]
     * @返回值  ac.cn.saya.mqtt.middle.tools.Result<java.lang.Integer>
     * @创建人  shmily
     * @创建时间  2020/8/1
     * @修改人和其它信息
     */
    public Result<Integer> deleteIotClient(Integer id);

    /**
     * @描述 设备分页
     * @参数  [entity]
     * @返回值  ac.cn.saya.mqtt.middle.tools.Result<java.lang.Object>
     * @创建人  shmily
     * @创建时间  2020/8/1
     * @修改人和其它信息
     */
    public Result<Object> getIotClientPage(IotClientEntity entity);

    /**
     * @描述 下拉列表显示Iot终端
     * @参数  [request, keyWord]
     * @返回值  ac.cn.saya.mqtt.middle.tools.Result<java.util.List<ac.cn.saya.mqtt.middle.entity.IotClientEntity>>
     * @创建人  shmily
     * @创建时间  2020/9/20
     * @修改人和其它信息
     * source 所属用户
     * keyWord 通过关键词，按照网关名或者设备名检索
     */
    public Result<List<IotClientEntity>> getClientSelectList(HttpServletRequest request,String keyWord);

}

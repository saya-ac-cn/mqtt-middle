package ac.cn.saya.mqtt.middle.service;

import ac.cn.saya.mqtt.middle.entity.*;
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
     * @Title   获取iot产品列表
     * @Params  [param]
     * @Return  ac.cn.saya.mqtt.middle.tools.Result<java.util.List<ac.cn.saya.mqtt.middle.entity.IotProductTypeEntity>>
     * @Author  saya.ac.cn-刘能凯
     * @Date  2021/8/22
     * @Description
     */
    public Result<List<IotProductTypeEntity>> getIotProduct(IotProductTypeEntity param);

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
     * 查看指定网关下可用的设备序号
     * @param  gatewayId 网关
     * @return  序号map
     * @author  saya.ac.cn-刘能凯
     * @date  9/20/21
     * @description
     */
    public Result<Object> getAvailableSerialNum(Integer gatewayId);

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

    /**
     * @描述 创建iot产品
     * @参数  [entity]
     * @返回值  ac.cn.saya.mqtt.middle.tools.Result<java.lang.Integer>
     * @创建人  shmily
     * @创建时间  2020/8/1
     * @修改人和其它信息
     */
    public Result<Integer> addIotProduct(IotProductTypeEntity entity);

    /**
     * @描述 修改iot产品
     * @参数  [entity]
     * @返回值  ac.cn.saya.mqtt.middle.tools.Result<java.lang.Integer>
     * @创建人  shmily
     * @创建时间  2020/8/1
     * @修改人和其它信息
     */
    public Result<Integer> editIotProduct(IotProductTypeEntity entity);

    /**
     * @描述 删除iot产品
     * @参数  [id]
     * @返回值  ac.cn.saya.mqtt.middle.tools.Result<java.lang.Integer>
     * @创建人  shmily
     * @创建时间  2020/8/1
     * @修改人和其它信息
     */
    public Result<Integer> deleteIotProduct(Integer id);

    /**
     * @描述 产品分页
     * @参数  [entity]
     * @返回值  ac.cn.saya.mqtt.middle.tools.Result<java.lang.Object>
     * @创建人  shmily
     * @创建时间  2020/8/1
     * @修改人和其它信息
     */
    public Result<Object> getIotProductPage(IotProductTypeEntity entity);

    /**
     * @描述 添加iot产品物模型
     * @参数  [entity]
     * @返回值  ac.cn.saya.mqtt.middle.tools.Result<java.lang.Integer>
     * @创建人  shmily
     * @创建时间  2020/8/1
     * @修改人和其它信息
     */
    public Result<Integer> addIotProductAbility(List<IotAbilityEntity> entities);

    /**
     * @描述 修改iot产品物模型
     * @参数  [entity]
     * @返回值  ac.cn.saya.mqtt.middle.tools.Result<java.lang.Integer>
     * @创建人  shmily
     * @创建时间  2020/8/1
     * @修改人和其它信息
     */
    public Result<Integer> editIotProductAbility(IotAbilityEntity entity);

    /**
     * @描述 删除iot产品物模型
     * @参数  [id] 主键
     * @返回值  ac.cn.saya.mqtt.middle.tools.Result<java.lang.Integer>
     * @创建人  shmily
     * @创建时间  2020/8/1
     * @修改人和其它信息
     */
    public Result<Integer> deleteIotProductAbility(Integer id);

    /**
     * @描述 iot产品物模型列表
     * @参数  [id] 产品id
     * @返回值  ac.cn.saya.mqtt.middle.tools.Result<java.lang.Object>
     * @创建人  shmily
     * @创建时间  2020/8/1
     * @修改人和其它信息
     */
    public Result<List<IotAbilityEntity>> getIotProductAbilityPage(Integer id);

}

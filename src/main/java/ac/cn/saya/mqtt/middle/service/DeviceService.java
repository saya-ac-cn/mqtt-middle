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
     * @Title   获取iot产品列表
     * @Params  [param]
     * @Return  ac.cn.saya.mqtt.middle.tools.Result<java.util.List<ac.cn.saya.mqtt.middle.entity.IotProductTypeEntity>>
     * @Author  saya.ac.cn-刘能凯
     * @Date  2021/8/22
     * @Description
     */
    public Result<List<IotProductEntity>> getIotProduct(IotProductEntity param);

    /**
     * @Title   获取设备认证信息
     * @Params  [identifyUuid]
     * @Return  ac.cn.saya.mqtt.middle.tools.Result<ac.cn.saya.mqtt.middle.entity.IotIdentifyEntity>
     * @Author  saya.ac.cn-刘能凯
     * @Date  2021/8/22
     * @Description
     */
    public Result<IotIdentifyEntity> getIotIdentify(String identifyUuid);

    /**
     * @Title   获取iot标准物理量
     * @Params  [param]
     * @Return  ac.cn.saya.mqtt.middle.tools.Result<java.util.List<ac.cn.saya.mqtt.middle.entity.IotStandardUnitEntity>>
     * @Author  saya.ac.cn-刘能凯
     * @Date  2021/8/22
     * @Description
     */
    public Result<List<IotStandardUnitEntity>> getStandardList();

    /**
     * @描述 添加设备
     * @参数  [entity,request]
     * @返回值  ac.cn.saya.mqtt.middle.tools.Result<java.lang.Integer>
     * @创建人  shmily
     * @创建时间  2020/8/1
     * @修改人和其它信息
     */
    public Result<Integer> addIotClient(IotClientEntity entity,HttpServletRequest request);

    /**
     * @描述 修改设备
     * @参数  [entity,request]
     * @返回值  ac.cn.saya.mqtt.middle.tools.Result<java.lang.Integer>
     * @创建人  shmily
     * @创建时间  2020/8/1
     * @修改人和其它信息
     */
    public Result<Integer> editIotClient(IotClientEntity entity,HttpServletRequest request);

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
    public Result<Integer> addIotProduct(IotProductEntity entity);

    /**
     * @描述 修改iot产品
     * @参数  [entity]
     * @返回值  ac.cn.saya.mqtt.middle.tools.Result<java.lang.Integer>
     * @创建人  shmily
     * @创建时间  2020/8/1
     * @修改人和其它信息
     */
    public Result<Integer> editIotProduct(IotProductEntity entity);

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
    public Result<Object> getIotProductPage(IotProductEntity entity);

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
     * @返回值  ac.cn.saya.mqtt.middle.tools.Result<java.util.List<ac.cn.saya.mqtt.middle.entity.IotAbilityEntity>>
     * @创建人  shmily
     * @创建时间  2020/8/1
     * @修改人和其它信息
     */
    public Result<List<IotAbilityEntity>> getIotProductAbilityPage(Integer id);

}

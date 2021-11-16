package ac.cn.saya.mqtt.middle.controller;

import ac.cn.saya.mqtt.middle.entity.*;
import ac.cn.saya.mqtt.middle.service.CollectionService;
import ac.cn.saya.mqtt.middle.service.DeviceService;
import ac.cn.saya.mqtt.middle.tools.Result;
import ac.cn.saya.mqtt.middle.tools.ResultEnum;
import ac.cn.saya.mqtt.middle.tools.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Title: DeviceController
 * @ProjectName mqtt-middle
 * @Author ac.cn.saya.mqtt.middle
 * @Date: 2020/11/8 21:36
 * @Description: TODO
 */
@RestController
@RequestMapping(value = "/backend")
public class DeviceController {
    
    private final DeviceService deviceService;

    private final CollectionService collectionService;

    @Autowired
    public DeviceController(DeviceService deviceService,CollectionService collectionService) {
        this.deviceService = deviceService;
        this.collectionService = collectionService;
    }

    /**
     * @描述 获取网关设备类型
     * @参数  []
     * @返回值  ac.cn.saya.mqtt.middle.tools.Result<java.util.List<ac.cn.saya.mqtt.middle.entity.IotGatewayTypeEntity>>
     * @创建人  ac.cn.saya.mqtt.middle
     * @创建时间  2020/8/8
     * @修改人和其它信息
     */
    @GetMapping(value = "gateway/type")
    public Result<List<IotGatewayTypeEntity>> getIotGatewayType(){
        return deviceService.getIotGatewayType();
    }

    /**
     * @Title   获取产品列表
     * @Params  [param]
     * @Return  ac.cn.saya.mqtt.middle.tools.Result<java.util.List<ac.cn.saya.mqtt.middle.entity.IotProductTypeEntity>>
     * @Author  saya.ac.cn-刘能凯
     * @Date  2021/8/22
     * @Description
     */
    @GetMapping(value = "product/list")
    public Result<List<IotProductTypeEntity>> getIotGatewayType(IotProductTypeEntity param){
        return deviceService.getIotProduct(param);
    }

    /**
     * @Title   获取标准物理量
     * @Params  [param]
     * @Return  ac.cn.saya.mqtt.middle.tools.Result<java.util.List<ac.cn.saya.mqtt.middle.entity.IotStandardUnitEntity>>
     * @Author  saya.ac.cn-刘能凯
     * @Date  2021/8/22
     * @Description
     */
    @GetMapping(value = "standard")
    public Result<List<IotStandardUnitEntity>> getStandardList(){
        return deviceService.getStandardList();
    }

    /**
     * @描述 添加网关
     * @参数  [entity]
     * @返回值  ac.cn.saya.mqtt.middle.tools.Result<java.lang.Integer>
     * @创建人  ac.cn.saya.mqtt.middle
     * @创建时间  2020/8/8
     * @修改人和其它信息
     */
    @PostMapping(value = "gateway")
    public Result<Integer> addIotGateway(@RequestBody IotGatewayEntity entity, HttpServletRequest request){
        return deviceService.addIotGateway(entity,request);
    }

    /**
     * @描述 修改网关
     * @参数  [entity]
     * @返回值  ac.cn.saya.mqtt.middle.tools.Result<java.lang.Integer>
     * @创建人  ac.cn.saya.mqtt.middle
     * @创建时间  2020/8/8
     * @修改人和其它信息
     */
    @PutMapping(value = "gateway")
    public Result<Integer> editIotGateway(@RequestBody IotGatewayEntity entity,HttpServletRequest request){
        return deviceService.editIotGateway(entity,request);
    }

    /**
     * @描述 删除网关
     * @参数  [id]
     * @返回值  ac.cn.saya.mqtt.middle.tools.Result<java.lang.Integer>
     * @创建人  ac.cn.saya.mqtt.middle
     * @创建时间  2020/8/8
     * @修改人和其它信息
     */
    @DeleteMapping(value = "gateway/{id}")
    public Result<Integer> deleteIotGateway(@PathVariable(value = "id") Integer id){
        return deviceService.deleteIotGateway(id);
    }

    /**
     * @描述 网关分页
     * @参数  [entity]
     * @返回值  ac.cn.saya.mqtt.middle.tools.Result<java.lang.Object>
     * @创建人  ac.cn.saya.mqtt.middle
     * @创建时间  2020/8/8
     * @修改人和其它信息
     */
    @GetMapping(value = "gateway")
    public Result<Object> getIotGatewayPage(IotGatewayEntity entity, IotIdentifyEntity authenInfo){
        if (null != authenInfo && authenInfo.getEnable() != null && null != entity){
            entity.setAuthenInfo(authenInfo);
        }
        return deviceService.getIotGatewayPage(entity);
    }

    /**
     * @描述 查询网关详情
     * @参数  [id]
     * @返回值  ac.cn.saya.mqtt.middle.tools.Result<ac.cn.saya.lab.api.entity.IotGatewayEntity>
     * @创建人  ac.cn.saya.mqtt.middle
     * @创建时间  2020/8/23
     * @修改人和其它信息
     */
    @GetMapping(value = "gateway/{id}")
    public Result<IotGatewayEntity> getIotGatewayEntity(@PathVariable("id") Integer id){
        return deviceService.getIotGatewayEntity(id);
    }

    /**
     * @描述 获取网关下拉列表
     * @参数  [entity]
     * @返回值  ac.cn.saya.mqtt.middle.tools.Result<java.util.List< ac.cn.saya.mqtt.middle.entity.IotGatewayEntity>>
     * @创建人  ac.cn.saya.mqtt.middle
     * @创建时间  2020/8/23
     * @修改人和其它信息
     */
    @GetMapping(value = "gatewayList")
    public Result<List<IotGatewayEntity>> getIotGatewayList(HttpServletRequest request){
        return deviceService.getIotGatewayList(request);
    }

    /**
     * 查看指定网关下的设备序号
     * @param  gatewayId 网关id
     * @return  设备序号
     * @author  saya.ac.cn-刘能凯
     * @date  9/20/21
     * @description
     */
    @GetMapping(value = "client/serialNum/{gatewayId}")
    public Result<Object> getAvailableSerialNum(@PathVariable(value = "gatewayId") Integer gatewayId){
        return deviceService.getAvailableSerialNum(gatewayId);
    }

    /**
     * @描述 添加设备
     * @参数  [entity]
     * @返回值  ac.cn.saya.mqtt.middle.tools.Result<java.lang.Integer>
     * @创建人  ac.cn.saya.mqtt.middle
     * @创建时间  2020/8/8
     * @修改人和其它信息
     */
    @PostMapping(value = "client")
    public Result<Integer> addIotClient(@RequestBody IotClientEntity entity){
        return deviceService.addIotClient(entity);
    }

    /**
     * @描述 修改设备
     * @参数  [entity]
     * @返回值  ac.cn.saya.mqtt.middle.tools.Result<java.lang.Integer>
     * @创建人  ac.cn.saya.mqtt.middle
     * @创建时间  2020/8/8
     * @修改人和其它信息
     */
    @PutMapping(value = "client")
    public Result<Integer> editIotClient(@RequestBody IotClientEntity entity){
        return deviceService.editIotClient(entity);
    }

    /**
     * @描述 删除设备
     * @参数  [id]
     * @返回值  ac.cn.saya.mqtt.middle.tools.Result<java.lang.Integer>
     * @创建人  ac.cn.saya.mqtt.middle
     * @创建时间  2020/8/8
     * @修改人和其它信息
     */
    @DeleteMapping(value = "client/{id}")
    public Result<Integer> deleteIotClient(@PathVariable(value = "id") Integer id){
        return deviceService.deleteIotClient(id);
    }

    /**
     * @描述 设备分页
     * @参数  [entity]
     * @返回值  ac.cn.saya.mqtt.middle.tools.Result<java.lang.Object>
     * @创建人  ac.cn.saya.mqtt.middle
     * @创建时间  2020/8/8
     * @修改人和其它信息
     */
    @GetMapping(value = "client")
    public Result<Object> getIotClientPage(IotClientEntity entity){
        return deviceService.getIotClientPage(entity);
    }

    /**
     * @描述 下拉列表显示Iot终端
     * @参数  [keyWord, request]
     * @返回值  ac.cn.saya.mqtt.middle.tools.Result<java.util.List< ac.cn.saya.mqtt.middle.entity.IotClientEntity>>
     * @创建人  ac.cn.saya.mqtt.middle
     * @创建时间  2020/9/20
     * @修改人和其它信息
     */
    @GetMapping(value = "client/select")
    public Result<List<IotClientEntity>> getClientSelectList(@RequestParam(value = "keyWord") String keyWord,HttpServletRequest request){
        return deviceService.getClientSelectList(request,keyWord);
    }

    /**
     * @Title   产品绑定告警规则
     * @Params  [param]
     * @Return  ac.cn.saya.mqtt.middle.tools.Result<java.lang.Integer>
     * @Author  saya.ac.cn-刘能凯
     * @Date  6/20/21
     * @Description
     */
    @PostMapping(value = "product/rules/{productId}")
    public Result<Integer> bindIotProductRule(@PathVariable("productId") int productId,@RequestBody List<Integer> ruleIds){
        if (CollectionUtils.isEmpty(ruleIds)){
            return ResultUtil.error(ResultEnum.NOT_PARAMETER);
        }
        return collectionService.bindIotProductRule(productId, ruleIds);
    }

    /***
     * @Title   修改产品规则绑定
     * @Params  [param]
     * @Return  ac.cn.saya.mqtt.middle.tools.Result<java.lang.Integer>
     * @Author  saya.ac.cn-刘能凯
     * @Date  6/20/21
     * @Description
     */
    @PutMapping(value = "product/rules")
    public Result<Integer> editIotProductRule(@RequestBody IotProductRulesEntity param){
        if (null == param){
            return ResultUtil.error(ResultEnum.NOT_PARAMETER);
        }
        return collectionService.editIotProductRule(param);
    }

    /**
     * @Title   解除规则绑定
     * @Params  [list]
     * @Return  ac.cn.saya.mqtt.middle.tools.Result<java.lang.Integer>
     * @Author  saya.ac.cn-刘能凯
     * @Date  6/20/21
     * @Description
     */
    @PostMapping(value = "product/rules")
    public Result<Integer> deleteIotProductRule(@RequestBody List<IotProductRulesEntity> list){
        if (CollectionUtils.isEmpty(list)){
            return ResultUtil.error(ResultEnum.NOT_PARAMETER);
        }
        return collectionService.deleteIotProductRule(list);
    }

    /**
     * @Title    分页查看产品绑定的告警规则
     * @Params  [entity]
     * @Return  ac.cn.saya.mqtt.middle.tools.Result<java.lang.Object>
     * @Author  saya.ac.cn-刘能凯
     * @Date  6/20/21
     * @Description
     */
    @GetMapping(value = "product/rules")
    public Result<Object> getIotProductRulePage(IotProductRulesEntity entity){
        return collectionService.getIotProductRulePage(entity);
    }

    /**
     * @Title   创建产品
     * @Params  [param]
     * @Return  ac.cn.saya.mqtt.middle.tools.Result<java.lang.Integer>
     * @Author  saya.ac.cn-刘能凯
     * @Date  6/20/21
     * @Description
     */
    @PostMapping(value = "product")
    public Result<Integer> addIotProduct(@RequestBody IotProductTypeEntity product){
        return deviceService.addIotProduct(product);
    }

    /***
     * @Title   修改产品
     * @Params  [param]
     * @Return  ac.cn.saya.mqtt.middle.tools.Result<java.lang.Integer>
     * @Author  saya.ac.cn-刘能凯
     * @Date  6/20/21
     * @Description
     */
    @PutMapping(value = "product")
    public Result<Integer> editIotClientRule(@RequestBody IotProductTypeEntity product){
        return deviceService.editIotProduct(product);
    }

    /**
     * @Title   删除产品
     * @Params  [list]
     * @Return  ac.cn.saya.mqtt.middle.tools.Result<java.lang.Integer>
     * @Author  saya.ac.cn-刘能凯
     * @Date  6/20/21
     * @Description
     */
    @DeleteMapping(value = "product/{productId}")
    public Result<Integer> deleteIotProduct(@PathVariable(value = "productId") Integer productId){
        return deviceService.deleteIotProduct(productId);
    }

    /**
     * @Title    获取产品列表
     * @Params  [entity]
     * @Return  ac.cn.saya.mqtt.middle.tools.Result<java.util.List<ac.cn.saya.mqtt.middle.entity.IotProductTypeEntity>>
     * @Author  saya.ac.cn-刘能凯
     * @Date  6/20/21
     * @Description
     */
    @GetMapping(value = "product")
    public Result<List<IotProductTypeEntity>> getIotClientRulePage(IotProductTypeEntity param){
        return deviceService.getIotProduct(param);
    }

    /**
     * @Title   批量添加产品物模型
     * @Params  [param]
     * @Return  ac.cn.saya.mqtt.middle.tools.Result<java.lang.Integer>
     * @Author  saya.ac.cn-刘能凯
     * @Date  6/20/21
     * @Description
     */
    @PostMapping(value = "product/ability")
    public Result<Integer> addIotProductAbility(@RequestBody List<IotAbilityEntity> params){
        return deviceService.addIotProductAbility(params);
    }

    /***
     * @Title   修改产品物模型
     * @Params  [param]
     * @Return  ac.cn.saya.mqtt.middle.tools.Result<java.lang.Integer>
     * @Author  saya.ac.cn-刘能凯
     * @Date  6/20/21
     * @Description
     */
    @PutMapping(value = "product/ability")
    public Result<Integer> editIotProductAbility(@RequestBody IotAbilityEntity product){
        return deviceService.editIotProductAbility(product);
    }

    /**
     * @Title   删除产品物模型
     * @Params  [list]
     * @Return  ac.cn.saya.mqtt.middle.tools.Result<java.lang.Integer>
     * @Author  saya.ac.cn-刘能凯
     * @Date  6/20/21
     * @Description
     */
    @DeleteMapping(value = "product/ability/{id}")
    public Result<Integer> deleteIotProductAbility(@PathVariable(value = "id") Integer id){
        return deviceService.deleteIotProductAbility(id);
    }

    /**
     * @Title    获取产品物模型列表
     * @Params  [entity]
     * @Return  ac.cn.saya.mqtt.middle.tools.Result<java.util.List<ac.cn.saya.mqtt.middle.entity.IotProductTypeEntity>>
     * @Author  saya.ac.cn-刘能凯
     * @Date  6/20/21
     * @Description
     */
    @GetMapping(value = "product/ability/{id}")
    public Result<List<IotAbilityEntity>> getIotProductAbilityPage(@PathVariable(value = "id") Integer id){
        return deviceService.getIotProductAbilityPage(id);
    }

}
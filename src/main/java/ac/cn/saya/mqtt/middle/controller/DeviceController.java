package ac.cn.saya.mqtt.middle.controller;

import ac.cn.saya.mqtt.middle.entity.*;
import ac.cn.saya.mqtt.middle.service.CollectionService;
import ac.cn.saya.mqtt.middle.service.DeviceService;
import ac.cn.saya.mqtt.middle.tools.Result;
import org.springframework.beans.factory.annotation.Autowired;
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
    public DeviceController(DeviceService deviceService, CollectionService collectionService) {
        this.deviceService = deviceService;
        this.collectionService = collectionService;
    }

    /**
     * @Title 获取产品列表
     * @Params [param]
     * @Return ac.cn.saya.mqtt.middle.tools.Result<java.util.List   <   ac.cn.saya.mqtt.middle.entity.IotProductTypeEntity>>
     * @Author saya.ac.cn-刘能凯
     * @Date 2021/8/22
     * @Description
     */
    @GetMapping(value = "product/list")
    public Result<List<IotProductEntity>> getIotGatewayType(IotProductEntity param) {
        return deviceService.getIotProduct(param);
    }

    /**
     * @Title 获取标准物理量
     * @Params [param]
     * @Return ac.cn.saya.mqtt.middle.tools.Result<java.util.List   <   ac.cn.saya.mqtt.middle.entity.IotStandardUnitEntity>>
     * @Author saya.ac.cn-刘能凯
     * @Date 2021/8/22
     * @Description
     */
    @GetMapping(value = "standard")
    public Result<List<IotStandardUnitEntity>> getStandardList() {
        return deviceService.getStandardList();
    }

    /**
     * @描述 添加设备
     * @参数 [entity,request]
     * @返回值 ac.cn.saya.mqtt.middle.tools.Result<java.lang.Integer>
     * @创建人 ac.cn.saya.mqtt.middle
     * @创建时间 2020/8/8
     * @修改人和其它信息
     */
    @PostMapping(value = "client")
    public Result<Integer> addIotClient(@RequestBody IotClientEntity entity,HttpServletRequest request) {
        return deviceService.addIotClient(entity,request);
    }

    /**
     * @描述 修改设备
     * @参数 [entity]
     * @返回值 ac.cn.saya.mqtt.middle.tools.Result<java.lang.Integer>
     * @创建人 ac.cn.saya.mqtt.middle
     * @创建时间 2020/8/8
     * @修改人和其它信息
     */
    @PutMapping(value = "client")
    public Result<Integer> editIotClient(@RequestBody IotClientEntity entity,HttpServletRequest request) {
        return deviceService.editIotClient(entity,request);
    }

    /**
     * @描述 删除设备
     * @参数 [id]
     * @返回值 ac.cn.saya.mqtt.middle.tools.Result<java.lang.Integer>
     * @创建人 ac.cn.saya.mqtt.middle
     * @创建时间 2020/8/8
     * @修改人和其它信息
     */
    @DeleteMapping(value = "client/{id}")
    public Result<Integer> deleteIotClient(@PathVariable(value = "id") Integer id) {
        return deviceService.deleteIotClient(id);
    }

    /**
     * @描述 设备分页
     * @参数 [entity]
     * @返回值 ac.cn.saya.mqtt.middle.tools.Result<java.lang.Object>
     * @创建人 ac.cn.saya.mqtt.middle
     * @创建时间 2020/8/8
     * @修改人和其它信息
     */
    @GetMapping(value = "client")
    public Result<Object> getIotClientPage(IotClientEntity entity) {
        return deviceService.getIotClientPage(entity);
    }

    /**
     * @描述 下拉列表显示Iot终端
     * @参数 [keyWord, request]
     * @返回值 ac.cn.saya.mqtt.middle.tools.Result<java.util.List   <       ac.cn.saya.mqtt.middle.entity.IotClientEntity>>
     * @创建人 ac.cn.saya.mqtt.middle
     * @创建时间 2020/9/20
     * @修改人和其它信息
     */
    @GetMapping(value = "client/select")
    public Result<List<IotClientEntity>> getClientSelectList(@RequestParam(value = "keyWord") String keyWord, HttpServletRequest request) {
        return deviceService.getClientSelectList(request, keyWord);
    }

    /**
     * @Title 创建产品
     * @Params [param]
     * @Return ac.cn.saya.mqtt.middle.tools.Result<java.lang.Integer>
     * @Author saya.ac.cn-刘能凯
     * @Date 6/20/21
     * @Description
     */
    @PostMapping(value = "product")
    public Result<Integer> addIotProduct(@RequestBody IotProductEntity product) {
        return deviceService.addIotProduct(product);
    }

    /***
     * @Title 修改产品
     * @Params [param]
     * @Return ac.cn.saya.mqtt.middle.tools.Result<java.lang.Integer>
     * @Author saya.ac.cn-刘能凯
     * @Date 6/20/21
     * @Description
     */
    @PutMapping(value = "product")
    public Result<Integer> editIotClientRule(@RequestBody IotProductEntity product) {
        return deviceService.editIotProduct(product);
    }

    /**
     * @Title 删除产品
     * @Params [list]
     * @Return ac.cn.saya.mqtt.middle.tools.Result<java.lang.Integer>
     * @Author saya.ac.cn-刘能凯
     * @Date 6/20/21
     * @Description
     */
    @DeleteMapping(value = "product/{productId}")
    public Result<Integer> deleteIotProduct(@PathVariable(value = "productId") Integer productId) {
        return deviceService.deleteIotProduct(productId);
    }

    /**
     * @Title 获取产品列表
     * @Params [entity]
     * @Return ac.cn.saya.mqtt.middle.tools.Result<java.util.List   <   ac.cn.saya.mqtt.middle.entity.IotProductTypeEntity>>
     * @Author saya.ac.cn-刘能凯
     * @Date 6/20/21
     * @Description
     */
    @GetMapping(value = "product")
    public Result<List<IotProductEntity>> getIotClientRulePage(IotProductEntity param) {
        return deviceService.getIotProduct(param);
    }

    /**
     * @Title 批量添加产品物模型
     * @Params [param]
     * @Return ac.cn.saya.mqtt.middle.tools.Result<java.lang.Integer>
     * @Author saya.ac.cn-刘能凯
     * @Date 6/20/21
     * @Description
     */
    @PostMapping(value = "product/ability")
    public Result<Integer> addIotProductAbility(@RequestBody List<IotAbilityEntity> params) {
        return deviceService.addIotProductAbility(params);
    }

    /***
     * @Title 修改产品物模型
     * @Params [param]
     * @Return ac.cn.saya.mqtt.middle.tools.Result<java.lang.Integer>
     * @Author saya.ac.cn-刘能凯
     * @Date 6/20/21
     * @Description
     */
    @PutMapping(value = "product/ability")
    public Result<Integer> editIotProductAbility(@RequestBody IotAbilityEntity product) {
        return deviceService.editIotProductAbility(product);
    }

    /**
     * @Title 删除产品物模型
     * @Params [list]
     * @Return ac.cn.saya.mqtt.middle.tools.Result<java.lang.Integer>
     * @Author saya.ac.cn-刘能凯
     * @Date 6/20/21
     * @Description
     */
    @DeleteMapping(value = "product/ability/{id}")
    public Result<Integer> deleteIotProductAbility(@PathVariable(value = "id") Integer id) {
        return deviceService.deleteIotProductAbility(id);
    }

    /**
     * @Title 获取产品物模型列表
     * @Params [entity]
     * @Return ac.cn.saya.mqtt.middle.tools.Result<java.util.List   <   ac.cn.saya.mqtt.middle.entity.IotProductTypeEntity>>
     * @Author saya.ac.cn-刘能凯
     * @Date 6/20/21
     * @Description
     */
    @GetMapping(value = "product/ability/{id}")
    public Result<List<IotAbilityEntity>> getIotProductAbilityPage(@PathVariable(value = "id") Integer id) {
        return deviceService.getIotProductAbilityPage(id);
    }

}
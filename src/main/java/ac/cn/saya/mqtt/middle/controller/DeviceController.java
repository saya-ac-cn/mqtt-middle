package ac.cn.saya.mqtt.middle.controller;

import ac.cn.saya.mqtt.middle.entity.IotClientEntity;
import ac.cn.saya.mqtt.middle.entity.IotGatewayEntity;
import ac.cn.saya.mqtt.middle.entity.IotGatewayTypeEntity;
import ac.cn.saya.mqtt.middle.entity.IotIdentifyEntity;
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

    @Autowired
    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
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
    
}

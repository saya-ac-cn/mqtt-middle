package ac.cn.saya.mqtt.middle.controller;

import ac.cn.saya.mqtt.middle.entity.IotUserEntity;
import ac.cn.saya.mqtt.middle.service.SystemService;
import ac.cn.saya.mqtt.middle.tools.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @Title: SystemController
 * @ProjectName mqtt-middle
 * @Author shmily
 * @Date: 2020/11/15 21:25
 * @Description: TODO
 */
@RestController
@RequestMapping(value = "/backend")
public class SystemController {

    private final SystemService systemService;


    @Autowired
    public SystemController(SystemService systemService) {
        this.systemService = systemService;
    }

    /**
     * @描述 用户登录
     * @参数  [params, request]
     * @返回值  ac.cn.saya.mqtt.middle.tools.Result<java.lang.Object>
     * @创建人  shmily
     * @创建时间  2020/11/15
     * @修改人和其它信息 登录回执
     */
    @PostMapping(value = "login")
    public Result<Object> login(@RequestBody IotUserEntity params, HttpServletRequest request){
        return systemService.login(params, request);
    }

    /**
     * @描述 查询最近的告警事件列表
     * @参数  [size]
     * @返回值  ac.cn.saya.mqtt.middle.tools.Result<java.lang.Object>
     * @创建人  shmily
     * @创建时间  2022/1/9
     * @修改人和其它信息
     */
    @GetMapping(value = "chart/latestWarning/{size}")
    public Result<Object> getLatestWarning(@PathVariable("size") int size){
        return systemService.latestWarning(size);
    }

    /**
     * @描述 查询设备概览
     * @参数  []
     * @返回值  ac.cn.saya.mqtt.middle.tools.Result<java.lang.Object>
     * @创建人  shmily
     * @创建时间  2022/1/9
     * @修改人和其它信息
     */
    @GetMapping(value = "chart/clientOverview")
    public Result<Object> getLatestWarning(){
        return systemService.clientOverview();
    }

    /**
     * @描述 统计各个产品下的设备数量
     * @参数  []
     * @返回值  ac.cn.saya.mqtt.middle.tools.Result<java.lang.Object>
     * @创建人  shmily
     * @创建时间  2022/1/9
     * @修改人和其它信息
     */
    @GetMapping(value = "chart/productOverview")
    public Result<Object> totalProductClient(){
        return systemService.totalProductClient();
    }

    /**
     * @描述 统计近7天的数据上报情况
     * @参数  []
     * @返回值  ac.cn.saya.mqtt.middle.tools.Result<java.lang.Object>
     * @创建人  shmily
     * @创建时间  2022/1/9
     * @修改人和其它信息
     */
    @GetMapping(value = "chart/pre7DayCollect")
    public Result<Object> pre7DayCollect(){
        return systemService.getPre7DayCollect();
    }

}

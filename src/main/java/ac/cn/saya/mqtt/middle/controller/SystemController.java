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

}

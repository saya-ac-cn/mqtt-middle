package ac.cn.saya.mqtt.middle.service;

import ac.cn.saya.mqtt.middle.entity.IotUserEntity;
import ac.cn.saya.mqtt.middle.tools.IOTException;
import ac.cn.saya.mqtt.middle.tools.Result;

import javax.servlet.http.HttpServletRequest;

/**
 * @Title: SystemService
 * @ProjectName mqtt-middle
 * @Author shmily
 * @Date: 2020/11/15 21:02
 * @Description: TODO
 */

public interface SystemService {

    /**
     * @描述 用户登录
     * @参数  [params, request]
     * @返回值  ac.cn.saya.mqtt.middle.tools.Result<java.lang.Object>
     * @创建人  shmily
     * @创建时间  2020/11/15
     * @修改人和其它信息
     */
    public Result<Object> login(IotUserEntity params, HttpServletRequest request) throws IOTException;

}

package ac.cn.saya.mqtt.middle.tools;


import ac.cn.saya.mqtt.middle.entity.IotUserEntity;

import javax.servlet.http.HttpServletRequest;

/**
 * HttpRequest 工具类
 * @Title: HttpRequestUtil
 * @ProjectName spring-boot-pro
 * @Description: TODO
 * @Author liunengkai
 * @Date: 2019-10-02 21:21
 * @Description:
 */

public class HttpRequestUtil {


    /**
     * 获取用户session信息
     * @param request
     * @return
     */
    public static IotUserEntity getMemoryUser(HttpServletRequest request){
        //传递参数true，那么当session过期时，新的session被创建，接下来可通过session.isNew()的返回值来判断是不是同一个session
        //返回值为：true，新的session被创建，action提交执行时的那个用户session已经无效
        //返回值为：false，同一个session，仍然有效
        IotUserEntity userMemory = (IotUserEntity) request.getSession().getAttribute("user");
        return userMemory;
    }

}

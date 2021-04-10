package ac.cn.saya.mqtt.middle.service.impl;

import ac.cn.saya.mqtt.middle.entity.UserEntity;
import ac.cn.saya.mqtt.middle.repository.UserDAO;
import ac.cn.saya.mqtt.middle.service.SystemService;
import ac.cn.saya.mqtt.middle.tools.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @Title: SystemServiceImpl
 * @ProjectName mqtt-middle
 * @Author shmily
 * @Date: 2020/11/15 21:05
 * @Description: TODO
 */
@Service("systemService")
public class SystemServiceImpl implements SystemService {

    @Resource
    private UserDAO userDAO;

    /**
     * @描述 用户登录
     * @参数 [params, request]
     * @返回值 ac.cn.saya.mqtt.middle.tools.Result<java.lang.Object>
     * @创建人 shmily
     * @创建时间 2020/11/15
     * @修改人和其它信息
     */
    @Override
    public Result<Object> login(UserEntity params, HttpServletRequest request) throws IOTException{
        UserEntity memoryUser = HttpRequestUtil.getMemoryUser(request);
        if (null == params || StringUtils.isEmpty(params.getAccount()) || StringUtils.isEmpty(params.getPassword())){
            return ResultUtil.error(ResultEnum.NOT_PARAMETER);
        }
        UserEntity queryResult = userDAO.queryUser(params);
        if (null == queryResult){
            return ResultUtil.error(ResultEnum.NOT_EXIST);
        }
        if (null != memoryUser){
            // 密码脱敏处理
            queryResult.setPassword(null);
            return ResultUtil.success(queryResult);
        }else{
            try {
                params.setPassword(DesUtil.encrypt(params.getPassword()));
            } catch (Exception e) {
                CurrentLineInfo.printCurrentLineInfo("用户登录时，对密码加密发生异常",e,SystemServiceImpl.class);
                throw new IOTException(ResultEnum.ERROR);
            }
            if ((params.getPassword()).equals(queryResult.getPassword())){
                queryResult.setPassword(null);
                HttpSession session = request.getSession();
                session.setAttribute("user", queryResult);
                UserEntity editParams = new UserEntity();
                editParams.setAccount(params.getAccount());
                userDAO.update(editParams);
                return ResultUtil.success(queryResult);
            }else{
                return ResultUtil.error(ResultEnum.ERROR);
            }
        }
    }
}

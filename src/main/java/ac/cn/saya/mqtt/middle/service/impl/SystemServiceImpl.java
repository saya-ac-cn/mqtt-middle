package ac.cn.saya.mqtt.middle.service.impl;

import ac.cn.saya.mqtt.middle.entity.IotClientEntity;
import ac.cn.saya.mqtt.middle.entity.IotUserEntity;
import ac.cn.saya.mqtt.middle.entity.IotWarningResultEntity;
import ac.cn.saya.mqtt.middle.meta.Metadata;
import ac.cn.saya.mqtt.middle.repository.primary.*;
import ac.cn.saya.mqtt.middle.service.SystemService;
import ac.cn.saya.mqtt.middle.tools.*;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private IotUserDAO iotUserDAO;

    @Resource
    private IotWarningResultDAO iotWarningResultDAO;

    @Resource
    private IotClientDAO iotClientDAO;

    @Resource
    private IotProductDAO iotProductDAO;

    @Resource
    private PrimaryBatchDAO primaryBatchDAO;

    @Resource
    private Metadata metadata;

    /**
     * @描述 用户登录
     * @参数 [params, request]
     * @返回值 ac.cn.saya.mqtt.middle.tools.Result<java.lang.Object>
     * @创建人 shmily
     * @创建时间 2020/11/15
     * @修改人和其它信息
     */
    @Override
    public Result<Object> login(IotUserEntity params, HttpServletRequest request) throws IOTException{
        IotUserEntity memoryUser = HttpRequestUtil.getMemoryUser(request);
        if (null == params || StringUtils.isEmpty(params.getAccount()) || StringUtils.isEmpty(params.getPassword())){
            return ResultUtil.error(ResultEnum.NOT_PARAMETER);
        }
        IotUserEntity queryResult = iotUserDAO.queryUser(params);
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
                IotUserEntity editParams = new IotUserEntity();
                editParams.setAccount(params.getAccount());
                iotUserDAO.update(editParams);
                return ResultUtil.success(queryResult);
            }else{
                return ResultUtil.error(ResultEnum.ERROR);
            }
        }
    }

    /**
     * @描述 查询最近size条告警事件
     * @参数  [size]
     * @返回值  ac.cn.saya.mqtt.middle.tools.Result<java.lang.Object>
     * @创建人  shmily
     * @创建时间  2022/1/9
     * @修改人和其它信息
     */
    @Override
    public Result<Object> latestWarning(int size){
        try {
            List<IotWarningResultEntity> result = iotWarningResultDAO.queryLatestN(size);
            if (CollectionUtils.isEmpty(result)){
                return ResultUtil.error(ResultEnum.NOT_EXIST);
            }
            return ResultUtil.success(result);
        } catch (Exception e) {
            CurrentLineInfo.printCurrentLineInfo("查询最近size条告警事件发生异常",e,SystemServiceImpl.class);
            throw new IOTException(ResultEnum.ERROR);
        }
    }

    /**
     * @描述 设备概览
     * @参数  []
     * @返回值  ac.cn.saya.mqtt.middle.tools.Result<java.lang.Object>
     * @创建人  shmily
     * @创建时间  2022/1/9
     * @修改人和其它信息
     */
    @Override
    public Result<Object> clientOverview(){
        // 取出设备在线数
        int onlineClientSize = metadata.getOnlineClientSize();
        IotClientEntity query = new IotClientEntity();
        // 取出所有的设备数
        query.setRemove(1);
        Long allClientSize = iotClientDAO.queryCount(query);
        // 查询启用的设备
        query.setEnable(1);
        Long enableClientSize = iotClientDAO.queryCount(query);
        Map<String, Object> result = new HashMap<>();
        result.put("all",allClientSize);
        result.put("online",onlineClientSize);
        result.put("enable",enableClientSize);
        return ResultUtil.success(result);
    }

    /**
     * @描述 统计各个产品下的设备数量
     * @参数  []
     * @返回值  ac.cn.saya.mqtt.middle.tools.Result<java.lang.Object>
     * @创建人  shmily
     * @创建时间  2022/1/9
     * @修改人和其它信息
     */
    @Override
    public Result<Object> totalProductClient(){
        try {
            List<Map> result = iotProductDAO.totalProductClient();
            if (CollectionUtils.isEmpty(result)){
                return ResultUtil.error(ResultEnum.NOT_EXIST);
            }
            return ResultUtil.success(result);
        } catch (Exception e) {
            CurrentLineInfo.printCurrentLineInfo("统计各个产品下的设备数量发生异常",e,SystemServiceImpl.class);
            throw new IOTException(ResultEnum.ERROR);
        }
    }

    /**
     * @描述 查询近7天的数据上报情况
     * @参数  []
     * @返回值  ac.cn.saya.mqtt.middle.tools.Result<java.lang.Object>
     * @创建人  shmily
     * @创建时间  2022/1/9
     * @修改人和其它信息
     */
    @Override
    public Result<Object> getPre7DayCollect(){
        try {
            Map result = primaryBatchDAO.countPre7DayCollect();
            if (CollectionUtils.isEmpty(result)){
                return ResultUtil.error(ResultEnum.NOT_EXIST);
            }
            return ResultUtil.success(result);
        } catch (Exception e) {
            CurrentLineInfo.printCurrentLineInfo("查询近7天的数据上报异常",e,SystemServiceImpl.class);
            throw new IOTException(ResultEnum.ERROR);
        }
    }
}

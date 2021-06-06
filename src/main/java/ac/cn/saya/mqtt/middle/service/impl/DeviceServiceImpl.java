package ac.cn.saya.mqtt.middle.service.impl;

import ac.cn.saya.mqtt.middle.entity.*;
import ac.cn.saya.mqtt.middle.tools.IOTException;
import ac.cn.saya.mqtt.middle.service.DeviceService;
import ac.cn.saya.mqtt.middle.tools.*;
import ac.cn.saya.mqtt.middle.meta.Metadata;
import ac.cn.saya.mqtt.middle.repository.IotClientDAO;
import ac.cn.saya.mqtt.middle.repository.IotGatewayDAO;
import ac.cn.saya.mqtt.middle.repository.IotGatewayTypeDAO;
import ac.cn.saya.mqtt.middle.repository.IotIdentifyDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Title: DeviceServiceImpl
 * @ProjectName mqtt-middle
 * @Author shmily
 * @Date: 2020/7/29 21:00
 * @Description: 网关&设备业务层
 */
@Service("deviceService")
@Transactional(propagation= Propagation.REQUIRED, isolation= Isolation.SERIALIZABLE, rollbackFor= IOTException.class)
public class DeviceServiceImpl implements DeviceService {

    @Resource
    private IotGatewayTypeDAO iotGatewayTypeDAO;

    @Resource
    private IotIdentifyDAO iotIdentifyDAO;

    @Resource
    private IotGatewayDAO iotGatewayDAO;

    @Resource
    private IotClientDAO iotClientDAO;

    @Resource
    private Metadata metadata;

    /**
     * @描述 获取Iot设备类型
     * @参数
     * @返回值 IotGatewayTypeEntity 集合
     * @创建人 shmily
     * @创建时间 2020/7/29
     * @修改人和其它信息
     */
    @Transactional(readOnly = true)
    @Override
    public Result<List<IotGatewayTypeEntity>> getIotGatewayType() {
        try {
            List<IotGatewayTypeEntity> result = iotGatewayTypeDAO.queryAll();
            if (!result.isEmpty()){
                return ResultUtil.success(result);
            }
            return ResultUtil.error(ResultEnum.NOT_EXIST);
        } catch (Exception e) {
            CurrentLineInfo.printCurrentLineInfo("获取Iot设备类型异常",e,DeviceServiceImpl.class);
            throw new IOTException(ResultEnum.ERROR);
        }
    }

    /**
     * @描述 添加网关
     * @参数
     * @返回值
     * @创建人 shmily
     * @创建时间 2020/7/29
     * @修改人和其它信息
     */
    @Override
    public Result<Integer> addIotGateway(IotGatewayEntity entity, HttpServletRequest request) {
        if (null ==entity){
            return ResultUtil.error(ResultEnum.NOT_PARAMETER);
        }
        UserEntity userSession = (UserEntity) request.getSession().getAttribute("user");
        entity.setSource(userSession.getAccount());
        IotIdentifyEntity authenInfo = entity.getAuthenInfo();
        if (null == authenInfo){
            return ResultUtil.error(ResultEnum.NOT_PARAMETER);
        }
        if (StringUtils.isEmpty(authenInfo.getUsername()) || StringUtils.isEmpty(authenInfo.getPassword())){
            return ResultUtil.error(ResultEnum.NOT_PARAMETER);
        }
        // 本例采用固定写法
        authenInfo.setSalt("sha256");
        // 加密密码
        authenInfo.setPassword(Sha256Utils.getSHA256(authenInfo.getPassword()));
        String uuid = RandomUtil.getRandKeys(12);
        authenInfo.setUuid(uuid);
        if (StringUtils.isEmpty(entity.getSource()) || null == entity.getDeviceType()){
            return ResultUtil.error(ResultEnum.NOT_PARAMETER);
        }
        try {
            if (iotIdentifyDAO.insert(authenInfo)>0){
                entity.setUuid(uuid);
                if (iotGatewayDAO.insert(entity)>0){
                    return ResultUtil.success();
                }
            }
            return ResultUtil.error(ResultEnum.ERROR);
        } catch (Exception e) {
            CurrentLineInfo.printCurrentLineInfo("添加网关设备异常", e,DeviceServiceImpl.class);
            throw new IOTException(ResultEnum.ERROR);
        }
    }

    /**
     * @描述 修改网关
     * @参数
     * @返回值
     * @创建人 shmily
     * @创建时间 2020/7/29
     * @修改人和其它信息
     */
    @Override
    public Result<Integer> editIotGateway(IotGatewayEntity entity,HttpServletRequest request) {
        if (null ==entity || null == entity.getId()){
            return ResultUtil.error(ResultEnum.NOT_PARAMETER);
        }
        UserEntity userSession = (UserEntity) request.getSession().getAttribute("user");
        entity.setSource(userSession.getAccount());
        IotIdentifyEntity authenInfo = entity.getAuthenInfo();
        try {
            if (null != authenInfo || null != entity.getUuid()){
                iotIdentifyDAO.update(authenInfo);
            }
            iotGatewayDAO.update(entity);
            return ResultUtil.success();
        } catch (Exception e) {
            CurrentLineInfo.printCurrentLineInfo("修改网关设备异常",e,DeviceServiceImpl.class);
            throw new IOTException(ResultEnum.ERROR);
        }
    }

    /**
     * @描述 删除网关信息
     * @参数 [id]
     * @返回值 ac.cn.saya.mqtt.middle.tools.Result<java.lang.Integer>
     * @创建人 shmily
     * @创建时间 2020/8/1
     * @修改人和其它信息
     */
    @Override
    public Result<Integer> deleteIotGateway(Integer id) {
        IotGatewayEntity gatewayEntity = new IotGatewayEntity();
        gatewayEntity.setId(id);
        try {
            IotGatewayEntity query = iotGatewayDAO.query(gatewayEntity);
            if (null == query){
                return ResultUtil.error(ResultEnum.NOT_EXIST);
            }
            // 置为删除
            query.setRemove(2);
            IotClientEntity clientEntity = new IotClientEntity();
            clientEntity.setGatewayId(id);
            clientEntity.setRemove(2);
            // 先通过网关id删除设备信息
            if (iotClientDAO.updateByGatewayId(clientEntity) < 0){
                return ResultUtil.error(ResultEnum.ERROR.getCode(),"删除设备信息异常");
            }
            // 查询出设备信息，并从元数据中删除
            List<IotClientEntity> clientEntities = iotClientDAO.queryByGatewayId(id);
            metadata.removeClients(clientEntities);
            // 删除认证信息
            if (iotIdentifyDAO.delete(query.getUuid()) < 0){
                return ResultUtil.error(ResultEnum.ERROR.getCode(),"删除认证信息异常");
            }
            // 删除网关信息
            if (iotGatewayDAO.update(query) < 0){
                return ResultUtil.error(ResultEnum.ERROR.getCode(),"删除网关信息异常");
            }
            return ResultUtil.success();
        } catch (Exception e) {
            CurrentLineInfo.printCurrentLineInfo("删除网关信息异常",e,DeviceServiceImpl.class);
            throw new IOTException(ResultEnum.ERROR);
        }
    }

    /**
     * @描述 网关分页
     * @参数 [entity]
     * @返回值 ac.cn.saya.mqtt.middle.tools.Result<java.lang.Object>
     * @创建人 shmily
     * @创建时间 2020/8/1
     * @修改人和其它信息
     */
    @Transactional(readOnly = true)
    @Override
    public Result<Object> getIotGatewayPage(IotGatewayEntity entity) {
        try {
            // 只显示在用的网关
            entity.setRemove(1);
            Long count = iotGatewayDAO.queryCount(entity);
            return PageTools.page(count, entity, (condition) -> iotGatewayDAO.queryPage((IotGatewayEntity) condition));
        } catch (Exception e) {
            CurrentLineInfo.printCurrentLineInfo("查询分页后的网关列表发生异常",e,DeviceServiceImpl.class);
            throw new IOTException(ResultEnum.ERROR);
        }
    }

    /**
     * @描述 获取单个网关详情
     * @参数  [id]
     * @返回值  ac.cn.saya.mqtt.middle.tools.Result<ac.cn.saya.mqtt.middle.entity.IotGatewayEntity>
     * @创建人  shmily
     * @创建时间  2020/8/23
     * @修改人和其它信息
     */
    @Transactional(readOnly = true)
    @Override
    public Result<IotGatewayEntity> getIotGatewayEntity(Integer id){
        if (null == id || id <= 0){
            return ResultUtil.error(ResultEnum.NOT_PARAMETER);
        }
        IotGatewayEntity gatewayEntity = new IotGatewayEntity();
        gatewayEntity.setId(id);
        try {
            IotGatewayEntity result = iotGatewayDAO.query(gatewayEntity);
            if (null == result){
                return ResultUtil.error(ResultEnum.NOT_EXIST);
            }
            return ResultUtil.success(result);
        } catch (Exception e) {
            CurrentLineInfo.printCurrentLineInfo("获取单个网关详情发生异常",e,DeviceServiceImpl.class);
            throw new IOTException(ResultEnum.ERROR);
        }
    }

    /**
     * @描述 获取网关列表-用于添加设备时的下拉选框
     * @参数  [entity]
     * @返回值  ac.cn.saya.mqtt.middle.tools.Result<java.util.List<ac.cn.saya.mqtt.middle.entity.IotGatewayEntity>>
     * @创建人  shmily
     * @创建时间  2020/8/23
     * @修改人和其它信息
     */
    @Transactional(readOnly = true)
    @Override
    public Result<List<IotGatewayEntity>> getIotGatewayList(HttpServletRequest request){
        IotGatewayEntity entity = new IotGatewayEntity();
        UserEntity userSession = (UserEntity) request.getSession().getAttribute("user");
        // 放入用户名
        entity.setSource(userSession.getAccount());
        // 正常在用设备
        entity.setRemove(1);
        try {
            List<IotGatewayEntity> result = iotGatewayDAO.queryList(entity);
            if (result.isEmpty()){
                return ResultUtil.error(ResultEnum.NOT_EXIST);
            }
            return ResultUtil.success(result);
        } catch (Exception e) {
            CurrentLineInfo.printCurrentLineInfo("获取网关下拉列表发生异常",e,DeviceServiceImpl.class);
            throw new IOTException(ResultEnum.ERROR);
        }
    }

    /**
     * @描述 添加设备
     * @参数 [entity]
     * @返回值 ac.cn.saya.mqtt.middle.tools.Result<java.lang.Integer>
     * @创建人 shmily
     * @创建时间 2020/8/1
     * @修改人和其它信息
     */
    @Override
    public Result<Integer> addIotClient(IotClientEntity entity) {
        if (null == entity || entity.getGatewayId() == null || StringUtils.isEmpty(entity.getName())|| entity.getSerialNum() == null){
            return ResultUtil.error(ResultEnum.NOT_PARAMETER);
        }
        try {
            if (iotClientDAO.insert(entity) >= 0){
                metadata.doRefreshClient(entity);
                return ResultUtil.success();
            }
            return ResultUtil.error(ResultEnum.ERROR.getCode(),"添加设备异常");
        } catch (Exception e) {
            CurrentLineInfo.printCurrentLineInfo("添加设备发生异常",e,DeviceServiceImpl.class);
            throw new IOTException(ResultEnum.ERROR);
        }
    }

    /**
     * @描述 修改设备
     * @参数 [entity]
     * @返回值 ac.cn.saya.mqtt.middle.tools.Result<java.lang.Integer>
     * @创建人 shmily
     * @创建时间 2020/8/1
     * @修改人和其它信息
     */
    @Override
    public Result<Integer> editIotClient(IotClientEntity entity) {
        if (null == entity || entity.getId()== null){
            return ResultUtil.error(ResultEnum.NOT_PARAMETER);
        }
        try {
            IotClientEntity oldClient = iotClientDAO.query(new IotClientEntity(entity.getId()));
            if (null == oldClient){
                return ResultUtil.error(ResultEnum.NOT_EXIST);
            }
            if (iotClientDAO.update(entity) >= 0){
                if (null != entity.getGatewayId() && null != oldClient.getGatewayId() && !(entity.getGatewayId()).equals(oldClient.getGatewayId())){
                    // 如果用户改了所属网关，这里需要做相应的处理
                    metadata.doRefreshClient(oldClient,entity);
                }
                metadata.doRefreshClient(entity);
                return ResultUtil.success();
            }
            return ResultUtil.error(ResultEnum.ERROR.getCode(),"修改设备异常");
        } catch (Exception e) {
            CurrentLineInfo.printCurrentLineInfo("修改设备发生异常", e,DeviceServiceImpl.class);
            throw new IOTException(ResultEnum.ERROR);
        }
    }

    /**
     * @描述 删除设备
     * @参数 [id]
     * @返回值 ac.cn.saya.mqtt.middle.tools.Result<java.lang.Integer>
     * @创建人 shmily
     * @创建时间 2020/8/1
     * @修改人和其它信息
     */
    @Override
    public Result<Integer> deleteIotClient(Integer id) {
        if (null == id || id <= 0){
            return ResultUtil.error(ResultEnum.NOT_PARAMETER);
        }
        try {
            IotClientEntity clientEntity = iotClientDAO.query(new IotClientEntity(id));
            if (null == clientEntity){
                return ResultUtil.error(ResultEnum.NOT_EXIST);
            }
            // 值为删除状态
            clientEntity.setRemove(2);
            if (iotClientDAO.update(clientEntity) >= 0){
                metadata.removeClient(clientEntity);
                return ResultUtil.success();
            }
            return ResultUtil.error(ResultEnum.ERROR.getCode(),"删除设备异常");
        } catch (Exception e) {
            CurrentLineInfo.printCurrentLineInfo("删除设备发生异常", e,DeviceServiceImpl.class);
            throw new IOTException(ResultEnum.ERROR);
        }
    }

    /**
     * @描述 设备分页
     * @参数 [entity]
     * @返回值 ac.cn.saya.mqtt.middle.tools.Result<java.lang.Object>
     * @创建人 shmily
     * @创建时间 2020/8/1
     * @修改人和其它信息
     */
    @Transactional(readOnly = true)
    @Override
    public Result<Object> getIotClientPage(IotClientEntity entity) {
        // 是否移除,1=正常;2=已移除
        entity.setRemove(1);
        try {
            Long count = iotClientDAO.queryCount(entity);
            return PageTools.page(count, entity, (condition) -> iotClientDAO.queryPage((IotClientEntity) condition));
        } catch (Exception e) {
            CurrentLineInfo.printCurrentLineInfo("查询分页后的设备分页发生异常",e,DeviceServiceImpl.class);
            throw new IOTException(ResultEnum.ERROR);
        }
    }

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
    @Transactional(readOnly = true)
    @Override
    public Result<List<IotClientEntity>> getClientSelectList(HttpServletRequest request,String keyWord){
        try {
            UserEntity userSession = (UserEntity) request.getSession().getAttribute("user");
            List<IotClientEntity> result = iotClientDAO.querySelectList(userSession.getAccount(), keyWord);
            if (result.isEmpty()){
                return ResultUtil.error(ResultEnum.NOT_EXIST);
            }
            return ResultUtil.success(result);
        } catch (Exception e) {
            CurrentLineInfo.printCurrentLineInfo("获取下拉列表显示Iot终端发生异常",e,DeviceServiceImpl.class);
            throw new IOTException(ResultEnum.ERROR);
        }
    }

}

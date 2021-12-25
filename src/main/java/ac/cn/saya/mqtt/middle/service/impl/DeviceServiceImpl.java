package ac.cn.saya.mqtt.middle.service.impl;

import ac.cn.saya.mqtt.middle.entity.*;
import ac.cn.saya.mqtt.middle.repository.primary.*;
import ac.cn.saya.mqtt.middle.repository.identify.IotIdentifyDAO;
import ac.cn.saya.mqtt.middle.tools.IOTException;
import ac.cn.saya.mqtt.middle.service.DeviceService;
import ac.cn.saya.mqtt.middle.tools.*;
import ac.cn.saya.mqtt.middle.meta.Metadata;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

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
    private IotIdentifyDAO iotIdentifyDAO;

    @Resource
    private IotClientDAO iotClientDAO;

    @Resource
    private IotProductDAO iotProductDAO;

    @Resource
    private IotAbilityDAO iotAbilityDAO;

    @Resource
    private IotStandardDAO iotStandardDAO;

    @Resource
    private Metadata metadata;

    /**
     * @Title   获取iot产品列表
     * @Params  [param]
     * @Return  ac.cn.saya.mqtt.middle.tools.Result<java.util.List<ac.cn.saya.mqtt.middle.entity.IotProductTypeEntity>>
     * @Author  saya.ac.cn-刘能凯
     * @Date  2021/8/22
     * @Description
     */
    @Override
    public Result<List<IotProductEntity>> getIotProduct(IotProductEntity param) {
        try {
            param.setStatus(1);
            List<IotProductEntity> result = iotProductDAO.queryList(param);
            if (CollectionUtils.isEmpty(result)){
                return ResultUtil.error(ResultEnum.NOT_EXIST);
            }
            return ResultUtil.success(result);
        } catch (Exception e) {
            CurrentLineInfo.printCurrentLineInfo("获取Iot产品异常",e,DeviceServiceImpl.class);
            throw new IOTException(ResultEnum.ERROR);
        }
    }


    /**
     * @Title   获取iot标准物理量
     * @Params  [param]
     * @Return  ac.cn.saya.mqtt.middle.tools.Result<java.util.List<ac.cn.saya.mqtt.middle.entity.IotStandardUnitEntity>>
     * @Author  saya.ac.cn-刘能凯
     * @Date  2021/8/22
     * @Description
     */
    @Override
    public Result<List<IotStandardUnitEntity>> getStandardList(){
        try {
            List<IotStandardUnitEntity> result = iotStandardDAO.findAll();
            if (CollectionUtils.isEmpty(result)){
                return ResultUtil.error(ResultEnum.NOT_EXIST);
            }
            return ResultUtil.success(result);
        } catch (Exception e) {
            CurrentLineInfo.printCurrentLineInfo("获取Iot标准物理量异常",e,DeviceServiceImpl.class);
            throw new IOTException(ResultEnum.ERROR);
        }
    }

    /**
     * @描述 添加设备
     * @参数 [entity,request]
     * @返回值 ac.cn.saya.mqtt.middle.tools.Result<java.lang.Integer>
     * @创建人 shmily
     * @创建时间 2020/8/1
     * @修改人和其它信息
     */
    @Override
    public Result<Integer> addIotClient(IotClientEntity entity,HttpServletRequest request) {
        if (null == entity || entity.getProductId() == null ||StringUtils.hasText(entity.getName())){
            return ResultUtil.error(ResultEnum.NOT_PARAMETER);
        }
        IotIdentifyEntity authenInfo = entity.getAuthenInfo();
        if (null == authenInfo){
            return ResultUtil.error(ResultEnum.NOT_PARAMETER);
        }
        if (StringUtils.hasText(authenInfo.getUsername()) || StringUtils.hasText(authenInfo.getPassword())){
            return ResultUtil.error(ResultEnum.NOT_PARAMETER);
        }
        IotUserEntity userSession = (IotUserEntity) request.getSession().getAttribute("user");
        entity.setBelongUser(userSession.getAccount());
        // 本例采用固定写法
        authenInfo.setSalt("sha256");
        // 加密密码
        authenInfo.setPassword(Sha256Utils.getSHA256(authenInfo.getPassword()));
        String uuid = RandomUtil.getRandKeys(18);
        authenInfo.setUuid(uuid);
        try {
             if (iotIdentifyDAO.insert(authenInfo)>0){
                 entity.setIdentifyUuid(uuid);
                 if (iotClientDAO.insert(entity) >= 0){
                     entity = iotClientDAO.query(entity);
                     // 添加设备需要将设备加入缓存
                     metadata.doRefreshClient(entity);
                     return ResultUtil.success();
                 }
             }
            return ResultUtil.error(ResultEnum.ERROR.getCode(),"添加设备异常");
        } catch (Exception e) {
            CurrentLineInfo.printCurrentLineInfo("添加设备发生异常",e,DeviceServiceImpl.class);
            throw new IOTException(ResultEnum.ERROR);
        }
    }

    /**
     * @描述 修改设备
     * @参数 [entity,request]
     * @返回值 ac.cn.saya.mqtt.middle.tools.Result<java.lang.Integer>
     * @创建人 shmily
     * @创建时间 2020/8/1
     * @修改人和其它信息
     */
    @Override
    public Result<Integer> editIotClient(IotClientEntity entity,HttpServletRequest request) {
        if (null == entity || entity.getId()== null){
            return ResultUtil.error(ResultEnum.NOT_PARAMETER);
        }
        IotUserEntity userSession = (IotUserEntity) request.getSession().getAttribute("user");
        entity.setBelongUser(userSession.getAccount());
        // 获取用户提取的表单信息
        IotIdentifyEntity authenInfoForm = entity.getAuthenInfo();
        try {
            IotClientEntity oldClient = iotClientDAO.query(new IotClientEntity(entity.getId()));
            if (null == oldClient){
                // 原来的设备不存在
                return ResultUtil.error(ResultEnum.NOT_EXIST);
            }
            IotIdentifyEntity authenInfo = null;
            if (null != authenInfoForm && StringUtils.hasText(authenInfoForm.getPassword())){
                // 发生密码的修改
                authenInfo = new IotIdentifyEntity();
                authenInfo.setUuid(oldClient.getIdentifyUuid());
                authenInfo.setPassword(Sha256Utils.getSHA256(authenInfo.getPassword()));
            }
            if(null != authenInfo){
                iotIdentifyDAO.update(authenInfo);
            }
            if (iotClientDAO.update(entity) >= 0){
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
            IotUserEntity userSession = (IotUserEntity) request.getSession().getAttribute("user");
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

    /**
     * @描述 创建iot产品
     * @参数 [entity]
     * @返回值 ac.cn.saya.mqtt.middle.tools.Result<java.lang.Integer>
     * @创建人 shmily
     * @创建时间 2020/8/1
     * @修改人和其它信息
     * TODO 创建产品不需要加入到缓存中（因为只创建了产品，下面还没有模型，无实意）
     */
    @Override
    public Result<Integer> addIotProduct(IotProductEntity entity) {
        if (null == entity || StringUtils.isEmpty(entity.getName())){
            return ResultUtil.error(ResultEnum.NOT_PARAMETER);
        }
        try {
            // TODO 创建时默认启用
            entity.setStatus(1);
            List<IotProductEntity> checkResult = iotProductDAO.queryList(entity);
            if (CollectionUtils.isEmpty(checkResult)){
                iotProductDAO.insert(entity);
                return ResultUtil.success();
            }
            return ResultUtil.error(ResultEnum.ERROR.getCode(),"该产品名称已经存在了，请换一个吧");
        } catch (Exception e) {
            CurrentLineInfo.printCurrentLineInfo("添加iot产品发生异常",e,DeviceServiceImpl.class);
            throw new IOTException(ResultEnum.ERROR);
        }
    }

    /**
     * @描述 修改iot产品(当前只允许修改产品的名称)
     * @参数 [entity]
     * @返回值 ac.cn.saya.mqtt.middle.tools.Result<java.lang.Integer>
     * @创建人 shmily
     * @创建时间 2020/8/1
     * @修改人和其它信息
     * TODO 修改产品名称不需要更新缓存
     */
    @Override
    public Result<Integer> editIotProduct(IotProductEntity entity) {
        if (null == entity || entity.getId()== null || StringUtils.isEmpty(entity.getName())){
            return ResultUtil.error(ResultEnum.NOT_PARAMETER);
        }
        try {
            IotProductEntity checkWhere = new IotProductEntity();
            checkWhere.setName(entity.getName());
            List<IotProductEntity> checkResult = iotProductDAO.queryList(checkWhere);
            boolean checkFlag = true;
            for (IotProductEntity item:checkResult) {
                if ((entity.getName()).equals(item.getName()) && (entity.getId()).equals(item.getId())){
                    // 要修该的产品名和已有的产品名冲突
                    checkFlag = false;
                }
            }
            if (!checkFlag){
                return ResultUtil.error(ResultEnum.ERROR.getCode(),"该产品名称已经存在了，请换一个吧");
            }
            // TODO 由于当前只允许修改产品的名称，为了防止用户在这一步修改产品的状态，在这里将产品的状态临时置位null
            entity.setStatus(null);
            iotProductDAO.update(entity);
            return ResultUtil.success();
        } catch (Exception e) {
            CurrentLineInfo.printCurrentLineInfo("修改产品发生异常", e,DeviceServiceImpl.class);
            throw new IOTException(ResultEnum.ERROR);
        }
    }

    /**
     * @描述 删除iot产品
     * @参数 [id]
     * @返回值 ac.cn.saya.mqtt.middle.tools.Result<java.lang.Integer>
     * @创建人 shmily
     * @创建时间 2020/8/1
     * @修改人和其它信息
     * TODO 删除iot产品需要同步缓存（因为它下面还有物模型）
     */
    @Override
    public Result<Integer> deleteIotProduct(Integer id) {
        // 对于已经关联设备的产品不予删除
        if (null == id || id <= 0){
            return ResultUtil.error(ResultEnum.NOT_PARAMETER);
        }
        try {
            List<IotClientEntity> clientEntity = iotClientDAO.queryClientByProductId(id);
            if (!CollectionUtils.isEmpty(clientEntity)){
                return ResultUtil.error(ResultEnum.ERROR.getCode(),"该产品已经关联了其它设备，不允许删除");
            }
            IotProductEntity entity = new IotProductEntity();
            entity.setId(id);
            entity.setStatus(2);
            iotProductDAO.update(entity);
            // TODO 需要更新缓存
            metadata.removeProductAbility(id);
            metadata.removeProductRule(id);
            return ResultUtil.success();
        } catch (Exception e) {
            CurrentLineInfo.printCurrentLineInfo("删除产品发生异常", e,DeviceServiceImpl.class);
            throw new IOTException(ResultEnum.ERROR);
        }
    }

    /**
     * @描述 产品分页
     * @参数 [entity]
     * @返回值 ac.cn.saya.mqtt.middle.tools.Result<java.lang.Object>
     * @创建人 shmily
     * @创建时间 2020/8/1
     * @修改人和其它信息
     */
    @Transactional(readOnly = true)
    @Override
    public Result<Object> getIotProductPage(IotProductEntity entity) {
        // 只显示正常的产品
        entity.setStatus(1);
        try {
            Long count = iotProductDAO.queryCount(entity);
            return PageTools.page(count, entity, (condition) -> iotProductDAO.queryPage((IotProductEntity) condition));
        } catch (Exception e) {
            CurrentLineInfo.printCurrentLineInfo("查询分页后的产品分页发生异常",e,DeviceServiceImpl.class);
            throw new IOTException(ResultEnum.ERROR);
        }
    }

    /**
     * @描述 添加iot产品物模型（同一批次下的添加，产品id应该是一致的）
     * @参数 [entity]
     * @返回值 ac.cn.saya.mqtt.middle.tools.Result<java.lang.Integer>
     * @创建人 shmily
     * @创建时间 2020/8/1
     * @修改人和其它信息
     * TODO 需要写入缓存
     */
    @Override
    public Result<Integer> addIotProductAbility(List<IotAbilityEntity> entities) {
        List<IotAbilityEntity> params = new ArrayList<>(entities.size());
        try {
            for (IotAbilityEntity entity:entities) {
                boolean flag = (null == entity || StringUtils.isEmpty(entity.getProperty()) || Objects.isNull(entity.getProductId())
                        || StringUtils.isEmpty(entity.getScope()) || Objects.isNull(entity.getType())|| StringUtils.isEmpty(entity.getName())
                        || Objects.isNull(entity.getRwFlag() )|| Objects.isNull(entity.getStandardId()));
                if (flag){
                    continue;
                }
                params.add(entity);
            }
            if (CollectionUtils.isEmpty(params)){
                return ResultUtil.error(ResultEnum.NOT_PARAMETER);
            }
            iotAbilityDAO.batchInsert(params);
            // TODO 插入到缓存
            // 当前批次的模型对于的是一个批次下的产品，找到第一个模型下的产品id，然后在数据库里面找到这个产品下面所有的模型放入内存即可
            List<IotAbilityEntity> planWrite = iotAbilityDAO.queryAbilityByProductId(params.get(0).getProductId());
            if (!CollectionUtils.isEmpty(planWrite)){
                metadata.doRefreshProductAbility(params.get(0).getProductId(),planWrite);
            }
            return ResultUtil.success();
        } catch (Exception e) {
            CurrentLineInfo.printCurrentLineInfo("添加iot产品物模型发生异常",e,DeviceServiceImpl.class);
            throw new IOTException(ResultEnum.ERROR);
        }
    }

    /**
     * @描述 修改iot产品物模型（同一批次下的修改，产品id应该是一致的）
     * @参数 [entity]
     * @返回值 ac.cn.saya.mqtt.middle.tools.Result<java.lang.Integer>
     * @创建人 shmily
     * @创建时间 2020/8/1
     * @修改人和其它信息
     * TODO 需要更新缓存
     */
    @Override
    public Result<Integer> editIotProductAbility(IotAbilityEntity entity) {
        boolean flag = (null == entity || StringUtils.isEmpty(entity.getProperty()) || Objects.isNull(entity.getProductId())
                || StringUtils.isEmpty(entity.getScope()) || StringUtils.isEmpty(entity.getName())|| Objects.isNull(entity.getProductId())
                || Objects.isNull(entity.getRwFlag() ) || Objects.isNull(entity.getStandardId()));
        if (flag){
            return ResultUtil.error(ResultEnum.NOT_PARAMETER);
        }
        try {
            if (iotAbilityDAO.update(entity) >= 0){
                // TODO 更新缓存
                List<IotAbilityEntity> planWrite = iotAbilityDAO.queryAbilityByProductId(entity.getProductId());
                if (!CollectionUtils.isEmpty(planWrite)){
                    metadata.doRefreshProductAbility(entity.getProductId(),planWrite);
                }
                // 同添加
                return ResultUtil.success();
            }
            return ResultUtil.error(ResultEnum.ERROR.getCode(),"修改iot产品物模型异常");
        } catch (Exception e) {
            CurrentLineInfo.printCurrentLineInfo("修改iot产品物模型发生异常", e,DeviceServiceImpl.class);
            throw new IOTException(ResultEnum.ERROR);
        }
    }

    /**
     * @描述 删除iot产品物模型
     * @参数 [id]
     * @返回值 ac.cn.saya.mqtt.middle.tools.Result<java.lang.Integer>
     * @创建人 shmily
     * @创建时间 2020/8/1
     * @修改人和其它信息
     * TODO 需要同步缓存
     */
    @Override
    public Result<Integer> deleteIotProductAbility(Integer id){
        if (null == id || id <= 0){
            return ResultUtil.error(ResultEnum.NOT_PARAMETER);
        }
        try {
            if (iotAbilityDAO.delete(id) >= 0){
                // TODO 更新缓存
                List<IotAbilityEntity> planWrite = iotAbilityDAO.queryAbilityByProductId(id);
                if (!CollectionUtils.isEmpty(planWrite)){
                    metadata.doRefreshProductAbility(id,planWrite);
                }
                return ResultUtil.success();
            }
            return ResultUtil.error(ResultEnum.ERROR.getCode(),"删除iot产品物模型异常");
        } catch (Exception e) {
            CurrentLineInfo.printCurrentLineInfo("删除设备发生异常", e,DeviceServiceImpl.class);
            throw new IOTException(ResultEnum.ERROR);
        }
    }

    /**
     * @描述 iot产品物模型列表
     * @参数 [entity]
     * @返回值 ac.cn.saya.mqtt.middle.tools.Result<java.util.List<ac.cn.saya.mqtt.middle.entity.IotAbilityEntity>>
     * @创建人 shmily
     * @创建时间 2020/8/1
     * @修改人和其它信息
     */
    @Transactional(readOnly = true)
    @Override
    public Result<List<IotAbilityEntity>> getIotProductAbilityPage(Integer id) {
        try {
            List<IotAbilityEntity> result = iotAbilityDAO.queryAbilityByProductId(id);
            if (CollectionUtils.isEmpty(result)){
                return ResultUtil.success(Collections.EMPTY_LIST);
            }
            // 序列化scope
            for (IotAbilityEntity item:result) {
                item.setScopeParam(item.scopeToString());
            }
            return ResultUtil.success(result);
        } catch (Exception e) {
            CurrentLineInfo.printCurrentLineInfo("查询iot产品物模型列表发生异常",e,DeviceServiceImpl.class);
            throw new IOTException(ResultEnum.ERROR);
        }
    }


}

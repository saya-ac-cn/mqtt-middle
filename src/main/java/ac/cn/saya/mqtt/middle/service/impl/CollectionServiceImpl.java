package ac.cn.saya.mqtt.middle.service.impl;

import ac.cn.saya.mqtt.middle.entity.IotCollectionEntity;
import ac.cn.saya.mqtt.middle.entity.IotWarningResultEntity;
import ac.cn.saya.mqtt.middle.entity.IotWarningRulesEntity;
import ac.cn.saya.mqtt.middle.tools.IOTException;
import ac.cn.saya.mqtt.middle.service.CollectionService;
import ac.cn.saya.mqtt.middle.tools.*;
import ac.cn.saya.mqtt.middle.meta.Metadata;
import ac.cn.saya.mqtt.middle.repository.IotCollectionDAO;
import ac.cn.saya.mqtt.middle.repository.IotWarningResultDAO;
import ac.cn.saya.mqtt.middle.repository.IotWarningRulesDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Title: CollectionServiceImpl
 * @ProjectName mqtt-middle
 * @Description: TODO
 * @Author saya
 * @Date: 2020/8/2 10:56
 * @Description:
 */
@Service("collectionService")
@Transactional(propagation= Propagation.REQUIRED, isolation= Isolation.SERIALIZABLE, rollbackFor= IOTException.class)
public class CollectionServiceImpl implements CollectionService {


    @Resource
    private IotCollectionDAO iotCollectionDAO;

    @Resource
    private IotWarningRulesDAO iotWarningRulesDAO;

    @Resource
    private IotWarningResultDAO iotWarningResultDAO;

    @Resource
    private Metadata metadata;

    /**
     * @描述 分页查看采集信息
     * @参数 [entity]
     * @返回值 ac.cn.saya.mqtt.middle.tools.Result<java.lang.Object>
     * @创建人 shmily
     * @创建时间 2020/8/2
     * @修改人和其它信息
     */
    @Transactional(readOnly = true)
    @Override
    public Result<Object> getIotCollectionPage(IotCollectionEntity entity) {
        try {
            Long count = iotCollectionDAO.queryCount(entity);
            return PageTools.page(count, entity, (condition) -> iotCollectionDAO.queryPage((IotCollectionEntity) condition));
        } catch (Exception e) {
            CurrentLineInfo.printCurrentLineInfo("查询分页后的采集信息列表发生异常",e,CollectionServiceImpl.class);
            throw new IOTException(ResultEnum.ERROR);
        }
    }

    /**
     * @描述 分页查看告警报告信息
     * @参数 [entity]
     * @返回值 ac.cn.saya.mqtt.middle.tools.Result<java.lang.Object>
     * @创建人 shmily
     * @创建时间 2020/8/2
     * @修改人和其它信息
     */
    @Transactional(readOnly = true)
    @Override
    public Result<Object> getIotWarningResultPage(IotWarningResultEntity entity) {
        try {
            Long count = iotWarningResultDAO.queryCount(entity);
            return PageTools.page(count, entity, (condition) -> iotWarningResultDAO.queryPage((IotWarningResultEntity) condition));
        } catch (Exception e) {
            CurrentLineInfo.printCurrentLineInfo("查询分页后的告警报告信息列表发生异常",e,CollectionServiceImpl.class);
            throw new IOTException(ResultEnum.ERROR);
        }
    }

    /**
     * @描述 添加告警规则
     * @参数
     * @返回值
     * @创建人 shmily
     * @创建时间 2020/7/29
     * @修改人和其它信息
     */
    @Override
    public Result<Integer> addIotWarningRules(List<IotWarningRulesEntity> list) {
        if (list.isEmpty()){
            return ResultUtil.error(ResultEnum.NOT_PARAMETER);
        }
        try {
            iotWarningRulesDAO.insert(list);
            // 还需要写入到缓存
            metadata.doRefreshRules(list);
            return ResultUtil.success();
        } catch (Exception e) {
            CurrentLineInfo.printCurrentLineInfo("添加告警规则发生异常", e,CollectionServiceImpl.class);
            throw new IOTException(ResultEnum.ERROR);
        }
    }

    /**
     * @描述 修改告警规则
     * @参数
     * @返回值
     * @创建人 shmily
     * @创建时间 2020/7/29
     * @修改人和其它信息
     */
    @Override
    public Result<Integer> editIotWarningRules(List<IotWarningRulesEntity> list) {
        if (list.isEmpty()){
            return ResultUtil.error(ResultEnum.NOT_PARAMETER);
        }
        try {
            iotWarningRulesDAO.update(list);
            // 还需要修改到缓存
            metadata.doRefreshRules(list);
            return ResultUtil.success();
        } catch (Exception e) {
            CurrentLineInfo.printCurrentLineInfo("修改告警规则发生异常",e,CollectionServiceImpl.class);
            throw new IOTException(ResultEnum.ERROR);
        }
    }

    /**
     * @描述 删除告警规则
     * @参数 [id]
     * @返回值 ac.cn.saya.mqtt.middle.tools.Result<java.lang.Integer>
     * @创建人 shmily
     * @创建时间 2020/8/1
     * @修改人和其它信息
     */
    @Override
    public Result<Integer> deleteIotWarningRules(List<Integer> list) {
        if (list.isEmpty()){
            return ResultUtil.error(ResultEnum.NOT_PARAMETER);
        }
        try {
            iotWarningRulesDAO.deleteById(list);
            // 还需要删除缓存
            metadata.removeRules(list);
            return ResultUtil.success();
        } catch (Exception e) {
            CurrentLineInfo.printCurrentLineInfo("删除告警规则发生异常", e,CollectionServiceImpl.class);
            throw new IOTException(ResultEnum.ERROR);
        }
    }

    /**
     * @描述 分页查看终端告警规则
     * @参数 [entity]
     * @返回值 ac.cn.saya.mqtt.middle.tools.Result<java.lang.Object>
     * @创建人 shmily
     * @创建时间 2020/8/1
     * @修改人和其它信息
     */
    @Transactional(readOnly = true)
    @Override
    public Result<Object> getIotWarningRulesPage(IotWarningRulesEntity entity) {
        try {
            Long count = iotWarningRulesDAO.queryCount(entity);
            return PageTools.page(count, entity, (condition) -> iotWarningRulesDAO.queryPage((IotWarningRulesEntity) condition));
        } catch (Exception e) {
            CurrentLineInfo.printCurrentLineInfo("查询分页后的查看终端告警规则列表发生异常",e,CollectionServiceImpl.class);
            throw new IOTException(ResultEnum.ERROR);
        }
    }
}

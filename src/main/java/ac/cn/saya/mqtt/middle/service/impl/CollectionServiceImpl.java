package ac.cn.saya.mqtt.middle.service.impl;

import ac.cn.saya.mqtt.middle.entity.*;
import ac.cn.saya.mqtt.middle.enums.SymbolEnum;
import ac.cn.saya.mqtt.middle.meta.Metadata;
import ac.cn.saya.mqtt.middle.repository.*;
import ac.cn.saya.mqtt.middle.service.CollectionService;
import ac.cn.saya.mqtt.middle.tools.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Title: CollectionServiceImpl
 * @ProjectName mqtt-middle
 * @Description: TODO
 * @Author saya
 * @Date: 2020/8/2 10:56
 * @Description:
 */
@Service("collectionService")
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE, rollbackFor = IOTException.class)
public class CollectionServiceImpl implements CollectionService {

    @Resource
    private IotGatewayDAO iotGatewayDAO;

    @Resource
    private IotClientDAO iotClientDAO;

    @Resource
    private IotClientRulesDAO iotClientRulesDAO;

    @Resource
    private IotCollectionDAO iotCollectionDAO;

    @Resource
    private IotWarningRulesDAO iotWarningRulesDAO;

    @Resource
    private IotWarningResultDAO iotWarningResultDAO;

    @Resource
    private Metadata metadata;

    /**
     * @描述 获取所有告警规则列表
     * @参数
     * @返回值 ac.cn.saya.mqtt.middle.tools.Result<java.lang.Object>
     * @创建人 shmily
     * @创建时间 2020/6/20
     * @修改人和其它信息
     */
    @Transactional(readOnly = true)
    @Override
    public Result<Object> getWarningRule(Integer clientId) {
        try {
            List<IotWarningRulesEntity> allRules = iotWarningRulesDAO.selector();
            List<IotWarningRulesEntity> alreadyBindRules = iotClientRulesDAO.queryByClient(clientId,null);
            if (!CollectionUtils.isEmpty(allRules) && !CollectionUtils.isEmpty(alreadyBindRules)){
                // 排除已经绑定的规则
                allRules.removeAll(alreadyBindRules);
            }
            return ResultUtil.success(allRules);
        } catch (Exception e) {
            CurrentLineInfo.printCurrentLineInfo("获取所有告警规则列表发生异常", e, CollectionServiceImpl.class);
            throw new IOTException(ResultEnum.ERROR);
        }
    }

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
            CurrentLineInfo.printCurrentLineInfo("查询分页后的采集信息列表发生异常", e, CollectionServiceImpl.class);
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
            long count = iotWarningResultDAO.queryCount(entity);
            return PageTools.page(count, entity, (condition) -> iotWarningResultDAO.queryPage((IotWarningResultEntity) condition));
        } catch (Exception e) {
            CurrentLineInfo.printCurrentLineInfo("查询分页后的告警报告信息列表发生异常", e, CollectionServiceImpl.class);
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
    public Result<Integer> addIotWarningRules(IotWarningRulesEntity param) {
        if (null == param) {
            return ResultUtil.error(ResultEnum.NOT_PARAMETER);
        }
        try {
            iotWarningRulesDAO.insert(Collections.singletonList(param));
            return ResultUtil.success();
        } catch (Exception e) {
            CurrentLineInfo.printCurrentLineInfo("添加告警规则发生异常", e, CollectionServiceImpl.class);
            throw new IOTException(ResultEnum.ERROR);
        }
    }

    /**
     * @描述 修改告警规则（修改后，缓存中设备绑定的规则也需要做一次更新）
     * @参数
     * @返回值
     * @创建人 shmily
     * @创建时间 2020/7/29
     * @修改人和其它信息
     */
    @Override
    public Result<Integer> editIotWarningRules(IotWarningRulesEntity param) {
        if (null == param){
            return ResultUtil.error(ResultEnum.NOT_PARAMETER);
        }
        try {
            iotWarningRulesDAO.update(Collections.singletonList(param));
            // 对于绑定到设备上的告警规则，还需要刷新到缓存
            // 查询本规则已经被关联到那些设备上
            List<Integer> clientIds = iotClientRulesDAO.queryByRule(param.getId());
            if (!CollectionUtils.isEmpty(clientIds)){
                for (Integer clientId:clientIds) {
                    List<IotWarningRulesEntity> rules = iotClientRulesDAO.queryByClient(clientId,1);
                    metadata.removeRule(clientId);
                    metadata.doRefreshRule(clientId,rules);
                }
            }
            return ResultUtil.success();
        } catch (Exception e) {
            CurrentLineInfo.printCurrentLineInfo("修改告警规则发生异常", e, CollectionServiceImpl.class);
            throw new IOTException(ResultEnum.ERROR);
        }
    }

    /**
     * @描述 删除告警规则（删除后，缓存中设备绑定的规则也需要做一次更新）
     * @参数 [id]
     * @返回值 ac.cn.saya.mqtt.middle.tools.Result<java.lang.Integer>
     * @创建人 shmily
     * @创建时间 2020/8/1
     * @修改人和其它信息
     */
    @Override
    public Result<Integer> deleteIotWarningRules(Integer ruleId) {
        try {
            // 查询本规则被绑定到了哪些设备
            List<Integer> clientIds = iotClientRulesDAO.queryByRule(ruleId);
            // 解绑设备
            iotClientRulesDAO.deleteByRule(ruleId);
            // 删除规则
            iotWarningRulesDAO.deleteById(Collections.singletonList(ruleId));
            // 对于绑定到设备上的告警规则，还需要刷新到缓存
            if (!CollectionUtils.isEmpty(clientIds)){
                for (Integer clientId:clientIds) {
                    List<IotWarningRulesEntity> rules = iotClientRulesDAO.queryByClient(clientId,1);
                    metadata.removeRule(clientId);
                    metadata.doRefreshRule(clientId,rules);
                }
            }
            return ResultUtil.success();
        } catch (Exception e) {
            CurrentLineInfo.printCurrentLineInfo("删除告警规则发生异常", e, CollectionServiceImpl.class);
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
            CurrentLineInfo.printCurrentLineInfo("查询分页后的查看终端告警规则列表发生异常", e, CollectionServiceImpl.class);
            throw new IOTException(ResultEnum.ERROR);
        }
    }

    /**
     * @param datas 消息
     * @Title 批量写入采集数据，本方法由mq消费端发起调用
     * @Return void
     * @Author saya.ac.cn-刘能凯
     * @Date 4/11/21
     * @Description
     */
    @Override
    public void insertCollectionData(List<IotCollectionEntity> datas) {
        try {
            if (CollectionUtils.isEmpty(datas)){
                return;
            }
            iotCollectionDAO.batchInsert(datas);
        } catch (Exception e) {
            CurrentLineInfo.printCurrentLineInfo("批量写入采集数据发生异常", e, CollectionServiceImpl.class);
        }
    }

    /**
     * 根据采集的数据校验是否产生异常告警
     * @param datas  采集数据
     * @Return
     * @Author saya.ac.cn-刘能凯
     * @Date 4/11/21
     * @Description
     */
    @Override
    public void checkRuleWarring(List<IotCollectionEntity> datas) {
        if (CollectionUtils.isEmpty(datas)) {
            CurrentLineInfo.printCurrentLineInfo("跳过告警检查：", CollectionServiceImpl.class,"由于本批次数据为空，所以跳过检查");
            return;
        }

        List<IotWarningResultEntity> result = new ArrayList<>(datas.size());
        // 第一次遍历上报数据
        for (IotCollectionEntity record:datas) {
            List<IotWarningRulesEntity> rules = metadata.getRule(record.getClientId());
            if (CollectionUtils.isEmpty(rules)) {
                return;
            }
            // 针对这条数据依次去拿告警规则进行比对
            for (IotWarningRulesEntity rule : rules) {
                // 判断当前的数据字段是否为本告警规则字段
                if (!Objects.equals(record.getAbilityId(),rule.getAbilityId())){
                    continue;
                }
                SymbolEnum op = SymbolEnum.valueOf(rule.getSymbol());
                if (Objects.isNull(op)){
                    CurrentLineInfo.printCurrentLineInfo("获取运算符失败：", CollectionServiceImpl.class,rule.getSymbol()+"无效");
                    continue;
                }
                boolean flag = SymbolCompareUtil.compare(op, rule.getValue1(), rule.getValue2(), record.getValue());
                System.out.println("告警结果：" + flag);
                result.add(new IotWarningResultEntity(record.getClientId(), rule.getId(), "数据上报告警", rule.getAbilityEntity().getProperty() + op.getDescript() + record.getValue()));
            }
        }
        if (!CollectionUtils.isEmpty(result)) {
            iotWarningResultDAO.batchInsert(result);
        }
    }


    /**
     * @描述 分页查看设备已经绑定的告警报告信息
     * @参数 [entity]
     * @返回值 ac.cn.saya.mqtt.middle.tools.Result<java.lang.Object>
     * @创建人 shmily
     * @创建时间 2020/8/2
     * @修改人和其它信息
     */
    @Transactional(readOnly = true)
    @Override
    public Result<Object> getIotClientRulePage(IotClientRulesEntity entity) {
        try {
            Long count = iotClientRulesDAO.queryCount(entity);
            return PageTools.page(count, entity, (condition) -> iotClientRulesDAO.queryPage((IotClientRulesEntity) condition));
        } catch (Exception e) {
            CurrentLineInfo.printCurrentLineInfo("查询分页后的设备绑定告警规则列表发生异常", e, CollectionServiceImpl.class);
            throw new IOTException(ResultEnum.ERROR);
        }
    }

    /**
     * @描述 设备绑定告警规则（需要加入到缓存）
     * @参数
     * @返回值
     * @创建人 shmily
     * @创建时间 2020/7/29
     * @修改人和其它信息
     */
    @Override
    public Result<Integer> bindIotClientRule(int clientId,List<Integer> ruleIds) {
        if (CollectionUtils.isEmpty(ruleIds)) {
            return ResultUtil.error(ResultEnum.NOT_PARAMETER);
        }
        try {
            iotClientRulesDAO.insert(clientId,ruleIds);
            List<IotWarningRulesEntity> rules = iotClientRulesDAO.queryByClient(clientId,1);
            metadata.removeRule(clientId);
            metadata.doRefreshRule(clientId,rules);
            return ResultUtil.success();
        } catch (Exception e) {
            CurrentLineInfo.printCurrentLineInfo("设备绑定告警规则发生异常", e, CollectionServiceImpl.class);
            throw new IOTException(ResultEnum.ERROR);
        }
    }

    /**
     * @描述 修改绑定的告警规则（修改后，缓存中设备绑定的规则也需要做一次更新）
     * @参数
     * @返回值
     * @创建人 shmily
     * @创建时间 2020/7/29
     * @修改人和其它信息
     */
    @Override
    public Result<Integer> editIotClientRule(IotClientRulesEntity param) {
        if (null == param) {
            return ResultUtil.error(ResultEnum.NOT_PARAMETER);
        }
        try {
            iotClientRulesDAO.update(param);
            List<IotWarningRulesEntity> rules = iotClientRulesDAO.queryByClient(param.getClientId(),1);
            metadata.removeRule(param.getClientId());
            metadata.doRefreshRule(param.getClientId(),rules);
            return ResultUtil.success();
        } catch (Exception e) {
            CurrentLineInfo.printCurrentLineInfo("修改绑定的告警规则发生异常", e, CollectionServiceImpl.class);
            throw new IOTException(ResultEnum.ERROR);
        }
    }

    /**
     * @描述 解绑设备告警规则（删除后，缓存中设备绑定的规则也需要做一次更新）
     * @参数 [id]
     * @返回值 ac.cn.saya.mqtt.middle.tools.Result<java.lang.Integer>
     * @创建人 shmily
     * @创建时间 2020/8/1
     * @修改人和其它信息
     */
    @Override
    public Result<Integer> deleteIotClientRule(List<IotClientRulesEntity> list) {
        if (CollectionUtils.isEmpty(list)) {
            return ResultUtil.error(ResultEnum.NOT_PARAMETER);
        }
        try {
            List<Integer> ruleIds = list.stream().map(IotClientRulesEntity::getId).collect(Collectors.toList());
            iotClientRulesDAO.deleteById(ruleIds);
            // 按照设备id进行分组，确定哪些设备的规则需要刷新
            Map<Integer, List<IotClientRulesEntity>> clients = list.stream().collect(Collectors.groupingBy(IotClientRulesEntity::getClientId));
            for (Map.Entry<Integer, List<IotClientRulesEntity>> item:clients.entrySet()) {
                metadata.removeRule(item.getKey());
                List<IotWarningRulesEntity> rules = iotClientRulesDAO.queryByClient(item.getKey(),1);
                if (!CollectionUtils.isEmpty(rules)){
                    metadata.doRefreshRule(item.getKey(),rules);
                }
            }
            return ResultUtil.success();
        } catch (Exception e) {
            CurrentLineInfo.printCurrentLineInfo("解绑设备告警规则发生异常", e, CollectionServiceImpl.class);
            throw new IOTException(ResultEnum.ERROR);
        }
    }

    /**
     * 修改网关的最后上报时间
     * @param uuid 网关唯一编码
     */
    @Override
    public void updateGatewayHeart(String uuid){
        iotGatewayDAO.updateHeart(uuid);
        metadata.addOnlineGateway(uuid);
    }

    /**
     * 修改设备的最后上报时间
     * @param clientId 设备id
     */
    @Override
    public void updateDeviceHeart(int clientId){
        iotClientDAO.updateHeart(clientId);
    }


}
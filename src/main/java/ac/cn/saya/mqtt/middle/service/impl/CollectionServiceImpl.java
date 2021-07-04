package ac.cn.saya.mqtt.middle.service.impl;

import ac.cn.saya.mqtt.middle.entity.*;
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
     * @描述 获取基本物理量
     * @参数
     * @返回值 ac.cn.saya.mqtt.middle.tools.Result<java.lang.Object>
     * @创建人 shmily
     * @创建时间 2020/8/2
     * @修改人和其它信息
     */
    @Transactional(readOnly = true)
    @Override
    public Result<Object> getSymbolUnits() {
        try {
            Map<String, String> units = metadata.getUnits();
            return ResultUtil.success(units);
        } catch (Exception e) {
            CurrentLineInfo.printCurrentLineInfo("获取基本物理量发生异常", e, CollectionServiceImpl.class);
            throw new IOTException(ResultEnum.ERROR);
        }
    }

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
            iotCollectionDAO.batchInsert(datas);
        } catch (Exception e) {
            CurrentLineInfo.printCurrentLineInfo("批量写入采集数据发生异常", e, CollectionServiceImpl.class);
        }
    }

    /**
     * @param client 设备信息
     * @param datas  采集数据
     * @Title根据采集的数据校验是否产生异常告警
     * @Return
     * @Author saya.ac.cn-刘能凯
     * @Date 4/11/21
     * @Description
     */
    @Override
    public void checkRuleWarring(IotClientEntity client, List<IotCollectionEntity> datas) {
        List<IotWarningRulesEntity> rules = metadata.getRule(client.getId());
        if (CollectionUtils.isEmpty(rules)) {
            return;
        }
        List<IotWarningResultEntity> result = new ArrayList<>(datas.size());
        for (IotWarningRulesEntity rule : rules) {
            List<IotCollectionEntity> collectData = datas.stream().filter(e -> (rule.getUnits()).equals(e.getUnits())).collect(Collectors.toList());
            if (CollectionUtils.isEmpty(collectData)) {
                continue;
            }
            // 在指定规则下，只能对应一种上报的数据类型（终端设备上报的数据中，各种类型的数据只有一条）。
            IotCollectionEntity item = collectData.get(0);
            boolean flag = SymbolCompareUtil.compare(rule.getSymbol(), rule.getValue1(), rule.getValue2(), item.getValue());
            System.out.println("告警结果：" + flag);
            result.add(new IotWarningResultEntity(client.getId(), rule.getId(), "数据上报告警", rule.getUnitsName() + "=" + item.getValue()));
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
     * 修改设备&网关的最后山包时间
     * @param gatewayId
     * @param clientId
     */
    @Override
    public void updateDeviceHeart(int gatewayId,int clientId){
        iotGatewayDAO.updateHeart(gatewayId);
        iotClientDAO.updateHeart(clientId);
    }


}
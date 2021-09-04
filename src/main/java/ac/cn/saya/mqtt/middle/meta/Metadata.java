package ac.cn.saya.mqtt.middle.meta;

import ac.cn.saya.mqtt.middle.entity.*;
import ac.cn.saya.mqtt.middle.repository.IotClientDAO;
import ac.cn.saya.mqtt.middle.repository.IotClientRulesDAO;
import ac.cn.saya.mqtt.middle.repository.IotUnitsDAO;
import ac.cn.saya.mqtt.middle.repository.IotWarningRulesDAO;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @Title: Metadata
 * @ProjectName mqtt-middle
 * @Author shmily
 * @Date: 2020/8/2 15:27
 * @Description: 元数据（终端数据，规则告警）
 */
@Component
public class Metadata {
//
//    @Resource
//    private IotClientDAO iotClientDAO;
//
//    @Resource
//    private IotClientRulesDAO iotClientRulesDAO;
//
//    @Resource
//    private IotUnitsDAO iotUnitsDAO;
//
//    /**
//     * 终端设备数据 内存元数据
//     */
//    private final Map<ClientParam, IotClientEntity> clients = new ConcurrentHashMap<>();
//
//    /**
//     * 设备绑定的告警规则数据 内存元数据
//     */
//    private final Map<Integer, List<IotWarningRulesEntity>> rules = new ConcurrentHashMap<>();
//
//    /**
//     * 在线的设备 内存元数据
//     */
//    private final Map<String, String> onlineGateway = new ConcurrentHashMap<>();
//
//    /**
//     * 标准物理量分类
//     */
//    private final Map<String, String> units = new ConcurrentHashMap<>();
//
//    public void doRefreshClient(IotClientEntity param) {
//        if (null == param) {
//            return;
//        }
//        IotGatewayEntity gateway = param.getGateway();
//        if (null == gateway) {
//            return;
//        }
//        Integer serialNum = param.getSerialNum();
//        String uuid = gateway.getUuid();
//        clients.put(new ClientParam(uuid, serialNum), param);
//    }
//
//    public void doRefreshClient(IotClientEntity oldParam,IotClientEntity newParam) {
//        if (null == oldParam) {
//            return;
//        }
//        IotGatewayEntity oldGateway = oldParam.getGateway();
//        if (null == oldGateway) {
//            return;
//        }
//        Integer oldSerialNum= newParam.getSerialNum();
//        String oldUuid = oldGateway.getUuid();
//        // 先移除之前的，然后进行添加操作
//        clients.remove(new ClientParam(oldUuid, oldSerialNum));
//        if (null == newParam) {
//            return;
//        }
//        IotGatewayEntity gateway = newParam.getGateway();
//        if (null == gateway) {
//            return;
//        }
//        Integer serialNum = newParam.getSerialNum();
//        String uuid = gateway.getUuid();
//        clients.put(new ClientParam(uuid, serialNum), newParam);
//    }
//
//    public void doRefreshRule(int clientId,List<IotWarningRulesEntity> param) {
//        if (CollectionUtils.isEmpty(param)) {
//            return;
//        }
//        rules.put(clientId,param);
//    }
//
//    public void doRefreshUnits(IotUnitsEntity param){
//        if (null == param || StringUtils.isEmpty(param.getSymbol()) || StringUtils.isEmpty(param.getName())){
//            return;
//        }
//        units.put(param.getSymbol(),param.getName());
//    }
//
//    public void doRefreshUnits(IotUnitsEntity oldParam,IotUnitsEntity newParam){
//        if (null == oldParam || StringUtils.isEmpty(oldParam.getSymbol())){
//            return;
//        }
//        if (null == newParam || StringUtils.isEmpty(newParam.getSymbol()) || StringUtils.isEmpty(newParam.getName())){
//            return;
//        }
//        if (units.containsKey(oldParam.getSymbol())){
//            units.remove(oldParam.getSymbol());
//        }
//        units.put(newParam.getSymbol(),newParam.getSymbol());
//    }
//
//    public void doRefreshUnits(List<IotUnitsEntity> param){
//        if (CollectionUtils.isEmpty(param)) {
//            return;
//        }
//        param.forEach(this::doRefreshUnits);
//    }
//
//    public void doRefreshClients(List<IotClientEntity> param) {
//        if (CollectionUtils.isEmpty(param)) {
//            return;
//        }
//        param.forEach(this::doRefreshClient);
//    }
//
//    public void removeClient(IotClientEntity param) {
//        if (null == param) {
//            return;
//        }
//        IotGatewayEntity gateway = param.getGateway();
//        if (null == gateway) {
//            return;
//        }
//        Integer clientId = param.getId();
//        String uuid = gateway.getUuid();
//        clients.remove(new ClientParam(uuid, clientId));
//    }
//
//    public void removeRule(Integer param) {
//        if (null == param) {
//            return;
//        }
//        rules.remove(param);
//    }
//
//    public void removeClients(List<IotClientEntity> param) {
//        if (param.isEmpty()) {
//            return;
//        }
//        param.forEach(this::removeClient);
//    }
//
//    public void removeRules(List<Integer> param) {
//        if (param.isEmpty()) {
//            return;
//        }
//        param.forEach(this::removeRule);
//    }
//
//    public IotClientEntity getClient(IotClientEntity param) {
//        if (null == param) {
//            return null;
//        }
//        IotGatewayEntity gateway = param.getGateway();
//        if (null == gateway) {
//            return null;
//        }
//        Integer clientId = param.getId();
//        String uuid = gateway.getUuid();
//        return clients.get(new ClientParam(uuid, clientId));
//    }
//
////    public IotWarningRulesEntity getRule(Integer ruleId) {
////        if (null == ruleId) {
////            return null;
////        }
////        return rules.get(ruleId);
////    }
//
//    public void addOnlineGateway(String uuid, String onlineTime) {
//        if (!StringUtils.isEmpty(uuid) && !StringUtils.isEmpty(onlineTime)) {
//            onlineGateway.put(uuid, onlineTime);
//        }
//    }
//
//    public boolean isOnlineGateway(String uuid){
//        if (StringUtils.isEmpty(uuid)){
//            return false;
//        }
//        return onlineGateway.containsKey(uuid);
//    }
//
//    /**
//     * 根据网关uuid，设备的序号查询设备
//     * @param key
//     * @return
//     */
//    public IotClientEntity getClients(ClientParam key) {
//        return clients.get(key);
//    }
//
//    public List<IotWarningRulesEntity> getRule(int clientId){
//        return rules.get(clientId);
//    }
//
////    public List<IotWarningRulesEntity> getRuleByClient(int clientId){
////        if (CollectionUtils.isEmpty(rules) || CollectionUtils.isEmpty(rules.values())){
////            return Collections.EMPTY_LIST;
////        }
////        List<IotWarningRulesEntity> clientRules = rules.values().stream().filter(e -> e.getClientId() == clientId).collect(Collectors.toList());
////        return clientRules;
////    }
//
//    public Map<String, String> getUnits() {
//        return units;
//    }
//
//    public String getUnitsName(String symbol) {
//        return units.getOrDefault(symbol,symbol);
//    }
//
//    public List<IotUnitsEntity> getUnitsToList(){
//        List<IotUnitsEntity> list = units.entrySet().stream().map(c -> new IotUnitsEntity(c.getKey(), c.getValue())).collect(Collectors.toList());
//        return list;
//    }
//
//    /**
//     * @描述 spring 容器启动后 本方法将执行，每间隔60分钟全量刷新一次
//     * @参数 []
//     * @返回值 void
//     * @创建人 shmily
//     * @创建时间 2020/8/2
//     * @修改人和其它信息
//     */
//    public void refresh() {
//        // 写入设备信息到内存
//        IotClientEntity clientEntity = new IotClientEntity();
//        clientEntity.setRemove(1);
//        Long clientCount = iotClientDAO.queryCount(clientEntity);
//        if (clientCount > 0) {
//            clientEntity.setStartLine(0);
//            clientEntity.setEndLine(clientCount.intValue());
//            List<IotClientEntity> clientList = iotClientDAO.queryPage(clientEntity);
//            doRefreshClients(clientList);
//        } else {
//            clients.clear();
//        }
//        // 写入告警规则到设备
//        IotClientRulesEntity ruleEntity = new IotClientRulesEntity();
//        ruleEntity.setEnable(1);
//        Long ruleCount = iotClientRulesDAO.queryCount(ruleEntity);
//        if (clientCount > 0) {
//            ruleEntity.setStartLine(0);
//            ruleEntity.setEndLine(ruleCount.intValue());
//            List<IotClientRulesEntity> ruleList = iotClientRulesDAO.queryPage(ruleEntity);
//            if (!CollectionUtils.isEmpty(ruleList)){
//                // 按设备id进行分组，确定每个设备关联的规则
//                Map<Integer, List<IotClientRulesEntity>> groupByClientRuleMap = ruleList.stream().collect(Collectors.groupingBy(IotClientRulesEntity::getClientId));
//                for (Map.Entry<Integer, List<IotClientRulesEntity>> item:groupByClientRuleMap.entrySet()) {
//                    List<IotClientRulesEntity> clentBindRules = item.getValue();
//                    List<IotWarningRulesEntity> rules = clentBindRules.stream().map(e -> e.getRule()).collect(Collectors.toList());
//                    // 缓存
//                    doRefreshRule(item.getKey(),rules);
//                }
//            }
//        } else {
//            rules.clear();
//        }
//        // 写入基本物理量到内存
//        List<IotUnitsEntity> allUnits = iotUnitsDAO.queryAll();
//        if (!CollectionUtils.isEmpty(allUnits)){
//            doRefreshUnits(allUnits);
//        }
//    }

}
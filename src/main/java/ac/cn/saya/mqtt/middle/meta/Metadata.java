package ac.cn.saya.mqtt.middle.meta;

import ac.cn.saya.mqtt.middle.entity.*;
import ac.cn.saya.mqtt.middle.repository.IotClientDAO;
import ac.cn.saya.mqtt.middle.repository.IotProductTypeDAO;
import ac.cn.saya.mqtt.middle.tools.DateUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.*;
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

    @Resource
    private IotClientDAO iotClientDAO;

    @Resource
    private IotProductTypeDAO iotProductTypeDAO;


    /**
     * 终端设备数据 内存元数据
     */
    private final Map<ClientParam, IotClientEntity> clients = new ConcurrentHashMap<>();

    /**
     * 产品绑定的告警规则数据 内存元数据，key->产品id，value->这个产品下的告警规则
     */
    private final Map<Integer, List<IotWarningRulesEntity>> productRuleMap = new ConcurrentHashMap<>();

    /**
     * 产品以及下属的物模型内存元数据 key-> 产品id，value -> (属性字段，物模型)
     */
    private final Map<Integer,Map<String,IotAbilityEntity>> productAbilityMap = new ConcurrentHashMap<>();

    /**
     * 在线的网关设备 内存元数据，key->网关编码
     */
    private final Map<String, String> onlineGatewayMap = new ConcurrentHashMap<>();

    /**
     * 标准物理量分类
     */
    private final Map<String, String> units = new ConcurrentHashMap<>();


    public void doRefreshClient(IotClientEntity param) {
        if (null == param) {
            return;
        }
        IotGatewayEntity gateway = param.getGateway();
        if (null == gateway) {
            return;
        }
        Integer serialNum = param.getSerialNum();
        String uuid = gateway.getUuid();
        clients.put(new ClientParam(uuid, serialNum), param);
    }

    public void doRefreshClient(IotClientEntity oldParam,IotClientEntity newParam) {
        if (null == oldParam) {
            return;
        }
        IotGatewayEntity oldGateway = oldParam.getGateway();
        if (null == oldGateway) {
            return;
        }
        if (null == newParam) {
            return;
        }
        IotGatewayEntity gateway = newParam.getGateway();
        if (null == gateway) {
            return;
        }
        Integer oldSerialNum = newParam.getSerialNum();
        String oldUuid = oldGateway.getUuid();
        // 先移除之前的，然后进行添加操作
        clients.remove(new ClientParam(oldUuid, oldSerialNum));
        Integer serialNum = newParam.getSerialNum();
        String uuid = gateway.getUuid();
        clients.put(new ClientParam(uuid, serialNum), newParam);
    }

    public void doRefreshProductRule(int productId, List<IotWarningRulesEntity> param) {
        if (CollectionUtils.isEmpty(param)) {
            return;
        }
        productRuleMap.put(productId,param);
    }


    /**
     * 将产品以及下面的物模型推入缓存
     * @param productId 产品id
     * @param abilities 物模型
     */
    public void doRefreshProductAbility(Integer productId, List<IotAbilityEntity> abilities){
        if (Objects.isNull(productId) || CollectionUtils.isEmpty(abilities)){
            return;
        }
        Map<String, IotAbilityEntity> abilitiyMap = abilities.stream().collect(Collectors.toMap(IotAbilityEntity::getProperty,e->e));
        this.productAbilityMap.put(productId,abilitiyMap);
    }

    /**
     * 查询指定产品下的物模型
     * @param productId 产品id
     * @return
     */
    public Map<String, IotAbilityEntity> getProductAbility(Integer productId){
        if (Objects.isNull(productId)){
            return Collections.EMPTY_MAP;
        }
        return this.productAbilityMap.getOrDefault(productId,Collections.EMPTY_MAP);
    }

    public void doRefreshClients(List<IotClientEntity> param) {
        if (CollectionUtils.isEmpty(param)) {
            return;
        }
        param.forEach(this::doRefreshClient);
    }

    public void removeClient(IotClientEntity param) {
        if (null == param) {
            return;
        }
        IotGatewayEntity gateway = param.getGateway();
        if (null == gateway) {
            return;
        }
        Integer clientId = param.getSerialNum();
        String uuid = gateway.getUuid();
        clients.remove(new ClientParam(uuid, clientId));
    }

    public void removeProductRule(Integer productId) {
        if (null == productId) {
            return;
        }
        productRuleMap.remove(productId);
    }

    public void removeClients(List<IotClientEntity> param) {
        if (param.isEmpty()) {
            return;
        }
        param.forEach(this::removeClient);
    }

    public void removeProductAbility(Integer productId){
        if (null == productId) {
            return;
        }
        productAbilityMap.remove(productId);
    }

    public IotClientEntity getClient(IotClientEntity param) {
        if (null == param) {
            return null;
        }
        IotGatewayEntity gateway = param.getGateway();
        if (null == gateway) {
            return null;
        }
        Integer clientId = param.getId();
        String uuid = gateway.getUuid();
        return clients.get(new ClientParam(uuid, clientId));
    }

    public void addOnlineGateway(String uuid) {
        if (!StringUtils.isEmpty(uuid)) {
            onlineGatewayMap.put(uuid, DateUtils.getCurrentDateTime(DateUtils.dateTimeFormat));
        }
    }

    public boolean isOnlineGateway(String uuid){
        if (StringUtils.isEmpty(uuid)){
            return false;
        }
        return onlineGatewayMap.containsKey(uuid);
    }

    public void removeOnlineGateway(String uuid){
        if (StringUtils.isEmpty(uuid)){
            return;
        }
        onlineGatewayMap.remove(uuid);
    }

    /**
     * 根据网关uuid，设备的序号查询设备
     * @param key
     * @return
     */
    public IotClientEntity getClients(ClientParam key) {
        return clients.get(key);
    }

    public List<IotWarningRulesEntity> getProductRule(int productId){
        return productRuleMap.get(productId);
    }

    public Map<String, String> getUnits() {
        return units;
    }

    /**
     * @描述 spring 容器启动后 本方法将执行，每间隔60分钟全量刷新一次
     * @参数 []
     * @返回值 void
     * @创建人 shmily
     * @创建时间 2020/8/2
     * @修改人和其它信息
     */
    public synchronized void refresh() {
        // 写入设备信息到内存
        IotClientEntity clientEntity = new IotClientEntity();
        // 必须是正常且为启用状态
        clientEntity.setRemove(1);
        clientEntity.setEnable(1);
        Long clientCount = iotClientDAO.queryCount(clientEntity);
        if (clientCount > 0) {
            clientEntity.setStartLine(0);
            clientEntity.setEndLine(clientCount.intValue());
            List<IotClientEntity> clientList = iotClientDAO.queryPage(clientEntity);
            doRefreshClients(clientList);
        } else {
            clients.clear();
        }

        // 推入产品属性到内存中
        IotProductTypeEntity productWhere = new IotProductTypeEntity();
        // 只显示正常在用的
        productWhere.setStatus(1);
        List<IotProductTypeEntity> productProperties= iotProductTypeDAO.queryProductProperties(productWhere);
        if (CollectionUtils.isEmpty(productProperties)) {
            productAbilityMap.clear();
        } else {
            for (IotProductTypeEntity item :productProperties) {
                this.doRefreshProductAbility(item.getId(),item.getProperties());
            }
        }

        // 推入产品告警规则到内存中
        List<IotProductTypeEntity> productRules = iotProductTypeDAO.queryProductRules(productWhere);
        if (CollectionUtils.isEmpty(productRules)){
            productRuleMap.clear();
        }else {
            for (IotProductTypeEntity item:productRules) {
                this.doRefreshProductRule(item.getId(),item.getRules());
            }
        }
    }

}
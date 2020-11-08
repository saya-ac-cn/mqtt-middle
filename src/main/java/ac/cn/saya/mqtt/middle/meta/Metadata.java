package ac.cn.saya.mqtt.middle.meta;

import ac.cn.saya.mqtt.middle.repository.IotClientDAO;
import ac.cn.saya.mqtt.middle.repository.IotWarningRulesDAO;
import ac.cn.saya.mqtt.middle.entity.IotClientEntity;
import ac.cn.saya.mqtt.middle.entity.IotGatewayEntity;
import ac.cn.saya.mqtt.middle.entity.IotWarningRulesEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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
    private IotWarningRulesDAO iotWarningRulesDAO;

    /**
     * 终端设备数据 内存元数据
     */
    private final Map<ClientParam, IotClientEntity> clients = new ConcurrentHashMap<>();

    /**
     * 告警规则数据 内存元数据
     */
    private final Map<Integer, IotWarningRulesEntity> rules = new ConcurrentHashMap<>();

    private final Map<String, String> onlineGateway = new ConcurrentHashMap<>();

    public void doRefreshClient(IotClientEntity param) {
        if (null == param) {
            return;
        }
        IotGatewayEntity gateway = param.getGateway();
        if (null == gateway) {
            return;
        }
        Integer clientId = param.getId();
        String code = gateway.getCode();
        clients.put(new ClientParam(code, clientId), param);
    }

    public void doRefreshClient(IotClientEntity oldParam,IotClientEntity newParam) {
        if (null == oldParam) {
            return;
        }
        IotGatewayEntity oldGateway = oldParam.getGateway();
        if (null == oldGateway) {
            return;
        }
        Integer oldClientId = newParam.getId();
        String oldCode = oldGateway.getCode();
        // 先移除之前的，然后进行添加操作
        clients.remove(new ClientParam(oldCode, oldClientId));
        if (null == newParam) {
            return;
        }
        IotGatewayEntity gateway = newParam.getGateway();
        if (null == gateway) {
            return;
        }
        Integer clientId = newParam.getId();
        String code = gateway.getCode();
        clients.put(new ClientParam(code, clientId), newParam);
    }

    public void doRefreshRule(IotWarningRulesEntity param) {
        if (null == param) {
            return;
        }
        rules.put(param.getId(), param);
    }

    public void doRefreshClients(List<IotClientEntity> param) {
        if (param.isEmpty()) {
            return;
        }
        param.forEach(this::doRefreshClient);
    }

    public void doRefreshRules(List<IotWarningRulesEntity> param) {
        if (param.isEmpty()) {
            return;
        }
        param.forEach(this::doRefreshRule);
    }

    public void removeClient(IotClientEntity param) {
        if (null == param) {
            return;
        }
        IotGatewayEntity gateway = param.getGateway();
        if (null == gateway) {
            return;
        }
        Integer clientId = param.getId();
        String code = gateway.getCode();
        clients.remove(new ClientParam(code, clientId));
    }

    public void removeRule(Integer param) {
        if (null == param) {
            return;
        }
        rules.remove(param);
    }

    public void removeClients(List<IotClientEntity> param) {
        if (param.isEmpty()) {
            return;
        }
        param.forEach(this::removeClient);
    }

    public void removeRules(List<Integer> param) {
        if (param.isEmpty()) {
            return;
        }
        param.forEach(this::removeRule);
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
        String code = gateway.getCode();
        return clients.get(new ClientParam(code, clientId));
    }

    public IotWarningRulesEntity getRule(Integer ruleId) {
        if (null == ruleId) {
            return null;
        }
        return rules.get(ruleId);
    }

    public void addOnlineGateway(String code, String onlineTime) {
        if (!StringUtils.isEmpty(code) && !StringUtils.isEmpty(onlineTime)) {
            onlineGateway.put(code, onlineTime);
        }
    }

    public boolean isOnlineGateway(String code){
        if (StringUtils.isEmpty(code)){
            return false;
        }
        return onlineGateway.containsKey(code);
    }

    /**
     * @描述 spring 容器启动后 本方法将执行，每间隔60分钟全量刷新一次
     * @参数 []
     * @返回值 void
     * @创建人 shmily
     * @创建时间 2020/8/2
     * @修改人和其它信息
     */
    public void refresh() {
        IotClientEntity clientEntity = new IotClientEntity();
        clientEntity.setRemove(1);
        Long clientCount = iotClientDAO.queryCount(clientEntity);
        if (clientCount > 0) {
            clientEntity.setStartLine(0);
            clientEntity.setEndLine(clientCount.intValue());
            List<IotClientEntity> clientList = iotClientDAO.queryPage(clientEntity);
            doRefreshClients(clientList);
        } else {
            clients.clear();
        }
        IotWarningRulesEntity ruleEntity = new IotWarningRulesEntity();
        ruleEntity.setEnable(1);
        Long ruleCount = iotWarningRulesDAO.queryCount(ruleEntity);
        if (clientCount > 0) {
            ruleEntity.setStartLine(0);
            ruleEntity.setEndLine(ruleCount.intValue());
            List<IotWarningRulesEntity> clientList = iotWarningRulesDAO.queryPage(ruleEntity);
            doRefreshRules(clientList);
        } else {
            rules.clear();
        }
    }

}
package ac.cn.saya.mqtt.middle.meta;

import ac.cn.saya.mqtt.middle.config.AppointmentScheduledPoolTask;
import ac.cn.saya.mqtt.middle.entity.*;
import ac.cn.saya.mqtt.middle.job.AppointmentThread;
import ac.cn.saya.mqtt.middle.repository.primary.IotAppointmentDAO;
import ac.cn.saya.mqtt.middle.repository.primary.IotClientDAO;
import ac.cn.saya.mqtt.middle.repository.primary.IotProductDAO;
import ac.cn.saya.mqtt.middle.tools.DateUtils;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;
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
    private IotAppointmentDAO iotAppointmentDAO;

    @Resource
    private IotProductDAO iotProductDAO;

    @Resource
    private ThreadPoolTaskScheduler threadPoolTaskScheduler;

    /**
     * 终端设备数据 内存元数据，key->iot_identify表的id，终端在物联网的统一认证uuid，value->设备详细信息
     */
    private final Map<String, IotClientEntity> clients = new ConcurrentHashMap<>();

    /**
     * 产品绑定的告警规则数据 内存元数据，key->产品id，value->这个产品下的告警规则
     */
    private final Map<Integer, List<IotWarningRulesEntity>> productRuleMap = new ConcurrentHashMap<>();

    /**
     * 产品以及下属的物模型内存元数据 key-> 产品id，value -> (属性字段，物模型)
     */
    private final Map<Integer, Map<String, IotAbilityEntity>> productAbilityMap = new ConcurrentHashMap<>();

    /**
     * 在线的设备 内存元数据，key->iot_identify表的id，终端在物联网的统一认证uuid，value->最后一次上报时间
     */
    private final Map<String, String> onlineClientMap = new ConcurrentHashMap<>();

    /**
     * 标准物理量分类
     */
    private final Map<String, String> units = new ConcurrentHashMap<>();


    public void doRefreshClient(IotClientEntity param) {
        if (null == param) {
            return;
        }
        if (StringUtils.hasText(param.getIdentifyUuid())) {
            return;
        }
        clients.put(param.getIdentifyUuid(), param);
    }

    public void doRefreshProductRule(int productId, List<IotWarningRulesEntity> param) {
        if (CollectionUtils.isEmpty(param)) {
            return;
        }
        productRuleMap.put(productId, param);
    }


    /**
     * 将产品以及下面的物模型推入缓存
     *
     * @param productId 产品id
     * @param abilities 物模型
     */
    public void doRefreshProductAbility(Integer productId, List<IotAbilityEntity> abilities) {
        if (Objects.isNull(productId) || CollectionUtils.isEmpty(abilities)) {
            return;
        }
        Map<String, IotAbilityEntity> abilitiyMap = abilities.stream().collect(Collectors.toMap(IotAbilityEntity::getProperty, e -> e));
        this.productAbilityMap.put(productId, abilitiyMap);
    }

    /**
     * 查询指定产品下的物模型
     *
     * @param productId 产品id
     * @return
     */
    public Map<String, IotAbilityEntity> getProductAbility(Integer productId) {
        if (Objects.isNull(productId)) {
            return Collections.EMPTY_MAP;
        }
        return this.productAbilityMap.getOrDefault(productId, Collections.EMPTY_MAP);
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
        if (StringUtils.hasText(param.getIdentifyUuid())) {
            return;
        }
        clients.remove(param.getIdentifyUuid());
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

    public void removeProductAbility(Integer productId) {
        if (null == productId) {
            return;
        }
        productAbilityMap.remove(productId);
    }

    public IotClientEntity getClient(IotClientEntity param) {
        if (null == param) {
            return null;
        }
        if (StringUtils.hasText(param.getIdentifyUuid())) {
            return null;
        }
        return clients.get(param.getIdentifyUuid());
    }

    public void addOnlineClient(String uuid) {
        if (!StringUtils.isEmpty(uuid)) {
            onlineClientMap.put(uuid, DateUtils.getCurrentDateTime(DateUtils.dateTimeFormat));
        }
    }

    public boolean isOnlineClient(String uuid) {
        if (StringUtils.isEmpty(uuid)) {
            return false;
        }
        return onlineClientMap.containsKey(uuid);
    }

    public void removeOnlineClient(String uuid) {
        if (StringUtils.hasText(uuid)) {
            return;
        }
        onlineClientMap.remove(uuid);
    }

    /**
     * 根据设备uuid，获取设备详情
     *
     * @param identifyUuid iot_identify表的id，终端在物联网的统一认证uuid
     * @return
     */
    public IotClientEntity getClients(String identifyUuid) {
        return clients.get(identifyUuid);
    }

    public List<IotWarningRulesEntity> getProductRule(int productId) {
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
        IotProductEntity productWhere = new IotProductEntity();
        // 只显示正常在用的
        productWhere.setStatus(1);
        List<IotProductEntity> productProperties = iotProductDAO.queryProductProperties(productWhere);
        if (CollectionUtils.isEmpty(productProperties)) {
            productAbilityMap.clear();
        } else {
            for (IotProductEntity item : productProperties) {
                this.doRefreshProductAbility(item.getId(), item.getProperties());
            }
        }

        // 推入产品告警规则到内存中
        List<IotProductEntity> productRules = iotProductDAO.queryProductRules(productWhere);
        if (CollectionUtils.isEmpty(productRules)) {
            productRuleMap.clear();
        } else {
            for (IotProductEntity item : productRules) {
                this.doRefreshProductRule(item.getId(), item.getRules());
            }
        }
    }

    /**
     * @描述 初始化预约执行指令到Scheduled定时任务队列
     * @参数 []
     * @返回值 void
     * @创建人 shmily
     * @创建时间 2021/12/4
     * @修改人和其它信息
     */
    public synchronized void initAppointmentScheduled() {
        List<IotAppointmentEntity> appointments = iotAppointmentDAO.queryList(new IotAppointmentEntity());
        if (CollectionUtils.isEmpty(appointments)) {
            return;
        }
        for (IotAppointmentEntity appointment : appointments) {
            IotClientEntity iotClient = appointment.getIotClient();
            if (Objects.isNull(iotClient)) {
                continue;
            }
            // 网关，设备id，定时cron，物模型字段，指令值都不能为空
            boolean flag = Objects.isNull(Objects.isNull(appointment.getClientId()) || StringUtils.hasText(appointment.getCron()) || Objects.isNull(appointment.getAbilityId()) || Objects.isNull(appointment.getCommand()));
            if (flag) {
                continue;
            }
            AppointmentThread task = new AppointmentThread(appointment);
            try {
                ScheduledFuture<?> schedule = threadPoolTaskScheduler.schedule(task, new CronTrigger(appointment.getCron()));
                AppointmentScheduledPoolTask.SCHEDULED_MAP.put(appointment.getCode(), schedule);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
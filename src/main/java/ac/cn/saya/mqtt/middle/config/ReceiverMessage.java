package ac.cn.saya.mqtt.middle.config;

import ac.cn.saya.mqtt.middle.entity.IotAbilityEntity;
import ac.cn.saya.mqtt.middle.entity.IotClientEntity;
import ac.cn.saya.mqtt.middle.entity.IotCollectionEntity;
import ac.cn.saya.mqtt.middle.meta.Metadata;
import ac.cn.saya.mqtt.middle.service.CollectionService;
import ac.cn.saya.mqtt.middle.tools.IOTException;
import ac.cn.saya.mqtt.middle.tools.JackJsonUtil;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.MessageProducer;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;

/**
 * @Title: ReceiverMessage
 * @ProjectName mqtt-middle
 * @Author shmily
 * @Date: 2020/7/8 22:17
 * @Description: 处理消息
 */

@Configuration
public class ReceiverMessage {

    @Resource
    private MqttConfig mqttConfig;

    @Resource
    @Qualifier("collectionService")
    private CollectionService collectionService;

    @Resource
    private Metadata metadata;

    @Resource
    private MqttPahoClientFactory mqttClientFactory;

    /**
     * 接收通道
     *
     * @return
     */
    @Bean
    public MessageChannel mqttInputChannel() {
        return new DirectChannel();
    }

    /**
     * 配置client,监听的topic
     *
     * @return
     */
    @Bean
    public MessageProducer inbound() {
        List<String> topicList = Arrays.asList((mqttConfig.getSubscribeDefaultTopic()).trim().split(","));
        String[] topics = new String[topicList.size()];
        topicList.toArray(topics);

        MqttPahoMessageDrivenChannelAdapter adapter =
                new MqttPahoMessageDrivenChannelAdapter(mqttConfig.getSubscribeClientId(),
                        mqttClientFactory,
                        topics);
        adapter.setCompletionTimeout(mqttConfig.getCompletionTimeout());
        adapter.setConverter(new DefaultPahoMessageConverter());
        adapter.setQos(1);
        adapter.setOutputChannel(mqttInputChannel());
        return adapter;
    }

    /**
     * 通过通道获取数据
     *
     * @return
     */
    @Bean
    @ServiceActivator(inputChannel = "mqttInputChannel")
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE, rollbackFor = IOTException.class)
    public MessageHandler handler() {
        return message -> {
            // mqtt_receivedTopic固定，不要随意改
            // 取出主题
            String topic = message.getHeaders().get("mqtt_receivedTopic").toString();
            String msg = message.getPayload().toString();
            System.out.println("----------------------------START---------------------------\n" +
                    "接收到订阅消息:\ntopic:" + topic + "\nmessage:" + msg +
                    "\n-----------------------------END----------------------------");
            // 提取设备在物联网认证库中唯一编号identifyUuid
            String identifyUuid = topic.substring(topic.lastIndexOf("/") + 1);
            // 在缓存中查询设备是否存在
            IotClientEntity client = metadata.getClients(identifyUuid);
            if (client == null || client.getProductId() == null) {
                // 设备为空！直接返回错误
                return;
            }
            // 通过产品id，拿到本产品下物模型
            Map<String, IotAbilityEntity> abilities = metadata.getProductAbility(client.getProductId());
            if (CollectionUtils.isEmpty(abilities)) {
                return;
            }
            JsonNode jsonNode = JackJsonUtil.readTree(msg);
            if (jsonNode == null) {
                return;
            }
            // 遍历处理本设备上报上来的多条指标数据
            Iterator<JsonNode> deviceReport = jsonNode.elements();
            // 提取上报数据
            List<IotCollectionEntity> datas = new ArrayList<>();
            while (deviceReport.hasNext()) {
                // 当前传感器上报的数据
                JsonNode sensor = deviceReport.next();
                // 处理本传感器上报的所有数据
                Iterator<Map.Entry<String, JsonNode>> propertyReport = sensor.fields();
                while (propertyReport.hasNext()) {
                    Map.Entry<String, JsonNode> node = propertyReport.next();
                    // 本传感器上报数据属性key
                    String property = node.getKey();
                    IotAbilityEntity ability = abilities.getOrDefault(property, null);
                    if (Objects.isNull(ability)) {
                        // 上报的数据不在物模型中时，不予加工处理
                        continue;
                    }
                    // 本设备上报数据值value
                    JsonNode nodeValue = node.getValue();
                    datas.add(new IotCollectionEntity(client.getId(), ability.getId(), nodeValue.asText("")));
                }
            }
            // 修改设备的上报时间
            collectionService.updateDeviceHeart(identifyUuid);
            collectionService.insertCollectionData(datas);
            collectionService.checkRuleWarring(datas);
        };
    }

}
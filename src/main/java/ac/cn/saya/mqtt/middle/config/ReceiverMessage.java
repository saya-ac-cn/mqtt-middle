package ac.cn.saya.mqtt.middle.config;

import ac.cn.saya.mqtt.middle.entity.IotAbilityEntity;
import ac.cn.saya.mqtt.middle.entity.IotClientEntity;
import ac.cn.saya.mqtt.middle.entity.IotCollectionEntity;
import ac.cn.saya.mqtt.middle.meta.ClientParam;
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
import org.springframework.messaging.MessageHeaders;
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
            // 提取网关唯一编号id
            String uuid = topic.substring(topic.lastIndexOf("/") + 1);
            JsonNode jsonNode = JackJsonUtil.readTree(msg);
            // 遍历处理本网关上报上来的各设备数据
            Iterator<Map.Entry<String, JsonNode>> deviceReport = jsonNode.fields();
            // 提取上报数据
            List<IotCollectionEntity> datas = new ArrayList<>();
            while(deviceReport.hasNext()) {
                Map.Entry<String, JsonNode> reports = deviceReport.next();
                // 设备在网关山的序号
                int serialNum = Integer.parseInt(reports.getKey());
                // 在缓存中查询设备是否存在
                IotClientEntity client = metadata.getClients(new ClientParam(uuid, serialNum));
                if (null == client){
                    continue;
                }
                // 修改上报时间
                collectionService.updateDeviceHeart(client.getId());
                // 通过产品id，拿到本产品下物模型
                Map<String, IotAbilityEntity> abilities = metadata.getProduct(client.getProductId());
                if (CollectionUtils.isEmpty(abilities)){
                    continue;
                }
                // 处理本序号下的上报数据
                JsonNode reportsValue = reports.getValue();
                // 本设备下的所有上报数据
                Iterator<JsonNode> clientIterator = reportsValue.elements();
                while(clientIterator.hasNext()) {
                    // 当前传感器上报的数据
                    JsonNode sensor = clientIterator.next();
                    // 处理本传感器上报的所有数据
                    Iterator<Map.Entry<String, JsonNode>> propertyReport = sensor.fields();
                    while(propertyReport.hasNext()) {
                        Map.Entry<String, JsonNode> node = propertyReport.next();
                        // 本传感器上报数据属性key
                        String property = node.getKey();
                        IotAbilityEntity ability = abilities.getOrDefault(property, null);
                        if (Objects.isNull(ability)){
                            // 上报的数据不在物模型中时，不予加工处理
                            continue;
                        }
                        // 本设备上报数据值value
                        JsonNode nodeValue = node.getValue();
                        datas.add(new IotCollectionEntity(client.getId(),ability.getId(),nodeValue.asText("")));
                    }
                }

            }
            // 修改网关的上报时间
            collectionService.updateGatewayHeart(uuid);
            collectionService.insertCollectionData(datas);
            collectionService.checkRuleWarring(datas);
        };
    }

}
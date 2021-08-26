package ac.cn.saya.mqtt.middle.config;

import ac.cn.saya.mqtt.middle.entity.IotClientEntity;
import ac.cn.saya.mqtt.middle.entity.IotCollectionEntity;
import ac.cn.saya.mqtt.middle.meta.ClientParam;
import ac.cn.saya.mqtt.middle.meta.Metadata;
import ac.cn.saya.mqtt.middle.service.CollectionService;
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

//@Configuration
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
    public MessageHandler handler() {
        return message -> {
            // mqtt_receivedTopic固定，不要随意改
            // 取出主题
            String topic = message.getHeaders().get("mqtt_receivedTopic").toString();
            String msg = message.getPayload().toString();
            System.out.println("----------------------------START---------------------------\n" +
                    "接收到订阅消息:\ntopic:" + topic + "\nmessage:" + msg +
                    "\n-----------------------------END----------------------------");
            // 提取网关编号id
            String uuid = topic.substring(topic.lastIndexOf("/") + 1);
            JsonNode jsonNode = JackJsonUtil.readTree(msg);
            JsonNode serialNumNode = jsonNode.get("serialNum");
            int serialNum = -1;
            // 检查数据是否完整（对于缺少序号的数据直接过滤）
            if (null == serialNumNode || -1 == (serialNum = serialNumNode.asInt(-1))){
                return;
            }
            // 在缓存中查询设备是否存在
            IotClientEntity client = metadata.getClients(new ClientParam(uuid, serialNum));
            if (null == client){
                return;
            }
            collectionService.updateDeviceHeart(client.getGatewayId(),client.getId());
            Iterator<Map.Entry<String, JsonNode>> fields = jsonNode.fields();
            // 提取上报数据
            List<IotCollectionEntity> datas = new ArrayList<>();
            while(fields.hasNext()) {
                Map.Entry<String, JsonNode> node = fields.next();
                String key = node.getKey();
                if ("serialNum".equals(key)){
                    continue;
                }
                JsonNode nodeValue = node.getValue();
                datas.add(new IotCollectionEntity(client.getId(),key,nodeValue.asText("")));
            }
            if (CollectionUtils.isEmpty(datas)){
                return;
            }
            collectionService.insertCollectionData(datas);
            collectionService.checkRuleWarring(client,datas);
        };
    }

}
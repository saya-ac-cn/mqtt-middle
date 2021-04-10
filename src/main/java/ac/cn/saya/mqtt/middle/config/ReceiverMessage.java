package ac.cn.saya.mqtt.middle.config;

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

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

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
            String topic = message.getHeaders().get("mqtt_receivedTopic").toString();
            String msg = message.getPayload().toString();
            System.out.println("----------------------------START---------------------------\n" +
                    "接收到订阅消息:\ntopic:" + topic + "\nmessage:" + msg +
                    "\n-----------------------------END----------------------------");
        };
    }

}

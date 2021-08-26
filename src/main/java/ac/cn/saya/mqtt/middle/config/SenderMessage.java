package ac.cn.saya.mqtt.middle.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

import javax.annotation.Resource;

/**
 * @Title: SenderMessage
 * @ProjectName mqtt-middle
 * @Author shmily
 * @Date: 2020/7/8 22:17
 * @Description: 发送消息
 */

//@Configuration
public class SenderMessage {

    @Resource
    private MqttConfig mqttConfig;

    @Resource
    private MqttPahoClientFactory mqttClientFactory;

    @Bean
    @ServiceActivator(inputChannel = "mqttOutboundChannel")
    public MessageHandler mqttOutbound() {
        MqttPahoMessageHandler messageHandler =  new MqttPahoMessageHandler(mqttConfig.getPushClientId(), mqttClientFactory);
        messageHandler.setAsync(true);
        messageHandler.setDefaultTopic(mqttConfig.getPushDefaultTopic());
        return messageHandler;
    }
    @Bean
    public MessageChannel mqttOutboundChannel() {
        return new DirectChannel();
    }

}

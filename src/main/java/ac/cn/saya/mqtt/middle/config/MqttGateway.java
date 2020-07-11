package ac.cn.saya.mqtt.middle.config;

import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.handler.annotation.Header;

/**
 * @Title: MqttGateway
 * @ProjectName mqtt-middle
 * @Description: TODO
 * @Author saya
 * @Date: 2020/7/8 22:18
 * @Description:
 */
@MessagingGateway(defaultRequestChannel = "mqttOutboundChannel")
public interface MqttGateway {

    public void sendToMqtt(String data,@Header(MqttHeaders.TOPIC) String topic);


}

package ac.cn.saya.mqtt.middle.controller;

import ac.cn.saya.mqtt.middle.config.MqttGateway;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Title: TestController
 * @ProjectName mqtt-middle
 * @Author saya
 * @Date: 2020/7/8 22:20
 * @Description:
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @Resource
    private MqttGateway mqttGateway;

    @RequestMapping("/send")
    public String sendMqtt(String sendData){
        try {
            mqttGateway.sendToMqtt(sendData,"/iot/go/ack/esp32-001");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "OK";
    }
}

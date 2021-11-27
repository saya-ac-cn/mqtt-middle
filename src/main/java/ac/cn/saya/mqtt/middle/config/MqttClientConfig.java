package ac.cn.saya.mqtt.middle.config;

import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * @Title: MqttClientConfig
 * @ProjectName mqtt-middle
 * @Description: TODO
 * @Author shmily
 * @Date: 2020/7/8 22:07
 * @Description: Mqtt
 */
//@Configuration
//@IntegrationComponentScan
public class MqttClientConfig {

    @Resource
    private MqttConfig mqttConfig;

    @Bean
    public MqttConnectOptions getMqttConnectOptions() {
        MqttConnectOptions options = new MqttConnectOptions();
        // 设置连接的用户名
        if (!(mqttConfig.getUsername()).trim().equals("")) {
            options.setUserName(mqttConfig.getUsername());
        }
        // 设置连接的密码
        options.setPassword((mqttConfig.getPassword()).toCharArray());
        // 设置连接的地址
        /// options.setServerURIs(StringUtils.split(mqttConfig.getHostUrl(), ","));
        List<String> urlList = Arrays.asList((mqttConfig.getHostUrl()).trim().split(","));
        String[] url = new String[urlList.size()];
        urlList.toArray(url);
        options.setServerURIs(url);
        // 设置超时时间 单位为秒
        options.setConnectionTimeout(mqttConfig.getCompletionTimeout());
        // 设置会话心跳时间 单位为秒 服务器会每隔1.5*20秒的时间向客户端发送心跳判断客户端是否在线
        // 但这个方法并没有重连的机制
        options.setKeepAliveInterval(mqttConfig.getKeepalive());
        options.setCleanSession(true);
        return options;
    }


    @Bean
    public MqttPahoClientFactory mqttClientFactory() {
        DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
        factory.setConnectionOptions(getMqttConnectOptions());
        return factory;
    }

}

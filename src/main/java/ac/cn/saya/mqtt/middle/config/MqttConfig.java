package ac.cn.saya.mqtt.middle.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @Title: MqttConfig
 * @ProjectName mqtt-middle
 * @Description: TODO
 * @Author saya
 * @Date: 2020/7/7 23:34
 * @Description:
 */
@Component
public class MqttConfig {

    @Value("${spring.mqtt.username}")
    private String username;

    @Value("${spring.mqtt.password}")
    private String password;

    @Value("${spring.mqtt.url}")
    private String hostUrl;

    @Value("${spring.mqtt.completionTimeout}")
    private int completionTimeout;

    @Value("${spring.mqtt.keepalive}")
    private int keepalive;

    @Value("${spring.mqtt.publish.client.id}")
    private String pushClientId;

    @Value("${spring.mqtt.publish.default.topic}")
    private String pushDefaultTopic;

    @Value("${spring.mqtt.subscribe.client.id}")
    private String subscribeClientId;

    @Value("${spring.mqtt.subscribe.default.topic}")
    private String subscribeDefaultTopic;

    public MqttConfig() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHostUrl() {
        return hostUrl;
    }

    public void setHostUrl(String hostUrl) {
        this.hostUrl = hostUrl;
    }

    public int getCompletionTimeout() {
        return completionTimeout;
    }

    public void setCompletionTimeout(int completionTimeout) {
        this.completionTimeout = completionTimeout;
    }

    public int getKeepalive() {
        return keepalive;
    }

    public void setKeepalive(int keepalive) {
        this.keepalive = keepalive;
    }

    public String getPushClientId() {
        return pushClientId;
    }

    public void setPushClientId(String pushClientId) {
        this.pushClientId = pushClientId;
    }

    public String getPushDefaultTopic() {
        return pushDefaultTopic;
    }

    public void setPushDefaultTopic(String pushDefaultTopic) {
        this.pushDefaultTopic = pushDefaultTopic;
    }

    public String getSubscribeClientId() {
        return subscribeClientId;
    }

    public void setSubscribeClientId(String subscribeClientId) {
        this.subscribeClientId = subscribeClientId;
    }

    public String getSubscribeDefaultTopic() {
        return subscribeDefaultTopic;
    }

    public void setSubscribeDefaultTopic(String subscribeDefaultTopic) {
        this.subscribeDefaultTopic = subscribeDefaultTopic;
    }
}

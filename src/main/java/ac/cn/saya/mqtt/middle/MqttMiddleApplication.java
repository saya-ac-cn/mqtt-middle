package ac.cn.saya.mqtt.middle;

import ac.cn.saya.mqtt.middle.tools.CurrentLineInfo;
import ac.cn.saya.mqtt.middle.tools.Log4jUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;


/**
 * @描述 消息中间值服务（mqtt）
 * @参数
 * @返回值
 * @创建人  saya.ac.cn-刘能凯
 * @创建时间  2020/7/7
 * @修改人和其它信息：
 */

//// 移除 @SpringBootApplication and @ComponentScan, 用 @EnableAutoConfiguration 来替代
//@SpringBootApplication
@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = {"ac.cn.saya.mqtt.middle"})
@EnableTransactionManagement //开启声明式事务
@EnableScheduling
public class MqttMiddleApplication {

    private final static Logger logger = LoggerFactory.getLogger(MqttMiddleApplication.class);

    public static void main(String[] args) {
        try {
            ///SpringApplication.run(MqttMiddleApplication.class, args);
            SpringApplication springApplication = new SpringApplication(MqttMiddleApplication.class);
            // 禁止命令行设置参数
            springApplication.setAddCommandLineProperties(false);
            springApplication.run(args);
            //项目启动完成打印项目名
            logger.warn("实验室中心已经启动 ... ");
        } catch (Exception e) {
            logger.error("实验室中心已经启动失败:{}", Log4jUtils.getTrace(e));
            logger.error(CurrentLineInfo.printCurrentLineInfo());
        }
    }

}

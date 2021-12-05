package ac.cn.saya.mqtt.middle.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;

/**
 * Scheduled定时任务队列
 * @Title: CommandSchedulerPoolTask
 * @ProjectName mqtt-middle
 * @Author saya
 * @Date: 2021/12/4 17:26
 * @Description: TODO
 * https://blog.csdn.net/szzssz/article/details/81202363
 */
@Configuration
public class AppointmentScheduledPoolTask {

    public static final Map<String, ScheduledFuture<?>> SCHEDULED_MAP = new HashMap<>();

    @Bean
    public ThreadPoolTaskScheduler threadPoolTaskScheduler() {
        return new ThreadPoolTaskScheduler();
    }

}

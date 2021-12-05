package ac.cn.saya.mqtt.middle;

import ac.cn.saya.mqtt.middle.meta.Metadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @Title: MetaStartupRunner
 * @ProjectName lab
 * @Description: TODO
 * @Author shmily
 * @Date: 2020/8/2 17:03
 * @Description: spring boot 启动完成后 执行的初始化元数据
 */
@Component
public class MetaStartupRunner implements CommandLineRunner {

    @Autowired
    private Metadata metadata;

    @Override
    public void run(String... args){
        // 刷新设备元数据
        metadata.refresh();
        // 初始化预约执行指令到Scheduled定时任务队列
        metadata.initAppointmentScheduled();
    }
}

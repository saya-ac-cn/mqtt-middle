package ac.cn.saya.mqtt.middle;

import ac.cn.saya.mqtt.middle.meta.Metadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

/**
 * @Title: MetaStartupRunner
 * @ProjectName lab
 * @Description: TODO
 * @Author shmily
 * @Date: 2020/8/2 17:03
 * @Description: spring boot 启动完成后 执行的初始化元数据
 */

public class MetaStartupRunner implements CommandLineRunner {

    @Autowired
    private Metadata metadata;

    @Override
    public void run(String... args) throws Exception {
        metadata.refresh();
    }
}

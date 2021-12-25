package ac.cn.saya.mqtt.middle.datasource;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Title: IdentifyDataSourceWrapper
 * @ProjectName mqtt-middle
 * @Description: TODO
 * @Author liunengkai
 * @Date: 2021-12-25 13:30
 * @Description: 身份认证-物联网数据库数据源配置
 */
@ConfigurationProperties(prefix = "spring.datasource.identify")
public class IdentifyDataSourceWrapper extends HikariDataSource implements InitializingBean {

    private static final long serialVersionUID = -4382148522964973815L;
    @Autowired
    private DataSourceProperties basicProperties;



    @Override
    public void afterPropertiesSet() throws Exception {
        // 如果未找到前缀“spring.datasource.identify”JDBC属性，将使用“Spring.DataSource”前缀JDBC属性。l
        if (super.getUsername() == null) {
            super.setUsername(basicProperties.determineUsername());
        }
        if (super.getPassword() == null) {
            super.setPassword(basicProperties.determinePassword());
        }
        if (super.getJdbcUrl() == null) {
            super.setJdbcUrl(basicProperties.determineUrl());
        }
        if (super.getDriverClassName() == null) {
            super.setDriverClassName(basicProperties.getDriverClassName());
        }
    }

}

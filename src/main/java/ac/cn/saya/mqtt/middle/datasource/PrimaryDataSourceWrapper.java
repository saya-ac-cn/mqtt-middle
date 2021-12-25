package ac.cn.saya.mqtt.middle.datasource;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;


/**
 * @Title: PrimaryDataSourceWrapper
 * @ProjectName mqtt-middle
 * @Description: TODO
 * @Author liunengkai
 * @Date: 2021-12-25 13:33
 * @Description: 核心主数据库数据源配置
 */
@ConfigurationProperties(prefix = "spring.datasource.primary")
public class PrimaryDataSourceWrapper extends HikariDataSource implements InitializingBean {

    private static final long serialVersionUID = 2957957409906618805L;
    @Autowired
    private DataSourceProperties basicProperties;

    @Override
    public void afterPropertiesSet() throws Exception {
        // 如果未找到前缀“spring.datasource.primary”JDBC属性，将使用“Spring.DataSource”前缀JDBC属性。
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

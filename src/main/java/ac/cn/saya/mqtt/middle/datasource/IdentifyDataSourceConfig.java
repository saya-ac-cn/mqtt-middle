package ac.cn.saya.mqtt.middle.datasource;

import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * @Title: IdentifyDataSourceConfig
 * @ProjectName mqtt-middle
 * @Description: TODO
 * @Author liunengkai
 * @Date: 2021-12-25 13:32
 * @Description: 身份认证-物联网数据源配置
 */
@Configuration
@MapperScan(basePackages = "ac.cn.saya.mqtt.middle.repository.identify", sqlSessionTemplateRef = "identifySqlSessionTemplate")
public class IdentifyDataSourceConfig {

    public static IdentifyDataSourceConfig create() {
        return new IdentifyDataSourceConfig();
    }

    /**
     * 创建财政数据库数据源
     */
    public HikariDataSource build() {
        return new IdentifyDataSourceWrapper();
    }

    @Bean(name = "identifyDataSource")
    public DataSource identifyDateSource() {
        return IdentifyDataSourceConfig.create().build();
    }

    @Bean(name = "identifySqlSessionFactory")
    public SqlSessionFactory identifySqlSessionFactory(@Qualifier("identifyDataSource") DataSource datasource)
            throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(datasource);
        //设置mybatis配置文件路径
        bean.setConfigLocation(new PathMatchingResourcePatternResolver().getResource("classpath:mybatis-config.xml"));
        //设置对应的xml文件位置
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:mapper/identify/*.xml"));
        return bean.getObject();
    }

    @Bean
    public DataSourceTransactionManager identifyTransactionManager(@Qualifier("identifyDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }


    @Bean("identifySqlSessionTemplate")
    public SqlSessionTemplate identifySqlSessionTemplate(
            @Qualifier("identifySqlSessionFactory") SqlSessionFactory sessionfactory) {
        return new SqlSessionTemplate(sessionfactory);
    }

}

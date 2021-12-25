package ac.cn.saya.mqtt.middle.datasource;

import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
/**
 * @Title: PrimaryDataSourceConfig
 * @ProjectName mqtt-middle
 * @Description: TODO
 * @Author liunengkai
 * @Date: 2021-12-25 13:34
 * @Description: 主数据源配置
 * https://www.cnblogs.com/ziyue7575/p/549bc1f2e0996ed979bd09c25a6a26c0.html
 */

@Configuration
@MapperScan(basePackages = {"ac.cn.saya.mqtt.middle.repository.primary"}, sqlSessionTemplateRef = "primarySqlSessionTemplate")
public class PrimaryDataSourceConfig {

    public static PrimaryDataSourceConfig create() {
        return new PrimaryDataSourceConfig();
    }

    /**
     * 创建物联网数据库数据源
     */
    public HikariDataSource build() {
        return new PrimaryDataSourceWrapper();
    }


    /**
     * 表示这个数据源是默认数据源,这个一定要加，如果两个数据源都没有@Primary会报错
     */
    @Primary
    @Bean(name = "primaryDataSource")
    public DataSource primaryDateSource() {
        return PrimaryDataSourceConfig.create().build();
    }


    @Bean(name = "primarySqlSessionFactory")
    @Primary
    public SqlSessionFactory primarySqlSessionFactory(@Qualifier("primaryDataSource") DataSource datasource)
            throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(datasource);
        //设置mybatis配置文件路径
        bean.setConfigLocation(new PathMatchingResourcePatternResolver().getResource("classpath:mybatis-config.xml"));
        //设置对应的xml文件位置
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:mapper/primary/*.xml"));
        return bean.getObject();
    }

    @Bean
    @Primary
    public DataSourceTransactionManager primaryTransactionManager(@Qualifier("primaryDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }


    @Primary
    @Bean("primarySqlSessionTemplate")
    public SqlSessionTemplate primarySqlSessionTemplate(
            @Qualifier("primarySqlSessionFactory") SqlSessionFactory sessionfactory) {
        return new SqlSessionTemplate(sessionfactory);
    }

}

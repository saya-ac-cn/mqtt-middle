package ac.cn.saya.mqtt.middle.repository.primary;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Qualifier;

import javax.annotation.Resource;

/**
 * @Title: PrimaryJDBCConnection
 * @ProjectName mqtt-middle
 * @Description: TODO
 * @Author saya
 * @Date: 2020-09-22 22:51
 * @Description:
 * 基类Dao
 * 自动实现相关的Mybatis变量
 * 把配置文件中的been注入到此
 */

public class PrimaryJDBCConnection {

    @Resource
    @Qualifier("primarySqlSessionFactory")
    private SqlSessionFactory sqlSessionFactory;

    public SqlSessionFactory getSqlSessionFactory() {
        return sqlSessionFactory;
    }

    public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    public SqlSession getSqlSession(){
        //打开SqlSession会话
        SqlSession session = sqlSessionFactory.openSession();
        return session;
    }

}

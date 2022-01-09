package ac.cn.saya.mqtt.middle.repository.primary;

import ac.cn.saya.mqtt.middle.service.impl.SystemServiceImpl;
import ac.cn.saya.mqtt.middle.tools.CurrentLineInfo;
import ac.cn.saya.mqtt.middle.tools.IOTException;
import ac.cn.saya.mqtt.middle.tools.ResultEnum;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Title: PrimaryBatchDAO
 * @ProjectName mqtt-middle
 * @Description: TODO
 * @Author saya
 * @Date: 2020-09-22 22:51
 * @Description:
 */

@Repository("primaryBatchDAO")
public class PrimaryBatchDAO extends PrimaryJDBCConnection {

    /**
     * 调用存储过程查询近7天的数据上报情况
     *
     * @return 近7天的数据上报字典
     */
    public Map<String, Object> countPre7DayCollect() {
        Map<String, Object> result = null;
        try (SqlSession sqlSession = getSqlSession(); Connection sqlCon = sqlSession.getConfiguration().getEnvironment().getDataSource().getConnection()) {
            CallableStatement cs = sqlCon.prepareCall("{Call countPre7Collect()}");
            //执行
            cs.executeQuery();
            ResultSet rs = cs.getResultSet();
            result = new LinkedHashMap();
            while (rs.next()) {
                result.put(rs.getString("date"), rs.getLong("count"));
            }
            cs.close();
            return result;
        } catch (Exception e) {
            CurrentLineInfo.printCurrentLineInfo("调用存储过程查询近7天的数据上报情况发生异常", e, PrimaryBatchDAO.class);
            throw new IOTException(ResultEnum.ERROR);
        }
    }

}

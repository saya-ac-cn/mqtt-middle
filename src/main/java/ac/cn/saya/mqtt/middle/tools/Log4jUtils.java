package ac.cn.saya.mqtt.middle.tools;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Optional;

/**
 * @Title: Log4jUtils
 * @ProjectName DataCenter
 * @Description: TODO
 * @Author Saya
 * @Date: 2018/9/29 23:05
 * @Description:
 */

public class Log4jUtils {

    /**
     * log4j日志记录
     * @param t
     * @return
     */
    public static Optional<String> getTrace(Throwable t) {
        StringBuffer buffer = null;

        try (StringWriter stringWriter = new StringWriter();PrintWriter writer = new PrintWriter(stringWriter)) {
            t.printStackTrace(writer);
            buffer = stringWriter.getBuffer();
        }catch (IOException e){
            buffer = new StringBuffer(e.getMessage());
        }
        // 非空返回
        return Optional.ofNullable(buffer.toString());
    }

}

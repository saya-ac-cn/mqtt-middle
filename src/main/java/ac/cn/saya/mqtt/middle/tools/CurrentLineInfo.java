package ac.cn.saya.mqtt.middle.tools;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Optional;

/**
 * @Title: CurrentLineInfo
 * @ProjectName DataCenter
 * @Author shmily
 * @Date: 2018/10/20 21:59
 * @Description:
 */

public class CurrentLineInfo {

    private static int originStackIndex = 0;

    /**
     * log4j日志记录
     * @param t
     * @return
     */
    public static Optional<String> getStackTrace(Throwable t) {
        StringBuffer buffer = null;
        try (StringWriter stringWriter = new StringWriter();PrintWriter writer = new PrintWriter(stringWriter)) {
            t.printStackTrace(writer);
            buffer = stringWriter.getBuffer();
        }catch (IOException e){
            buffer = new StringBuffer(e.getMessage());
        }
        return Optional.ofNullable(buffer.toString());
    }

    /**
     * @Desc: 异常打印日志 ，提供给打印非正常异常
     * @Author shmily
     * @Date 2020/11/15  下午10:44
     */

    public static void printCurrentLineInfo(String errorTitle,Throwable e,Class c){
        Logger logger = LoggerFactory.getLogger(c);
        StackTraceElement s= e.getStackTrace()[originStackIndex];
        StringBuffer errorString = new StringBuffer(errorTitle);
        errorString.append("\n------------开始------------");
        errorString.append("\n出错文件名：").append(s.getFileName());
        errorString.append("\n出错的类：").append(s.getClassName());
        errorString.append("\n出错方法：").append(s.getMethodName());
        errorString.append("\n出错的行：").append(s.getLineNumber());
        errorString.append("\n出错信息：").append(e.getMessage());
        errorString.append("\n错误堆栈：").append(getStackTrace(e));
        errorString.append("\n------------结束------------");
        logger.error(errorString.toString());
    }


    public static void printCurrentLineInfo(String warnTitle,Class c,String ...tags){
        Logger logger = LoggerFactory.getLogger(c);
        StringBuffer warnString = new StringBuffer(warnTitle);
        for (int i = 0; i < tags.length; i++) {
            warnString.append(",").append(tags[i]);
        }
        logger.warn(warnString.toString());
    }

}

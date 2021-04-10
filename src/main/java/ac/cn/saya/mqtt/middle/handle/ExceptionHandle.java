package ac.cn.saya.mqtt.middle.handle;

import ac.cn.saya.mqtt.middle.tools.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Title: ExceptionHandle
 * @ProjectName mqtt-middle
 * @Author shmily
 * @Date: 2020/11/8 22:16
 * @Description: 全局控制的拦截器，主要用于对异常的处理-除了在此要配置外，还要在dispatcher-servlet中配置，让它能正常扫描到。
 */

@ControllerAdvice
public class ExceptionHandle {


    private static final Logger logger = LoggerFactory.getLogger(ExceptionHandle.class);

    @ExceptionHandler(value = Exception.class)

    @ResponseBody
    public Result<Object> handle(Exception e) {
        if (e instanceof IOTException) {
            IOTException exception = (IOTException) e;
            CurrentLineInfo.printCurrentLineInfo("在顶层捕获的异常",e,ExceptionHandle.class);
            return ResultUtil.error(exception.getCode(), exception.getMessage());
        } else {
            //不在定义范围内的异常错误
            CurrentLineInfo.printCurrentLineInfo("未能捕获的异常错误",e,ExceptionHandle.class);
            return ResultUtil.error(-1, "未知错误");
        }
    }

}
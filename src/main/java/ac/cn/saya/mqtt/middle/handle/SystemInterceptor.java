package ac.cn.saya.mqtt.middle.handle;
import ac.cn.saya.mqtt.middle.entity.IotUserEntity;
import ac.cn.saya.mqtt.middle.tools.HttpRequestUtil;
import ac.cn.saya.mqtt.middle.tools.ResultUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * @Title: SystemInterceptor
 * @ProjectName mqtt-middle
 * @Author shmily
 * @Date: 2018/9/24 23:24
 * @Description: 系统拦截器
 */

public class SystemInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(SystemInterceptor.class);

    private final static ObjectMapper jsonMapper = new ObjectMapper();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        IotUserEntity memoryUser = HttpRequestUtil.getMemoryUser(request);
        if (null != memoryUser) {
            return true;
        } else {
            /// logger.warn("controller tell you Please login");
            /// request.getRequestDispatcher("/login.html").forward(request, response);
            /// 设置将字符以"UTF-8"编码输出到客户端浏览器
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=utf-8");
            // 401（未授权） 请求要求身份验证
            response.setStatus(401);
            //获取PrintWriter输出流
            PrintWriter out = response.getWriter();
            out.write(jsonMapper.writeValueAsString(ResultUtil.error(-7, "请登录")));
            out.close();
            ///response.sendRedirect("/login.html");
            return false;
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        logger.info("处理请求完成后视图渲染之前的处理操作 ");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        logger.warn("视图渲染之后的操作");
    }
}

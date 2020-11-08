package ac.cn.saya.mqtt.middle.handle;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.HttpPutFormContentFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.io.File;

/**
 * @Title: WebConfigurerAdapter
 * @ProjectName mqtt-middle
 * @Author shmily
 * @Date: 2018/9/24 23:21
 * @Description: 使用WebMvcConfigurationSupport替代WebMvcConfigurerAdapter;
 */
@Configuration
public class WebConfigurerAdapter extends WebMvcConfigurationSupport {

    /**
     * 允许put
     *
     * @return
     */
    @Bean
    public HttpPutFormContentFilter httpPutFormContentFilter() {
        return new HttpPutFormContentFilter();
    }


    /**
     * 注册拦截器
     *
     * @param registry
     */
    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        //注册自定义拦截器，添加拦截路径和排除拦截路径
        registry.addInterceptor(new SystemInterceptor()).addPathPatterns("/backend/**").excludePathPatterns("/backend/login/*");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //将所有/files/** 访问都映射到classpath:/static/ 目录下
        ///registry.addResourceHandler("/files/**").addResourceLocations("classpath:/files/");
        /**
         * 资源映射路径
         * addResourceHandler：访问映射路径
         * addResourceLocations：资源绝对路径
         */
        registry.addResourceHandler("/warehouse/**").addResourceLocations("file:" + System.getProperty("user.home", "/home/saya") + File.separator + "warehouse" + File.separator);
        super.addResourceHandlers(registry);
    }


}

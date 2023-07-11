package cn.edu.xmut.config;

//import com.nowcoder.community.controller.interceptor.AlphaInterceptor;
//import com.nowcoder.community.controller.interceptor.LoginRequiredInterceptor;
//import com.nowcoder.community.controller.interceptor.LoginTicketInterceptor;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
/*
配置类：
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Value("${upload}")
    private String uploadPattern;
    @Value("${upload.location}")
    private String uploadLocation;
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(uploadPattern).addResourceLocations("file:"+uploadLocation);
    }
}

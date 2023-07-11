package cn.edu.xmut.config;
import cn.edu.xmut.interceptor.TokenInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class TokenConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new TokenInterceptor())
                .addPathPatterns("/**")    // 拦截以 /api/ 开头的所有请求
                .excludePathPatterns("/soft/user/**")  // 排除特定的请求，如 /api/login
                .excludePathPatterns("/soft/book/**")  // 排除特定的请求，如 /api/register
                .excludePathPatterns("/soft/review-book/**")
                .excludePathPatterns("/save")
                .excludePathPatterns("/upload")
                .excludePathPatterns("/soft/sale/**");
    }
}
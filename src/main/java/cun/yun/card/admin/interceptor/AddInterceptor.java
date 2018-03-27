package cun.yun.card.admin.interceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
@Configuration
public class AddInterceptor extends WebMvcConfigurerAdapter {
    @Bean
    public HandlerInterceptor getTokenInterceptor(){
        return new AuthInterceptor();
    }
    /**
     * 注册 拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration interceptorRegistry = registry.addInterceptor(getTokenInterceptor());
        interceptorRegistry.excludePathPatterns("/admin/login");
        interceptorRegistry.excludePathPatterns("/admin/logout");
        interceptorRegistry.excludePathPatterns("/sms/service/check");
        interceptorRegistry.addPathPatterns("/*/**");
//        registry.addInterceptor(new AuthInterceptor()).addPathPatterns("/*/**").excludePathPatterns("/admin/login","/admin/logout","/sms/service/check");
        super.addInterceptors(registry);
    }
}

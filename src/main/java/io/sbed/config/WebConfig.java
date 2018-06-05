package io.sbed.config;

import io.sbed.common.utils.FileUtils;
import io.sbed.common.xss.XssFilter;
import io.sbed.modules.api.intercept.ApiInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.servlet.DispatcherType;

/**
 * @author heguoliang
 * @Description: TODO(web配置)
 * @date 2017-6-23 15:07
 */
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

    @Autowired
    private ApiInterceptor apiInterceptor;

    @Value("${sys.swagger.open:#{false}}")
    private boolean swaggerOpen;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //映射上传文件的地址
        registry.addResourceHandler("/uploadFile/**").addResourceLocations("file:"+FileUtils.getTempPath());
        //映射swagger
        if(swaggerOpen){
            registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
            registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
        }
        super.addResourceHandlers(registry);
    }

    @Bean
    public FilterRegistrationBean shiroFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new DelegatingFilterProxy("shiroFilter"));
        //该值缺省为false，表示生命周期由SpringApplicationContext管理，设置为true则表示由ServletContainer管理
        registration.addInitParameter("targetFilterLifecycle", "true");
        registration.setEnabled(true);
        registration.setOrder(Integer.MAX_VALUE - 1);
        registration.addUrlPatterns("/*");
        return registration;
    }

    //配置xssFilter
    @Bean
    public FilterRegistrationBean xssFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setDispatcherTypes(DispatcherType.REQUEST);
        registration.setFilter(new XssFilter("/sys/"));
        registration.addUrlPatterns("/*");
        registration.setName("xssFilter");
        registration.setOrder(Integer.MAX_VALUE);
        return registration;
    }

    //添加api interceptor
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(apiInterceptor).addPathPatterns("/api/**");
    }

}
package io.sbed.config;

import io.sbed.common.shiro.*;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.mgt.SubjectFactory;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Description: XXXXX <br>
 * Copyright:DATANG SOFTWARE CO.LTD<br>
 *
 * @author fuxiangming
 * @date 2018/6/14 下午4:10
 */
@Configuration
@Order(1)
public class ShiroConfig {

    @Bean("sessionManager")
    public SessionManager sessionManager() {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setSessionValidationSchedulerEnabled(false);
        sessionManager.setSessionIdUrlRewritingEnabled(false);
        sessionManager.setSessionIdCookieEnabled(false);
        return sessionManager;
    }


    @Bean
    public SecurityManager securityManager(@Qualifier("sessionManager") SessionManager sessionManager,
                                           @Qualifier("myShiroRealm") ShiroRealm realm,
                                           @Qualifier("redisCacheManager") ShiroRedisCacheManager redisCacheManager,
                                           @Qualifier("subjectFactory") SubjectFactory subjectFactory
    ) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //注入缓存管理器
        securityManager.setCacheManager(redisCacheManager);
        // 设置realm.
        securityManager.setRealm(realm);
//        // 关闭shiro自带的session
        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
        DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = new DefaultSessionStorageEvaluator();
        defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
        subjectDAO.setSessionStorageEvaluator(defaultSessionStorageEvaluator);
        securityManager.setSubjectDAO(subjectDAO);
        securityManager.setSubjectFactory(subjectFactory);
//        securityManager.setSessionManager(sessionManager);
//        securityManager.setSessionMode(DefaultWebSecurityManager.NATIVE_SESSION_MODE);

        return securityManager;
    }

    /**
     * ShiroFilterFactoryBean 处理拦截资源文件问题。
     * 注意：单独一个ShiroFilterFactoryBean配置是或报错的，以为在
     * 初始化ShiroFilterFactoryBean的时候需要注入：SecurityManager Filter Chain定义说明
     * 1、一个URL可以配置多个Filter，使用逗号分隔
     * 2、当设置多个过滤器时，全部验证通过，才视为通过
     * 3、部分过滤器可指定参数，如perms，roles
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {

        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        // 必须设置 SecurityManager
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        //验证码过滤器
        Map<String, Filter> shiroFilterMap = shiroFilterFactoryBean.getFilters();
//        shiroFilterMap.put("jwt", new ShiroAuthenticatingFilter());
        shiroFilterMap.put("authc", new ShiroFormAuthenticationFilter());
        shiroFilterMap.put("logout", new ShiroLogoutFilter());
        shiroFilterFactoryBean.setFilters(shiroFilterMap);

        //登录
        shiroFilterFactoryBean.setLoginUrl("/sys/login");

        // 拦截器
        Map<String, String> filterMap = new LinkedHashMap<String, String>();
        filterMap.put("/api/**", "anon");
        filterMap.put("/swagger-resources/**", "anon");//swagger
        filterMap.put("/v2/**", "anon");//swagger
        filterMap.put("/webjars/**", "anon");//swagger
        filterMap.put("/**/druid/**", "anon");
        filterMap.put("/sys/getLoginErrorTimes", "anon");
        filterMap.put("/**/*.css", "anon");
        filterMap.put("/**/*.js", "anon");
        filterMap.put("/**/*.html", "anon");
        filterMap.put("/fonts/**", "anon");
        filterMap.put("/plugins/**", "anon");
        filterMap.put("/favicon.ico", "anon");
        filterMap.put("/captcha.jpg", "anon");
        filterMap.put("/image/**", "anon");
        filterMap.put("/uploadFile/**", "anon");//上传文件映射的地址
        filterMap.put("/admin", "anon");
        filterMap.put("/js/**", "anon");
        filterMap.put("/css/**", "anon");
        filterMap.put("/modules/**", "anon");
        filterMap.put("**/*.html", "anon");
        //登录
//        filterMap.put("/sys/login", "anon");
        //退出
        filterMap.put("/sys/logout", "logout");
        // 访问401和404页面不通过我们的Filter
        filterMap.put("/401", "anon");
        filterMap.put("/404", "anon");
        // 其他的
//        filterMap.put("/**", "jwt");
        filterMap.put("/**", "authc");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterMap);
        return shiroFilterFactoryBean;
    }

    @Bean("lifecycleBeanPostProcessor")
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator proxyCreator = new DefaultAdvisorAutoProxyCreator();
        proxyCreator.setProxyTargetClass(true);
        return proxyCreator;
    }

    /**
     * 身份认证realm; (这个需要自己写，账号密码校验；权限等)
     */
    @Bean(name = "myShiroRealm")
    public ShiroRealm myShiroRealm(@Qualifier("credentialsMatcher") CredentialsMatcher matcher) {
        ShiroRealm myShiroRealm = new ShiroRealm();
        myShiroRealm.setCredentialsMatcher(matcher);
        return myShiroRealm;
    }

    @Bean(name = "subjectFactory")
    public SubjectFactory subjectFactory() {
        SubjectFactory subjectFactory = new StatelessDefaultSubjectFactory();
        return subjectFactory;
    }

    /**
     * 开启shiro aop注解支持. 使用代理方式; 所以需要开启代码支持;
     *
     * @param securityManager 安全管理器
     * @return 授权Advisor
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    /**
     * shiro缓存管理器;
     * 需要注入对应的其它的实体类中：
     * 1、安全管理器：securityManager
     * 可见securityManager是整个shiro的核心；
     *
     * @return
     */
//    @Bean
//    public EhCacheManager ehCacheManager() {
//        EhCacheManager cacheManager = new EhCacheManager();
//        cacheManager.setCacheManagerConfigFile("classpath:ehcache.xml");
//        return cacheManager;
//    }
    @Bean(name = "redisCacheManager")
    public ShiroRedisCacheManager redisCacheManager() {
        ShiroRedisCacheManager redisCacheManager = new ShiroRedisCacheManager();
        return redisCacheManager;
    }

//    //配置自定义的权限登录器
//    @Bean(name="shiroRealm")
//    public ShiroRealm shiroRealm(@Qualifier("credentialsMatcher") CredentialsMatcher matcher) {
//        ShiroRealm shiroRealm=new ShiroRealm();
//        shiroRealm.setCredentialsMatcher(matcher);
//        return shiroRealm;
//    }

    //配置自定义的密码比较器
    @Bean(name = "credentialsMatcher")
    public CredentialsMatcher credentialsMatcher() {
        return new CredentialsMatcher();
    }


}
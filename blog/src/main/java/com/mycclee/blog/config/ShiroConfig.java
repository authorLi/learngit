package com.mycclee.blog.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import com.mycclee.blog.realm.AuthRealm;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.apache.shiro.session.mgt.eis.JavaUuidSessionIdGenerator;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

    @Bean
    public ShiroDialect shiroDialect(){
        return new ShiroDialect();
    }

    @Bean("shiroFilter")
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        Map<String,String> filterChainDefinitionMap = new LinkedHashMap<>();
        filterChainDefinitionMap.put("/logout","logout");
        filterChainDefinitionMap.put("/","anon");
        filterChainDefinitionMap.put("/index","anon");
        filterChainDefinitionMap.put("/login","anon");
        filterChainDefinitionMap.put("/register","anon");
        filterChainDefinitionMap.put("/user/add","anon");
        filterChainDefinitionMap.put("/user/edit","anon");

        filterChainDefinitionMap.put("/css/**","anon");
        filterChainDefinitionMap.put("/data/**","anon");
        filterChainDefinitionMap.put("/fonts/**","anon");
        filterChainDefinitionMap.put("/images/**","anon");

        filterChainDefinitionMap.put("/js/**","anon");

        filterChainDefinitionMap.put("/admins/**","roles[admin]");
//        filterChainDefinitionMap.put("/admins/**","anon");
        filterChainDefinitionMap.put("/**","authc");



        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        shiroFilterFactoryBean.setLoginUrl("/login");
        shiroFilterFactoryBean.setSuccessUrl("/index");
        return shiroFilterFactoryBean;
    }

    @Bean
    public Realm realm(){
        return new AuthRealm();
    }

    @Bean
    public SecurityManager securityManager(){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(realm());

        securityManager.setSessionManager(sessionManager());
        securityManager.setCacheManager(cacheManager());
        return securityManager;
    }

    @Bean
    public SessionManager sessionManager(){
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setGlobalSessionTimeout(1800000);
        sessionManager.setSessionIdCookieEnabled(true);
        sessionManager.setSessionIdCookie(simpleCookie());
        sessionManager.setCacheManager(cacheManager());
        sessionManager.setSessionIdUrlRewritingEnabled(true);
        sessionManager.setSessionDAO(sessionDAO());
        return sessionManager;
    }

    @Bean
    public SessionDAO sessionDAO(){
        EnterpriseCacheSessionDAO sessionDAO = new EnterpriseCacheSessionDAO();
        sessionDAO.setActiveSessionsCacheName("shiro-activeSessionCache");
        sessionDAO.setSessionIdGenerator(new JavaUuidSessionIdGenerator());
        return sessionDAO;
    }

    @Bean
    public SimpleCookie simpleCookie(){
        SimpleCookie simpleCookie = new SimpleCookie();
        simpleCookie.setName("shiro-security");
        simpleCookie.setHttpOnly(true);
        simpleCookie.setMaxAge(1800000);
        return simpleCookie;
    }

    @Bean
    public CacheManager cacheManager(){
        EhCacheManager ehCacheManager = new EhCacheManager();
        ehCacheManager.setCacheManagerConfigFile("classpath:config/shiro-ehcache.xml");
        return new EhCacheManager();
    }
}

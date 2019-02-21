package com.yhl.zuulresource.component.config;

import com.yhl.secritycommon.access.RequestAuthoritiesAccessDecisionVoter;
import com.yhl.secritycommon.access.RequestAuthoritiesFilterInvocationSecurityMetadataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

import java.util.Collections;
import java.util.List;


@EnableResourceServer
@EnableWebSecurity
public class ResourceConfig extends ResourceServerConfigurerAdapter  {

    /**
     * 配置对资源的保护模式
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        // 指定所有的资源都要被保护
        super.configure(http);
        // 增加自定义的资源授权过滤器
        http.addFilterBefore(interceptor(), FilterSecurityInterceptor.class);
        //http.requestMatcher(new BearerTokenRequestMatcher());
    }
    @Bean
    public FilterSecurityInterceptor interceptor() {
        FilterSecurityInterceptor interceptor = new FilterSecurityInterceptor();
        //自定一个投票系统
        List<AccessDecisionVoter<?>> voters = Collections.singletonList(new RequestAuthoritiesAccessDecisionVoter());
        AccessDecisionManager accessDecisionManager = new AffirmativeBased(voters);
        interceptor.setAccessDecisionManager(accessDecisionManager);
        interceptor.setSecurityMetadataSource(securityMetadataSource());
        return interceptor;
    }
    @Bean
    public FilterInvocationSecurityMetadataSource securityMetadataSource() {
        return new RequestAuthoritiesFilterInvocationSecurityMetadataSource();
    }
}

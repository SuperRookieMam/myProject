package com.yhl.zuulresource.componet.config;

import com.yhl.yhlsecuritycommon.componet.access.RequestAuthoritiesAccessDecisionVoter;
import com.yhl.yhlsecuritycommon.componet.access.RequestAuthoritiesFilterInvocationSecurityMetadataSource;
import com.yhl.yhlsecuritycommon.componet.provider.RequestAuthoritiesService;
import com.yhl.yhlsecuritycommon.service.Impl.LocalTokenStoreResourceServerTokenServices;
import com.yhl.zuulresource.service.OAuthClientDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;
/*资源服务器*/
@EnableResourceServer
@EnableWebSecurity
public class ResourceConfigure extends ResourceServerConfigurerAdapter {

    @Autowired
    OAuthClientDetailsService oAuthClientDetailsService;

    @Autowired
    RequestAuthoritiesService requestAuthoritiesService;


    /**
     * 配置对资源的保护模式
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        // 指定所有的资源都要被保护
        super.configure(http);
        // 增加自定义的资源授权过滤器
        http.addFilterBefore(interceptor(), FilterSecurityInterceptor.class);
        // 请求比较器,
        http.requestMatcher(new BearerTokenRequestMatcher());
    }

    @Bean
    public FilterSecurityInterceptor interceptor() {
        FilterSecurityInterceptor interceptor = new FilterSecurityInterceptor();
        List<AccessDecisionVoter<?>> voters = Collections.singletonList(new RequestAuthoritiesAccessDecisionVoter());
        //创建一个成功的控制权限
        AccessDecisionManager accessDecisionManager = new AffirmativeBased(voters);
        //权限管理
        interceptor.setAccessDecisionManager(accessDecisionManager);
        //返回给客户的东西
        interceptor.setSecurityMetadataSource(securityMetadataSource());
        return interceptor;
    }

    @Bean  //封装你想要的东西，比如说树形菜单啊什么的
    public FilterInvocationSecurityMetadataSource securityMetadataSource() {
        RequestAuthoritiesFilterInvocationSecurityMetadataSource MetadataSource
                = new RequestAuthoritiesFilterInvocationSecurityMetadataSource();
        MetadataSource.setRequestAuthoritiesService(requestAuthoritiesService);
        return MetadataSource;
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        super.configure(resources);
        // 指定这是一个restful service,不会保存会话状态
        resources.resourceId("ZUUL");
        resources.stateless(true);

        // 这里指定通过token store来校验token
        // 当第三方服务通过access_token来访问服务时，
        // 直接从token_store中获取相关信息，而不用再发起远程调用请求
        //        为了节省时间我是直接从服务那边拷贝过来的
        resources.tokenServices(ResourceServerTokenService());
    }

    /**
     * @return 使用本地还是调用远程到时候自己决定
     */
    public ResourceServerTokenServices ResourceServerTokenService() {
        LocalTokenStoreResourceServerTokenServices tokenService = new LocalTokenStoreResourceServerTokenServices();
        return tokenService;
    }


    //无状态请求比较
    static class BearerTokenRequestMatcher implements RequestMatcher {
        //请求里面的header里面要有这个属性
        private boolean matchHeader(HttpServletRequest request) {
            String authHeader = request.getHeader("Authorization");
            //获得请求类型，匹配的是什么 "scope";"Bearer";"OAuth2";"access_token";"token_type";"expires_in";
            return StringUtils.startsWithIgnoreCase(authHeader, OAuth2AccessToken.BEARER_TYPE);
        }

        @Override//判断是参数请求还是头部请求，还是非法请求
        public boolean matches(HttpServletRequest request) {
            return matchHeader(request) || matchParameter(request);
        }
        //判断是不是头部请求
        private boolean matchParameter(HttpServletRequest request) {
            return !StringUtils.isEmpty(request.getParameter(OAuth2AccessToken.ACCESS_TOKEN));
        }

    }
}

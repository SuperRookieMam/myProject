package com.yhl.oauth2server.componet.config;

import com.yhl.oauth2server.componet.ouathConverter.feature.AuthenticationManagerConverter;
import com.yhl.oauth2server.componet.ouathConverter.feature.AuthorizationServerTokenService;
import com.yhl.oauth2server.componet.ouathConverter.feature.TokenStoreConverter;
import com.yhl.oauth2server.service.OAuthClientDetailsService;
import com.yhl.oauth2server.service.UserApprovalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.OAuth2RequestFactory;
import org.springframework.security.oauth2.provider.approval.ApprovalStoreUserApprovalHandler;
import org.springframework.security.oauth2.provider.approval.UserApprovalHandler;
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestFactory;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;

@Configuration
@EnableAuthorizationServer
public class OAuthServerConfigurer extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private OAuthClientDetailsService oAuthClientDetailsService;
    @Autowired
    private UserApprovalService userApprovalService;

    /**
     * Configure the {link ClientDetailsService}, e.g. declaring individual clients and their properties.
     * Note that password grant is not enabled (even if some clients are allowed it)
     * unless an {link AuthenticationManager} is supplied to the {link #configure(AuthorizationServerEndpointsConfigurer)}.
     * At least one client, or a fully formed custom {link ClientDetailsService}  must be declared or the server will not start.
     *1. 配置{link ClientDetailsService}，例如声明单个客户端及其属性。
     *2.请注意，密码授予未启用(即使某些客户端允许), 除非{link #configure(AuthorizationServerEndpointsConfigurer)} 去配置提供{link AuthenticationManager}
     *3.至少有一个客户端，或一个完整的自定义{link ClientDetailsService} 必须声明，否则服务器将不会启动。
     * */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception{
       clients.withClientDetails(this.oAuthClientDetailsService);
    }
    /**
     *1.Configure the security of the Authorization Server,
     *2. which means in practical terms the /oauth/token endpoint.
     *    这其实意味着/oauth/token 这个接口
     *3.The/oauth/authorize endpoint also needs to be secure,
     *  but that is a normal user-facing endpoint
     *  也就是说先要/oauth/authorize这个验证安全，也就是说这个接口验证后再调用这个验证
     *  and should be secured the same way as the rest of your UI,
     *  so is not covered here. The default settings cover the most common
     * requirements, following recommendations from the OAuth2 spec,
     * so you don't need to do anything here to get a
     * basic server up and running.
     *
     * @param security a fluent configurer for security features
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception{
        // 开启表单验证

        // 通常情况下,Spring Security获取token的认证模式是基于http basic的,
        // 也就是client的client_id和client_secret是通过http的header或者url模式传递的，
        // 也就是通过http请求头的 Authorization传递，具体的请参考http basic
        // 或者http://client_id:client_secret@server/oauth/token的模式传递的
        // 当启用这个配置之后，server可以从表单参数中获取相应的client_id和client_secret信息
        // 默认情况下，checkToken的验证时denyAll的，需要手动开启
        security.checkTokenAccess("isAuthenticated()");
        security.allowFormAuthenticationForClients();
    }

    /**
     * Configure the non-security features of the Authorization Server endpoints,
     * like token store, token customizations, user approvals and grant types.
     * You shouldn't need to do anything by default, unless you need
     * password grants, in which case you need to provide an {link AuthenticationManager}.
     */
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception{
        AuthorizationServerTokenServices tokenService = tokenService();
        endpoints.tokenServices(tokenService);
       /**
         * 部分的授权服务终端接口是被机器使用的，但是也总有几个资源需要通过界面 UI 的，
         * 通过 GET /oauth/confirm_access 获取的页面和 /oauth/error 返回的 html。
         * 框架使用白板（whitelabel）来提供界面，所以现实中，大部分的授权服务需要提供自己的界面，这样才能控制界面样式和内容。
         * 你所需要做 提供一个 controller，给这个终端接口加上 @RequestMappings，
         * 框架默认采用低优先权的分发器(dispatcher)。在 /oauth/confirm_access 终端接口，
         * 你期望一个 AuthorizationRequest 绑定到 session，session 携带所有需要用户批准的数据
         * （默认的实现是 WhitelabelApprovalEndpoint，所以以这个为 copy 的起点），
         * 你能取得在 request 中的所有数据并且按你喜欢的样子渲染处来。
         * 然后用户需要做的就是带着授权或拒绝权限的信息 POST 请求到 /oauth/authorize。
         * 请求的额参数通过 AuthorizationEndpoint 被直接传输到 UserApprovalHandler，
         * 因此你能够按你需要的解析这些数据。
         * 默认的 UserApprovalHandler 依赖于你是否在 AuthorizationServerEndpointsConfigurer
         * 提供 ApprovalStore（提供的情况下是 ApprovalStoreUserApprovalHandler）(没有提供的情况下是 TokenStoreUserApprovalHandler)。
         *标准的授权 handlers 按如下接受：
         *TokenStoreUserApprovalHandler：通过判断 user_oauth_approval 等于 true 或 false，简单的返回 yes 或 no。
         *ApprovalStoreUserApprovalHandler：一组参数的 key 为 scope，.*,这些 key 的“*”等于被请求的域（scope）。
         * 参数的值是 true 或者 approved（如果用户授权了权限），否则用户被认为在这个域（scope）被拒绝。
         * 如果最终有一个域（scope）被授权，那么权限授权就是成功的。
         * 注意：不要忘记加入 CSRF 来保护你渲染给用户的表单页面。
         * Spring Security 默认期望参数中有一个名为“_csrf"的参数（Spring Security 在请求属性中提供该值）。
         * 查看 Spring Security 用户手册获取更多相关信息，或者查看 whitelabel 实现作为指导。
         * */
        endpoints.userApprovalHandler(userApprovalHandler());
    }

    @Bean
    public AuthorizationServerTokenServices tokenService() {
        AuthorizationServerTokenService tokenService = new AuthorizationServerTokenService();
        tokenService.setTokenStore(tokenStore());
        tokenService.setClientDetailsService(oAuthClientDetailsService);
        tokenService.setSupportRefreshToken(true);
        tokenService.setReuseRefreshToken(false); // 不允许
        tokenService.setAuthenticationManager(authenticationManager(tokenService ));//自定义权限验证
        return tokenService;
    }
    @Bean
    public TokenStore tokenStore(){
        TokenStoreConverter tokenStoreConverter =new TokenStoreConverter();
        return tokenStoreConverter;
    }

    @Bean
    public UserApprovalHandler userApprovalHandler(){
        // 存储用户的授权结果
        ApprovalStoreUserApprovalHandler handler = new ApprovalStoreUserApprovalHandler();
        handler.setApprovalStore(userApprovalService);
        handler.setRequestFactory(requestFactory());
        return handler;
    }

    @Bean
    public OAuth2RequestFactory requestFactory() {
        return new DefaultOAuth2RequestFactory(oAuthClientDetailsService);
    }


    public AuthenticationManager authenticationManager(AuthorizationServerTokenService tokenService) {
        AuthenticationManagerConverter manager = new AuthenticationManagerConverter();
        manager.setClientInfoService(oAuthClientDetailsService);
        manager.setTokenServices(tokenService);
        return manager;
    }
}

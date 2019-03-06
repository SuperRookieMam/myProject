package com.yhl.oauthserver.componet.config;

import com.yhl.oauthserver.service.ClinetDetailsInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;

@Configuration
@EnableAuthorizationServer
public class OauthServerConfigurers extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private ClinetDetailsInfoService clinetDetailsInfoService;

    /**
     * Configure the {@link ClientDetailsService}, e.g. declaring individual clients and their properties.
     * Note that password grant is not enabled (even if some clients are allowed it)
     * unless an {@link AuthenticationManager} is supplied to the {@link #configure(AuthorizationServerEndpointsConfigurer)}.
     * At least one client, or a fully formed custom {@link ClientDetailsService}  must be declared or the server will not start.
     *1. 配置{@link ClientDetailsService}，例如声明单个客户端及其属性。
     *2.请注意，密码授予未启用(即使某些客户端允许),
     * 除非{@link #configure(AuthorizationServerEndpointsConfigurer)} 去配置提供{@link AuthenticationManager}
     *3.至少有一个客户端，或一个完整的自定义{@link ClientDetailsService} 必须声明，否则服务器将不会启动。
     * */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception{
        clients.withClientDetails(this.clinetDetailsInfoService);
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
     * password grants, in which case you need to provide an {@link AuthenticationManager}.
     */
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception{

    }


}
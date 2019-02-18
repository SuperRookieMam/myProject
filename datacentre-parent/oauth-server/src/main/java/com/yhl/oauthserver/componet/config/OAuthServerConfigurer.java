package com.yhl.oauthserver.componet.config;

;
import com.yhl.oauthserver.service.MyAuthorizationServerTokenService;
import com.yhl.oauthserver.service.MyClientDetailService;
import com.yhl.oauthserver.service.UserApprovalStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.OAuth2RequestFactory;
import org.springframework.security.oauth2.provider.approval.ApprovalStoreUserApprovalHandler;
import org.springframework.security.oauth2.provider.approval.UserApprovalHandler;
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestFactory;

@Configuration
@EnableAuthorizationServer
public class OAuthServerConfigurer   extends AuthorizationServerConfigurerAdapter {
  @Autowired
  private MyClientDetailService myClientDetailService;
  @Autowired
  private UserApprovalStoreService userApprovalStoreService;
  @Autowired
  private MyAuthorizationServerTokenService myAuthorizationServerTokenService;
  @Override
  public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        // 通过自定义的service来存储客户端信息
        clients.withClientDetails(myClientDetailService);
    }
  @Override
  public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
    super.configure(security);
    security.checkTokenAccess("isAuthenticated()"); // 开启/oauth/check_token验证端口认证权限访问
    security.allowFormAuthenticationForClients();//允许对客户机进行表单身份验证
    //security.tokenKeyAccess("permitAll()");// 开启/oauth/token_key验证端口无权限访问
  }
  @Override
  public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
      super.configure(endpoints);
    //自定义了对AccessToken的相关操作创建、刷新、获取
    endpoints.tokenServices(myAuthorizationServerTokenService);
    //对用户端，权限，作用于的控制
    endpoints.userApprovalHandler(userApprovalHandler());

  }
  @Bean
  public UserApprovalHandler userApprovalHandler() {
    ApprovalStoreUserApprovalHandler handler = new ApprovalStoreUserApprovalHandler();
    handler.setApprovalStore(userApprovalStoreService);
    handler.setRequestFactory(requestFactory());
    return handler;
  }

  @Bean
  public OAuth2RequestFactory requestFactory() {
    return new DefaultOAuth2RequestFactory(myClientDetailService);
  }

}

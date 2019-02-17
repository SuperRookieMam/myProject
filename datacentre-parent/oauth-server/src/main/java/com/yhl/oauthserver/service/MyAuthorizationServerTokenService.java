package com.yhl.oauthserver.service;

import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;

public interface MyAuthorizationServerTokenService extends AuthorizationServerTokenServices, ResourceServerTokenServices,
        ConsumerTokenServices{


}

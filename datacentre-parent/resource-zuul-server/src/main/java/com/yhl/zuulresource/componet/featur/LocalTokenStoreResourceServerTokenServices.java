package com.yhl.zuulresource.componet.featur;

import lombok.Setter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

/**
 * 如果资源服务器和Oaut服务器在一个项目就用这个来获取Token
 * */
@Setter
public class LocalTokenStoreResourceServerTokenServices implements ResourceServerTokenServices, InitializingBean {

    private TokenStore tokenStore ;

    public void setTokenStore(TokenStore tokenStore) {
        this.tokenStore = tokenStore;
    }
    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(tokenStore, "tokenStore must be set");
    }

    @Override
    public OAuth2Authentication loadAuthentication(String accessToken) throws AuthenticationException, InvalidTokenException {
        TokenStoreConverter storeConverter =(TokenStoreConverter)tokenStore;
        OAuth2Authentication oAuth2Authentication =storeConverter.readAuthentication(accessToken);
           if (ObjectUtils.isEmpty(oAuth2Authentication)){
               throw new BadCredentialsException("查无此人");
           }
        return oAuth2Authentication;
    }

    @Override
    public OAuth2AccessToken readAccessToken(String accessToken) {
        TokenStoreConverter storeConverter =(TokenStoreConverter)tokenStore;
        return storeConverter.readAccessToken(accessToken);
    }
}

package com.yhl.oauthserver.componet.oauthImpl;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.approval.ApprovalStoreUserApprovalHandler;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;

public class ResourceServerTokenServiciys  implements ResourceServerTokenServices{

    /**
     *{
     *  name:userName(clinentId)
     *  passWord:password(secrect)
     *  type:user(client)
     *
     *}
     * */
    @Override
    public OAuth2Authentication loadAuthentication(String accessToken) throws AuthenticationException, InvalidTokenException {

        return null;
    }

    @Override
    public OAuth2AccessToken readAccessToken(String accessToken) {

        return null;
    }
}

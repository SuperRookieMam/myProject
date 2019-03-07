package com.yhl.oauth2server.componet.ouathConverter.feature;

import com.alibaba.fastjson.JSONObject;
import com.yhl.authoritycommom.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;

public class ResourceServerTokenServicesConverter implements ResourceServerTokenServices {

    @Autowired
    private UserInfoService userInfoService;
    /**
     * {
     *  userName:xxxx,
     *  passWord:xxxx,
     *  resourceIds:[],
     *  currentResource: xxxxx
     * }
     * */
    @Override
    public OAuth2Authentication loadAuthentication(String accessToken) throws AuthenticationException, InvalidTokenException {
        JSONObject jsonObject =JSONObject.parseObject(accessToken);
        String userName =jsonObject.getString("userName");
        String currentResource =jsonObject.getString("currentResource");
        String passWord =jsonObject.getString("passWord");


        return null;
    }

    @Override
    public OAuth2AccessToken readAccessToken(String accessToken) {
        return null;
    }
}

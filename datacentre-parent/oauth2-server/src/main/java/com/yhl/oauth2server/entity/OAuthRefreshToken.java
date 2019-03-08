package com.yhl.oauth2server.entity;

import org.springframework.security.oauth2.common.OAuth2RefreshToken;

/**
 * 在项目中,主要操作oauth_refresh_token表的对象是JdbcTokenStore.java. \
 * (与操作oauth_access_token表的对象一样);更多的细节请参考该类. 
 * 如果客户端的grant_type不支持refresh_token,则不会使用该表.
 * */
public class OAuthRefreshToken implements OAuth2RefreshToken {

    /**
     * 	该字段的值是将refresh_token的值通过MD5加密后存储的.
     * */
    private String tokenId;
    /**
     * 存储将OAuth2RefreshToken.java对象序列化后的二进制数据.
     * */
    private String token;

    /**
     *存储将OAuth2Authentication.java对象序列化后的二进制数据.
     * */
    private String authentication;

    @Override
    public String getValue() {
        return null;
    }
}

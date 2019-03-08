package com.yhl.oauth2server.entity;

import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;

import java.util.Date;
import java.util.Map;
import java.util.Set;

/**
 * 在项目中,主要操作oauth_access_token表的对象是JdbcTokenStore.java. 更多的细节请参考该类.
 * */
public class QAuthAccessToken implements OAuth2AccessToken {

    /**
     * 该字段的值是将access_token的值通过MD5加密后存储的.
     * */
    private String tokenId;

    /**
     * 存储将OAuth2AccessToken.java对象序列化后的二进制数据, 是真实的AccessToken的数据值.
     * */
    private String token;
    /**
     *该字段具有唯一性, 其值是根据当前的username(如果有),
     * client_id与scope通过MD5加密生成的.
     * 具体实现请参考DefaultAuthenticationKeyGenerator.java类.
     * */
    private String authenticationId;

    /**
     * 登录时的用户名, 若客户端没有用户名(如grant_type="client_credentials"),则该值等于client_id
     * */
    private  String userName;
    /**
     *
     * */
    private String clientId;

    /**
     *存储将OAuth2Authentication.java对象序列化后的二进制数据.
     * */
    private  String authentication;
    /**
     *该字段的值是将refresh_token的值通过MD5加密后存储的.
     * */
    private  String  refreshToken;

    @Override
    public Map<String, Object> getAdditionalInformation() {
        return null;
    }

    @Override
    public Set<String> getScope() {
        return null;
    }

    @Override
    public OAuth2RefreshToken getRefreshToken() {
        return null;
    }

    @Override
    public String getTokenType() {
        return null;
    }

    @Override
    public boolean isExpired() {
        return false;
    }

    @Override
    public Date getExpiration() {
        return null;
    }

    @Override
    public int getExpiresIn() {
        return 0;
    }

    @Override
    public String getValue() {
        return null;
    }
}

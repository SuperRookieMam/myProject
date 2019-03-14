package com.yhl.zuulresource.entity;

import com.yhl.base.baseEntity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * 在项目中,主要操作oauth_access_token表的对象是JdbcTokenStore.java. 更多的细节请参考该类.
 * */
@Getter
@Setter
@Entity
@Table(name = "oauth_access_token",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"authentication_id"})},
        indexes = {@Index(columnList = "authentication_id")})
public class OAuthAccessToken extends BaseEntity<String> {

    private static final long serialVersionUID = -5123030928910884773L;
    /**
     * 该字段的值是将access_token的值通过MD5加密后存储的.
     * */
    @Column(name = "token_id")
    private String tokenId;

    /**
     * 存储将OAuth2AccessToken.java对象序列化后的二进制数据, 是真实的AccessToken的数据值.
     * */
    @Column(name = "token")
    private String token;
    /**
     *该字段具有唯一性, 其值是根据当前的username(如果有),
     * client_id与scope通过MD5加密生成的.
     * 具体实现请参考DefaultAuthenticationKeyGenerator.java类.
     * */
    @Column(name = "authentication_id")
    private String authenticationId;

    /**
     *存储将OAuth2Authentication.java对象序列化后的二进制数据.
     * */
    @Column(name = "authentication")
    private  String authentication;
    /**
     *
     * */
    @Column(name = "client_id")
    private String clientId;

    /**
     * 登录时的用户名, 若客户端没有用户名(如grant_type="client_credentials"),则该值等于client_id
     * */
    @Column(name = "user_name")
    private  String userName;

    /**
     *该字段的值是将refresh_token的值通过MD5加密后存储的.
     * */
    @Column(name = "refresh_token")
    private  String  refreshToken;
}

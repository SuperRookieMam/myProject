package com.yhl.oauthserver.entity;

/**
 * 该表用于在客户端系统中存储从服务端获取的token数据, 在spring-oauth-server项目中未使用到.
 * 对oauth_client_token表的主要操作在JdbcClientTokenServices.java类中, 更多的细节请参考该类
 * */
public class OAuthClientToken {

    /**
     * 从服务器端获取到的access_token的值.
     * */
   private String tokenId;

   /**
    * 这是一个二进制的字段, 存储的数据是OAuth2AccessToken.java对象序列化后的二进制数据.
    * */
    private String token;

    /**
     *该字段具有唯一性, 是根据当前的username(如果有),client_id与scope通过MD5加密生成的.
     * 具体实现请参考DefaultClientKeyGenerator.java类.
     * */
    private String authenticationId;
    /**
     * 登录时的用户名
     * */
    private String userName;

    /**
     * client_id
     * */
    private String clientId;
}

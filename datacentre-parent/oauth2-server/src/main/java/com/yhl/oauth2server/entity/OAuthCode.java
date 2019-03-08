package com.yhl.oauth2server.entity;


/**
 * 在项目中,主要操作oauth_code表的对象是JdbcAuthorizationCodeServices.java. 更多的细节请参考该类. 
 * 只有当grant_type为"authorization_code"时,该表中才会有数据产生; 其他的grant_type没有使用该表.
 * */
public class OAuthCode  {

    /**
     * 存储服务端系统生成的code的值(未加密).
     * */
   private  String code;
    /**
     * 存储将AuthorizationRequestHolder.java对象序列化后的二进制数据.
     * */
   private String authentication;



}

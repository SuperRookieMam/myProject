package com.yhl.oauth2server.componet.ouathConverter.feature;

import com.yhl.baseorm.component.constant.WhereCondition;
import com.yhl.oauth2server.dao.OAuthAccessTokenDao;
import com.yhl.oauth2server.dao.OAuthRefreshTokenDao;
import com.yhl.oauth2server.entity.OAuthAccessToken;
import com.yhl.oauth2server.entity.OAuthRefreshToken;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.common.util.SerializationUtils;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.AuthenticationKeyGenerator;
import org.springframework.security.oauth2.provider.token.DefaultAuthenticationKeyGenerator;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/***
 * token 的创建、持久化、获取
 * */
@Setter
public class TokenStoreConverter implements TokenStore {
    @Autowired
    private OAuthAccessTokenDao oAuthAccessTokenDao;
    @Autowired
    private OAuthRefreshTokenDao oAuthRefreshTokenDao;

    private final String AUTHENTICATIONID = "authenticationId";
    private final String TOKENID="tokenId";
    private final  String REFRESHTOKEN ="refreshToken";
    private final  String CLIENTID="clientId";
    private final  String USERNAME="userName";
    //使用默认的MD5解密
    private AuthenticationKeyGenerator authenticationKeyGenerator = new DefaultAuthenticationKeyGenerator();

    @Override
    public OAuth2Authentication readAuthentication(OAuth2AccessToken token) {
        return readAuthentication(token.getValue());
    }

    @Override
    public OAuth2Authentication readAuthentication(String token) {
        OAuth2Authentication oAuth2Authentication =null;
        WhereCondition whereCondition =new WhereCondition();
        whereCondition.and().addEq(TOKENID,token);
        List<OAuthAccessToken>  oAuthAccessTokens =oAuthAccessTokenDao.findByParams(whereCondition);
        if (!oAuthAccessTokens.isEmpty()){
            OAuthAccessToken oAuthAccessToken = oAuthAccessTokens.get(0);
            String authenticationStr= oAuthAccessToken.getAuthentication();
            oAuth2Authentication = deserializeAuthentication(authenticationStr);
        }
        return oAuth2Authentication;
    }

    @Override
    @Transactional(value = "transactionManagerPrimary",rollbackFor = Exception.class)
    public void storeAccessToken(OAuth2AccessToken token, OAuth2Authentication authentication) {
        String refreshToken = null;
        if (token.getRefreshToken() != null) {
            refreshToken = token.getRefreshToken().getValue();
        }
        // 保持token的唯一性，如果有先删除
        if (readAccessToken(token.getValue())!=null) {
            removeAccessToken(token.getValue());
        }
        OAuthAccessToken oAuthAccessToken =new OAuthAccessToken();
        oAuthAccessToken.setTokenId(extractTokenKey(token.getValue()));
        oAuthAccessToken.setToken(serializeAccessToken(token));
        oAuthAccessToken.setAuthenticationId(authenticationKeyGenerator.extractKey(authentication));
        oAuthAccessToken.setAuthentication(serializeAuthentication(authentication));
        oAuthAccessToken.setClientId(authentication.getOAuth2Request().getClientId());
        oAuthAccessToken.setUserName(authentication.isClientOnly() ? null : authentication.getName());
        oAuthAccessToken.setRefreshToken(extractTokenKey(refreshToken));
        oAuthAccessTokenDao.insertByEntity(oAuthAccessToken);

    }

    @Override
    public OAuth2AccessToken readAccessToken(String tokenValue) {
        OAuth2AccessToken oAuth2AccessToken =null;
        WhereCondition whereCondition =new WhereCondition();
        whereCondition.and().addEq(TOKENID,tokenValue);
        List<OAuthAccessToken> oAuthAccessTokens =oAuthAccessTokenDao.findByParams(whereCondition);
        if (!oAuthAccessTokens.isEmpty()){
            OAuthAccessToken oAuthAccessToken =  oAuthAccessTokens.get(0);
            oAuth2AccessToken = deserializeAccessToken(oAuthAccessToken.getToken());

        }
        return oAuth2AccessToken;
    }

    @Override
    @Transactional(value = "transactionManagerPrimary",rollbackFor = Exception.class)
    public void removeAccessToken(OAuth2AccessToken token) {
        removeAccessToken(token.getValue());
    }
    @Transactional(value = "transactionManagerPrimary",rollbackFor = Exception.class)
    public void removeAccessToken(String tokenId) {
        WhereCondition whereCondition =new WhereCondition();
        whereCondition.and().addEq(TOKENID,tokenId);
        oAuthAccessTokenDao.deleteByWhereCondition(whereCondition);
    }
    @Override
    @Transactional(value = "transactionManagerPrimary",rollbackFor = Exception.class)
    public void storeRefreshToken(OAuth2RefreshToken refreshToken, OAuth2Authentication authentication) {
        OAuthRefreshToken oAuthRefreshToken =new OAuthRefreshToken();
        oAuthRefreshToken.setTokenId(extractTokenKey(refreshToken.getValue()));
        oAuthRefreshToken.setToken(serializeRefreshToken(refreshToken));
        oAuthRefreshToken.setAuthentication(serializeAuthentication(authentication));
        oAuthRefreshTokenDao.insertByEntity(oAuthRefreshToken);
    }

    @Override
    public OAuth2RefreshToken readRefreshToken(String tokenValue) {
        OAuth2RefreshToken refreshToken = null;
        WhereCondition whereCondition =new WhereCondition();
        whereCondition.and().addEq(TOKENID,extractTokenKey(tokenValue));
        List<OAuthRefreshToken>  oAuthRefreshTokens =oAuthRefreshTokenDao.findByParams(whereCondition);
        if (!oAuthRefreshTokens.isEmpty()){
            refreshToken = deserializeRefreshToken(oAuthRefreshTokens.get(0).getToken());
        }
        return refreshToken;
    }

    @Override
    @Transactional(value = "transactionManagerPrimary",rollbackFor = Exception.class)
    public OAuth2Authentication readAuthenticationForRefreshToken(OAuth2RefreshToken token) {
        return readAuthenticationForRefreshToken(token.getValue());
    }
    @Transactional(value = "transactionManagerPrimary",rollbackFor = Exception.class)
    public OAuth2Authentication readAuthenticationForRefreshToken(String tokenValue) {
        OAuth2Authentication authentication = null;
        WhereCondition whereCondition =new WhereCondition();
        whereCondition.and().addEq(TOKENID,extractTokenKey(tokenValue));
        List<OAuthRefreshToken>  oAuthRefreshTokens =oAuthRefreshTokenDao.findByParams(whereCondition);
        if (!oAuthRefreshTokens.isEmpty()){
            String authenticationStr =oAuthRefreshTokens.get(0).getAuthentication();
            authentication = deserializeAuthentication(authenticationStr);
        }
        return authentication;
    }
    @Override
    @Transactional(value = "transactionManagerPrimary",rollbackFor = Exception.class)
    public void removeRefreshToken(OAuth2RefreshToken token) {
        removeRefreshToken(token.getValue());
    }
    @Transactional(value = "transactionManagerPrimary",rollbackFor = Exception.class)
    public void removeRefreshToken(String tokenValue) {
        WhereCondition whereCondition =new WhereCondition();
        whereCondition.and().addEq(TOKENID,extractTokenKey(tokenValue));
        oAuthRefreshTokenDao.deleteByWhereCondition(whereCondition);
    }
    @Override
    @Transactional(value = "transactionManagerPrimary",rollbackFor = Exception.class)
    public void removeAccessTokenUsingRefreshToken(OAuth2RefreshToken refreshToken) {
        removeAccessTokenUsingRefreshToken(refreshToken.getValue());
    }
    @Transactional(value = "transactionManagerPrimary",rollbackFor = Exception.class)
    public void removeAccessTokenUsingRefreshToken(String refreshToken) {
        WhereCondition whereCondition =new WhereCondition();
        whereCondition.and().addEq(REFRESHTOKEN,extractTokenKey(refreshToken));
        oAuthAccessTokenDao.deleteByWhereCondition(whereCondition);
    }
    /**
     * 检索根据提供的身份验证密钥存储的访问令牌
     * */
    @Override
    @Transactional(value = "transactionManagerPrimary",rollbackFor = Exception.class)
    public OAuth2AccessToken getAccessToken(OAuth2Authentication authentication) {
        OAuth2AccessToken auth2AccessToken =null;
        //提取authentication请求的参数string作为QAuthAccessToken 的唯一Id
        //{username:xxxxx,client_id:xxxx,scope:xxxxx}
        String key = authenticationKeyGenerator.extractKey(authentication);
        WhereCondition whereCondition =new WhereCondition();
        whereCondition.and().addEq(AUTHENTICATIONID,key);
        List<OAuthAccessToken>  oAuthAccessTokens =oAuthAccessTokenDao.findByParams(whereCondition);
        if (oAuthAccessTokens.size()>0){
            OAuthAccessToken oAuthAccessToken = oAuthAccessTokens.get(0);
            String  authenticationStr  = oAuthAccessToken.getToken();
            auth2AccessToken = deserializeAccessToken(authenticationStr);
            if (auth2AccessToken != null) {
                OAuth2Authentication oAuth2Authentication =  readAuthentication(auth2AccessToken.getValue());
                if (!ObjectUtils.isEmpty(oAuth2Authentication)){
                    String key1  =  authenticationKeyGenerator.extractKey(oAuth2Authentication);
                        if (!key.equals(key1)){
                            removeAccessToken(auth2AccessToken.getValue());
                            /**
                             * Keep the store consistent (
                             * maybe the same user is represented by this authentication
                             * but the details have changed)
                             * */
                            storeAccessToken(auth2AccessToken, authentication);
                        }
                }

            }
        }
        return auth2AccessToken;
    }

    @Override
    public Collection<OAuth2AccessToken> findTokensByClientIdAndUserName(String clientId, String userName) {
        List<OAuth2AccessToken> accessTokens = new ArrayList<OAuth2AccessToken>();
        WhereCondition whereCondition =new WhereCondition();
        whereCondition.and().addEq(CLIENTID,clientId)
                            .addEq(USERNAME,userName);
        List<OAuthAccessToken> oAuthAccessTokens =   oAuthAccessTokenDao.findByParams(whereCondition);
            for (int i = 0; i <oAuthAccessTokens.size() ; i++) {
              accessTokens.add(deserializeAccessToken(oAuthAccessTokens.get(i).getToken())) ;
            }
        return accessTokens;
    }

    @Override
    public Collection<OAuth2AccessToken> findTokensByClientId(String clientId) {
        List<OAuth2AccessToken> accessTokens = new ArrayList<OAuth2AccessToken>();
        WhereCondition whereCondition =new WhereCondition();
        whereCondition.and().addEq(CLIENTID,clientId);
        List<OAuthAccessToken> oAuthAccessTokens =   oAuthAccessTokenDao.findByParams(whereCondition);
        for (int i = 0; i <oAuthAccessTokens.size() ; i++) {
            accessTokens.add(deserializeAccessToken(oAuthAccessTokens.get(i).getToken()));
        }
        return accessTokens;
    }

    /**
     * 反序列化得到OAuth2AccessToken
     * */
    private   OAuth2AccessToken deserializeAccessToken(String tokenStr){
        if (StringUtils.isEmpty(tokenStr)){
            return null;
        }
        return SerializationUtils.deserialize(tokenStr.getBytes());
    }
    /**
     * 反序列化得到OAuth2Authentication
     * */
    private OAuth2Authentication deserializeAuthentication(String authenticationStr) {
        if (StringUtils.isEmpty(authenticationStr)){
            return null;
        }
        return SerializationUtils.deserialize(authenticationStr.getBytes());
    }

    //MD5加密token的value
    protected String extractTokenKey(String value) {
        if (value == null) {
            return null;
        }
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("MD5");
        }
        catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("MD5 algorithm not available.  Fatal (should be in the JDK).");
        }
        try {
            byte[] bytes = digest.digest(value.getBytes("UTF-8"));
            return String.format("%032x", new BigInteger(1, bytes));
        }
        catch (UnsupportedEncodingException e) {
            throw new IllegalStateException("UTF-8 encoding not available.  Fatal (should be in the JDK).");
        }
    }
    //序列化token
    protected String serializeAccessToken(OAuth2AccessToken token) {
        String value =new String(SerializationUtils.serialize(token));
        return value;
    }
    protected String serializeAuthentication(OAuth2Authentication authentication) {
        String value =new String(SerializationUtils.serialize(authentication));
        return value;
    }
    protected OAuth2RefreshToken deserializeRefreshToken(String token) {
        String value =new String(SerializationUtils.serialize(token));
        return SerializationUtils.deserialize(token.getBytes());
    }
    protected String serializeRefreshToken(OAuth2RefreshToken token) {
        return new String(SerializationUtils.serialize(token));
    }
}

package com.yhl.oauth2server.componet.ouathConverter.feature;

import com.yhl.oauth2server.dao.AccessTokenDao;
import com.yhl.oauth2server.entity.AccessToken;
import com.yhl.oauth2server.service.ClientInfoService;
import com.yhl.oauth2server.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.SqlLobValue;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.AuthenticationKeyGenerator;
import org.springframework.security.oauth2.provider.token.DefaultAuthenticationKeyGenerator;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.sql.Types;
import java.util.Collection;

/***
 * token 的创建、持久化、获取
 * */
@Service
public class YhlTokenStore implements TokenStore {
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private ClientInfoService clientInfoService;
    @Autowired
    private AccessTokenDao accessTokenDao;
    //使用默认的MD5解密
    private AuthenticationKeyGenerator authenticationKeyGenerator = new DefaultAuthenticationKeyGenerator();

    @Override
    public OAuth2Authentication readAuthentication(OAuth2AccessToken token) {
        return readAuthentication(token.getValue());
    }

    @Override
    public OAuth2Authentication readAuthentication(String token) {
        OAuth2Authentication oAuth2Authentication =null;
        AccessToken accessToken = accessTokenDao.findById(token);
        if (ObjectUtils.isEmpty(accessToken)){
            return oAuth2Authentication;
        }



        return oAuth2Authentication;
    }

    @Override
    public void storeAccessToken(OAuth2AccessToken token, OAuth2Authentication authentication) {
        String refreshToken = null;
        if (token.getRefreshToken() != null) {
            refreshToken = token.getRefreshToken().getValue();
        }
        if (readAccessToken(token.getValue())!=null) {
            removeAccessToken(token);
        }


    }

    @Override
    public OAuth2AccessToken readAccessToken(String tokenValue) {
        //我自我实现的反回的是IdSstring
        return accessTokenDao.findById(tokenValue);
    }

    @Override
    public void removeAccessToken(OAuth2AccessToken token) {

    }

    @Override
    public void storeRefreshToken(OAuth2RefreshToken refreshToken, OAuth2Authentication authentication) {

    }

    @Override
    public OAuth2RefreshToken readRefreshToken(String tokenValue) {
        return null;
    }

    @Override
    public OAuth2Authentication readAuthenticationForRefreshToken(OAuth2RefreshToken token) {
        return null;
    }

    @Override
    public void removeRefreshToken(OAuth2RefreshToken token) {

    }

    @Override
    public void removeAccessTokenUsingRefreshToken(OAuth2RefreshToken refreshToken) {

    }
    /**
     * 检索根据提供的身份验证密钥存储的访问令牌
     * */
    @Override
    public OAuth2AccessToken getAccessToken(OAuth2Authentication authentication) {
        AccessToken accessToken =null;
        // 提取关键提取key
        String key = authenticationKeyGenerator.extractKey(authentication);


        accessToken = accessTokenDao.findById(key);
        if (accessToken != null) {
            storeAccessToken(accessToken, authentication);
         }
        return accessToken;
    }

    @Override
    public Collection<OAuth2AccessToken> findTokensByClientIdAndUserName(String clientId, String userName) {
        return null;
    }

    @Override
    public Collection<OAuth2AccessToken> findTokensByClientId(String clientId) {
        return null;
    }
}

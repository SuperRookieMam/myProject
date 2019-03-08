package com.yhl.oauth2server.componet.ouathConverter.feature;

import com.yhl.base.component.dto.ResultDto;
import com.yhl.baseorm.component.constant.WhereCondition;
import com.yhl.oauth2server.dao.AccessTokenDao;
import com.yhl.oauth2server.entity.AccessToken;
import com.yhl.oauth2server.entity.UserInfo;
import com.yhl.oauth2server.service.ClientInfoService;
import com.yhl.oauth2server.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.AuthenticationKeyGenerator;
import org.springframework.security.oauth2.provider.token.DefaultAuthenticationKeyGenerator;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Collection;
import java.util.List;

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
    private  String token = "accessToken";
    //使用默认的MD5解密
    private AuthenticationKeyGenerator authenticationKeyGenerator = new DefaultAuthenticationKeyGenerator();

    @Override
    public OAuth2Authentication readAuthentication(OAuth2AccessToken token) {
        return readAuthentication(token.getValue());
    }

    @Override
    public OAuth2Authentication readAuthentication(String token) {
        OAuth2Authentication oAuth2Authentication =null;
        ResultDto resultDto = userInfoService.findById(token);
        UserInfo userInfo =(UserInfo)resultDto.getData();
        if (!ObjectUtils.isEmpty(userInfo)){
            oAuth2Authentication =new OAuth2Authentication(null,userInfo);
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
        accessTokenDao.insertByEntity(token);
    }

    @Override
    public OAuth2AccessToken readAccessToken(String tokenValue) {
        //我自我实现的反回的是IdSstring
        return accessTokenDao.findById(tokenValue);
    }

    @Override
    public void removeAccessToken(OAuth2AccessToken token) {
        WhereCondition whereCondition =new WhereCondition();
        accessTokenDao.delete((AccessToken) token);
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

        // 提取关键提取key{client_id:xxxxx,username:xxxx,scope:[]} 的加密字符串
        String key = authenticationKeyGenerator.extractKey(authentication);

        authentication.getOAuth2Request();


        AccessToken accessToken =null;

        WhereCondition whereCondition =new WhereCondition();
        whereCondition.and().addEq(token,key);
        List<AccessToken> list = accessTokenDao.findByParams(whereCondition);
        accessToken =list.isEmpty()?null:list.get(0);
        //
        String key1 = authenticationKeyGenerator.extractKey(readAuthentication(accessToken.getValue()));

        //两个key不杨的时候
        if (accessToken != null &&!key.equals(key1)) {
             removeAccessToken(accessToken);
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

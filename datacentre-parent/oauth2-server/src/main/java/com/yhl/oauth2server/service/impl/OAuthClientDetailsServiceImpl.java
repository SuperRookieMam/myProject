package com.yhl.oauth2server.service.impl;

import com.yhl.authoritycommom.entity.OAuthClientDetailsDto;
import com.yhl.base.baseService.impl.BaseServiceImpl;
import com.yhl.baseorm.component.constant.WhereCondition;
import com.yhl.oauth2server.entity.OAuthClientDetails;
import com.yhl.oauth2server.service.OAuthClientDetailsService;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OAuthClientDetailsServiceImpl extends BaseServiceImpl<OAuthClientDetails,String> implements OAuthClientDetailsService {
    private  final String CLIENTID ="clientId";
     @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        WhereCondition whereCondition =new WhereCondition();
        whereCondition.and().addEq(CLIENTID,clientId);
        List<OAuthClientDetails> clientDetails =( List<OAuthClientDetails> ) findByParams(whereCondition).getData();
        if (clientDetails.isEmpty()){
            throw new ClientRegistrationException("客户端不存在");
        }
        OAuthClientDetails oAuthClientDetails =  clientDetails.get(0);

        return   getOAuthClientDetailsDto( new OAuthClientDetailsDto(),  clientDetails.get(0) );
    }
    private OAuthClientDetailsDto getOAuthClientDetailsDto(OAuthClientDetailsDto dto, OAuthClientDetails model ){
        dto.setRefreshTokenValiditySeconds(model.getRefreshTokenValiditySeconds());
        dto.setAccessTokenValiditySeconds(model.getAccessTokenValiditySeconds());
        dto.setAdditionalInformation(model.getAdditionalInformation());
        dto.setArchived(model.getArchived());
        dto.setAuthorities(model.getAuthorities());
        dto.setClientId(model.getClientId());
        dto.setAutoApprove(model.isAutoApprove());
        dto.setAuthorizedGrantTypes(model.getAuthorizedGrantTypes());
        dto.setResourceIds(model.getResourceIds());
        dto.setScopes(model.getScopes());
        dto.setTrusted(model.getTrusted());
        dto.setClientSecret(model.getClientSecret());
        dto.setRegisteredRedirectUri(model.getRegisteredRedirectUri());
        return dto;
    }




}

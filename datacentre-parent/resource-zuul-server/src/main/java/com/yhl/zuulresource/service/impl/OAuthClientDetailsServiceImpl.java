package com.yhl.zuulresource.service.impl;

import com.yhl.base.baseService.impl.BaseServiceImpl;
import com.yhl.zuulresource.entity.OAuthClientDetails;
import com.yhl.zuulresource.service.OAuthClientDetailsService;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Service;

@Service
public class OAuthClientDetailsServiceImpl extends BaseServiceImpl<OAuthClientDetails,String> implements OAuthClientDetailsService {
    private  final String CLIENTID ="clientId";
    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
       /* WhereCondition whereCondition =new WhereCondition();
        whereCondition.and().addEq(CLIENTID,clientId);
        ClientDetails clientDetails =(ClientDetails)findByParams(whereCondition).getData();
        if (ObjectUtils.isEmpty(clientDetails)){
            throw new ClientRegistrationException("客户端不存在");
        }*/
        return null ;
    }
}

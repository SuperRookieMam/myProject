package com.yhl.oauth2server.service.impl;

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
        List<ClientDetails> clientDetails = (List<ClientDetails>)findByParams(whereCondition).getData();
        if (clientDetails.isEmpty()){
            throw new ClientRegistrationException("客户端不存在");
        }
        return clientDetails.get(0) ;
    }

}

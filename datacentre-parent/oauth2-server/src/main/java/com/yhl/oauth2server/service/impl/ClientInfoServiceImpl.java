package com.yhl.oauth2server.service.impl;

import com.yhl.base.baseService.impl.BaseServiceImpl;
import com.yhl.baseorm.component.constant.WhereCondition;
import com.yhl.oauth2server.dao.ClientInfoDao;
import com.yhl.oauth2server.entity.ClientInfo;
import com.yhl.oauth2server.service.ClientInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientInfoServiceImpl extends BaseServiceImpl<ClientInfo,String> implements ClientInfoService {
    @Autowired
    private ClientInfoDao clientInfoDao;
    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        WhereCondition whereCondition =new WhereCondition();
        whereCondition.and().addEq("clientId",clientId);
        List<ClientInfo> list =clientInfoDao.findByParams(whereCondition);
        return list.isEmpty()?new ClientInfo():list.get(0);
    }

}

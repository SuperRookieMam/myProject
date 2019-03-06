package com.yhl.oauthserver.service.impl;

import com.yhl.base.baseService.impl.BaseServiceImpl;
import com.yhl.baseorm.component.constant.WhereCondition;
import com.yhl.oauthserver.dao.ClinetDetailsInfoDao;
import com.yhl.oauthserver.entity.ClinetDetailsInfo;
import com.yhl.oauthserver.service.ClinetDetailsInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClinetDetailsInfoServiceImpl extends BaseServiceImpl<ClinetDetailsInfo,String> implements ClinetDetailsInfoService {

    @Autowired
    private ClinetDetailsInfoDao clinetDetailsInfoDao;
    /**
     * Load a client by the client id. This method must not return null.
     * */
    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        WhereCondition whereCondition =new WhereCondition();
        whereCondition.and().addEq("clientId",clientId);
        List<ClinetDetailsInfo> list =clinetDetailsInfoDao.findByParams(whereCondition);
        return list.isEmpty()?new ClinetDetailsInfo():list.get(0);
    }
}

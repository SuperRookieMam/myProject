package com.yhl.oauthserver.service.impl;

import com.yhl.base.baseService.impl.BaseServiceImpl;
import com.yhl.baseorm.component.constant.WhereCondition;
import com.yhl.oauthserver.dao.MyClientDetailDao;
import com.yhl.oauthserver.entity.MyClientDetail;
import com.yhl.oauthserver.service.MyClientDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MyClientDetailServiceImpl extends BaseServiceImpl<MyClientDetail,String> implements MyClientDetailService {

    @Autowired
    private MyClientDetailDao myClientDetailDao;
    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        WhereCondition whereCondition =new WhereCondition();
        whereCondition.and().addEq("clientId",clientId);
        List<MyClientDetail> list =myClientDetailDao.findByParams(whereCondition);
        return list.isEmpty()?null:list.get(0);
    }



}

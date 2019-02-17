package com.yhl.oauthserver.service.impl;

import com.yhl.base.baseService.impl.BaseServiceImpl;
import com.yhl.oauthserver.entity.MyClientDetail;
import com.yhl.oauthserver.service.MyClientDetailService;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Service;

@Service
public class MyClientDetailServiceImpl extends BaseServiceImpl<MyClientDetail,String> implements MyClientDetailService {

    @Override
    public ClientDetails loadClientByClientId(String s) throws ClientRegistrationException {
        return null;
    }



}

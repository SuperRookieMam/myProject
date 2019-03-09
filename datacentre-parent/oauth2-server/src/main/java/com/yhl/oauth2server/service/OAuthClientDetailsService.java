package com.yhl.oauth2server.service;

import com.yhl.base.baseService.BaseService;
import com.yhl.oauth2server.entity.OAuthClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;

public interface OAuthClientDetailsService  extends ClientDetailsService,BaseService<OAuthClientDetails,String>{
}

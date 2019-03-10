package com.yhl.zuulresource.service;

import com.yhl.base.baseService.BaseService;
import com.yhl.zuulresource.entity.OAuthClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;

public interface OAuthClientDetailsService  extends ClientDetailsService/*,BaseService<OAuthClientDetails,String>*/{
}

package com.yhl.oauth2server.service;

import com.yhl.base.baseService.BaseService;
import com.yhl.oauth2server.entity.ClientInfo;
import org.springframework.security.oauth2.provider.ClientDetailsService;

public interface ClientInfoService extends ClientDetailsService, BaseService<ClientInfo,String> {
}

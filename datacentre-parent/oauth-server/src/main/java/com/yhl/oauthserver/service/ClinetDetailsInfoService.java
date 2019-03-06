package com.yhl.oauthserver.service;

import com.yhl.base.baseService.BaseService;
import com.yhl.oauthserver.entity.ClinetDetailsInfo;
import org.springframework.security.oauth2.provider.ClientDetailsService;

public interface ClinetDetailsInfoService extends ClientDetailsService , BaseService<ClinetDetailsInfo,String> {
}

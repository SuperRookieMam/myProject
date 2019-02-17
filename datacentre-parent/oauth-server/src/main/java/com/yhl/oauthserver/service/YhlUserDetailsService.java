package com.yhl.oauthserver.service;

import com.yhl.base.baseService.BaseService;
import com.yhl.oauthserver.entity.YhlUserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface YhlUserDetailsService extends BaseService<YhlUserDetails,String>, UserDetailsService {
}

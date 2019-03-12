package com.yhl.oauth2server.service;

import com.yhl.base.baseService.BaseService;
import com.yhl.oauth2server.entity.OAthUserDetailes;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface OAthUserDetailesService extends UserDetailsService, BaseService<OAthUserDetailes,String> {
}

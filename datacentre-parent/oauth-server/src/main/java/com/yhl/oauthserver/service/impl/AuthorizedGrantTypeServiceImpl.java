package com.yhl.oauthserver.service.impl;

import com.yhl.base.baseService.impl.BaseServiceImpl;
import com.yhl.oauthserver.entity.AuthorizedGrantType;
import com.yhl.oauthserver.service.AuthorizedGrantTypeService;
import org.springframework.stereotype.Service;

@Service
public class AuthorizedGrantTypeServiceImpl extends BaseServiceImpl<AuthorizedGrantType,String> implements AuthorizedGrantTypeService {
}

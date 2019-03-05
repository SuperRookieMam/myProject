package com.yhl.oauthserver.service.impl;

import com.yhl.base.baseService.impl.BaseServiceImpl;
import com.yhl.oauthserver.entity.ClientScope;
import com.yhl.oauthserver.service.ClientResourceIdService;
import org.springframework.stereotype.Service;

@Service
public class ClientResourceIdServiceImpl extends BaseServiceImpl<ClientScope,String> implements ClientResourceIdService {
}

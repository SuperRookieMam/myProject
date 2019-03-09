package com.yhl.oauth2server.dao;

import com.yhl.base.baseDao.BaseDao;
import com.yhl.oauth2server.entity.ClientRegisteredRedirectUri;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRegisteredRedirectUriDao extends BaseDao<ClientRegisteredRedirectUri,String> {
}

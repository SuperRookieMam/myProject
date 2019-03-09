package com.yhl.oauth2server.dao;

import com.yhl.base.baseDao.BaseDao;
import com.yhl.oauth2server.entity.ClientScope;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientScopeDao extends BaseDao<ClientScope,String> {
}

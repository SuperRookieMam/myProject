package com.yhl.oauthserver.dao;

import com.yhl.base.baseDao.BaseDao;
import com.yhl.oauthserver.entity.ClientScope;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientResourceIdDao extends BaseDao<ClientScope,String> {
}

package com.yhl.oauthserver.dao;

import com.yhl.base.baseDao.BaseDao;
import com.yhl.oauthserver.entity.ClientResourceId;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientResourceIdDao extends BaseDao<ClientResourceId,String> {
}

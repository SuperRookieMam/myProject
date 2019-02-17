package com.yhl.oauthserver.dao;

import com.yhl.base.baseDao.BaseDao;
import com.yhl.oauthserver.entity.AuthorizedGrantType;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorizedGrantTypeDao extends BaseDao<AuthorizedGrantType,String> {
}

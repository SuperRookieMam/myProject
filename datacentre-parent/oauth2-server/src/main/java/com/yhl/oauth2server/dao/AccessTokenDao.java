package com.yhl.oauth2server.dao;

import com.yhl.base.baseDao.BaseDao;
import com.yhl.oauth2server.entity.AccessToken;
import org.springframework.stereotype.Repository;

@Repository
public interface AccessTokenDao extends BaseDao<AccessToken,String> {
}

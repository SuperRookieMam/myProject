package com.yhl.oauth2server.dao;

import com.yhl.base.baseDao.BaseDao;
import com.yhl.oauth2server.entity.OAuthRefreshToken;
import org.springframework.stereotype.Repository;

@Repository
public interface OAuthRefreshTokenDao extends BaseDao<OAuthRefreshToken,String> {
}

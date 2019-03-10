package com.yhl.zuulresource.dao;

import com.yhl.base.baseDao.BaseDao;
import com.yhl.zuulresource.entity.OAuthRefreshToken;
import org.springframework.stereotype.Repository;

@Repository
public interface OAuthRefreshTokenDao extends BaseDao<OAuthRefreshToken,String> {
}

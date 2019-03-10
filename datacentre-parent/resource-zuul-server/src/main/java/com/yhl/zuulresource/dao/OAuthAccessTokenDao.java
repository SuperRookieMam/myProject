package com.yhl.zuulresource.dao;

import com.yhl.base.baseDao.BaseDao;
import com.yhl.zuulresource.entity.OAuthAccessToken;
import org.springframework.stereotype.Repository;

@Repository
public interface OAuthAccessTokenDao extends BaseDao<OAuthAccessToken,String> {
}

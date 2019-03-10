package com.yhl.zuulresource.dao;

import com.yhl.base.baseDao.BaseDao;
import com.yhl.zuulresource.entity.OAuthClientToken;
import org.springframework.stereotype.Repository;

@Repository
public interface OAuthClientTokenDao extends BaseDao<OAuthClientToken,String> {
}

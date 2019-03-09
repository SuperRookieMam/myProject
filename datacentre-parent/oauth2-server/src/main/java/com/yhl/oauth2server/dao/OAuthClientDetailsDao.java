package com.yhl.oauth2server.dao;

import com.yhl.base.baseDao.BaseDao;
import com.yhl.oauth2server.entity.OAuthClientDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface OAuthClientDetailsDao  extends BaseDao<OAuthClientDetails,String> {
}

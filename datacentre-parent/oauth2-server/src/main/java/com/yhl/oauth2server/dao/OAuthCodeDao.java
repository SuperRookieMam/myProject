package com.yhl.oauth2server.dao;

import com.yhl.base.baseDao.BaseDao;
import com.yhl.oauth2server.entity.OAuthCode;
import org.springframework.stereotype.Repository;

@Repository
public interface OAuthCodeDao extends BaseDao<OAuthCode,String> {
}

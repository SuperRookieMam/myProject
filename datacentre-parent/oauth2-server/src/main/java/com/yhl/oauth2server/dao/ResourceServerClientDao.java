package com.yhl.oauth2server.dao;

import com.yhl.base.baseDao.BaseDao;
import com.yhl.oauth2server.entity.ResourceServerClient;
import org.springframework.stereotype.Repository;

@Repository
public interface ResourceServerClientDao extends BaseDao<ResourceServerClient,String> {
}

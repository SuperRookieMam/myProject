package com.yhl.oauth2server.dao;

import com.yhl.base.baseDao.BaseDao;
import com.yhl.oauth2server.entity.ResourceServer;
import org.springframework.stereotype.Repository;

@Repository
public interface ResourceServerDao extends BaseDao<ResourceServer,String> {
}

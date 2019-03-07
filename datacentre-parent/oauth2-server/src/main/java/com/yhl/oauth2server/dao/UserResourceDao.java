package com.yhl.oauth2server.dao;

import com.yhl.base.baseDao.BaseDao;
import com.yhl.oauth2server.entity.UserResource;
import org.springframework.stereotype.Repository;

@Repository
public interface UserResourceDao extends BaseDao<UserResource,String> {
}

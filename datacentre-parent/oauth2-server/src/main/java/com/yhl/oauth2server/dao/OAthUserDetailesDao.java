package com.yhl.oauth2server.dao;

import com.yhl.base.baseDao.BaseDao;
import com.yhl.oauth2server.entity.OAthUserDetailes;
import org.springframework.stereotype.Repository;

@Repository
public interface OAthUserDetailesDao extends BaseDao<OAthUserDetailes,String> {
}

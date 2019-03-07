package com.yhl.oauth2server.dao;

import com.yhl.base.baseDao.BaseDao;
import com.yhl.oauth2server.entity.UserInfo;
import org.springframework.stereotype.Repository;

@Repository
public interface UserInfoDao extends BaseDao<UserInfo,String> {
}

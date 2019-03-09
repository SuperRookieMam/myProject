package com.yhl.oauth2server.dao;

import com.yhl.base.baseDao.BaseDao;
import com.yhl.oauth2server.entity.UserApproval;
import org.springframework.stereotype.Repository;

@Repository
public interface UserApprovalDao extends BaseDao<UserApproval,String> {
}

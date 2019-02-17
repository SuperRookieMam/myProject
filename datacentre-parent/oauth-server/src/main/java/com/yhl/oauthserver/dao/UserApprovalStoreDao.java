package com.yhl.oauthserver.dao;

import com.yhl.base.baseDao.BaseDao;
import com.yhl.oauthserver.entity.UserApprovalStore;
import org.springframework.stereotype.Repository;

@Repository
public interface UserApprovalStoreDao  extends BaseDao<UserApprovalStore,String> {
}

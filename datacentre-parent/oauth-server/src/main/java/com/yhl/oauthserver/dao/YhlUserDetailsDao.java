package com.yhl.oauthserver.dao;

import com.yhl.base.baseDao.BaseDao;
import com.yhl.oauthserver.entity.YhlUserDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface YhlUserDetailsDao extends BaseDao<YhlUserDetails,String> {
}

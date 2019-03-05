package com.yhl.oauthserver.dao;

import com.yhl.base.baseDao.BaseDao;
import com.yhl.oauthserver.entity.ClinentResource;
import org.springframework.stereotype.Repository;

@Repository
public interface MyClinetScopDao extends BaseDao<ClinentResource,String> {

}

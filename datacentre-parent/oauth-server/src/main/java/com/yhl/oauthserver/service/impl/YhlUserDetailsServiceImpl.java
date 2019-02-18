package com.yhl.oauthserver.service.impl;

import com.yhl.base.baseService.impl.BaseServiceImpl;
import com.yhl.baseorm.component.constant.WhereCondition;
import com.yhl.oauthserver.dao.YhlUserDetailsDao;
import com.yhl.oauthserver.entity.YhlUserDetails;
import com.yhl.oauthserver.service.YhlUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class YhlUserDetailsServiceImpl extends BaseServiceImpl<YhlUserDetails,String> implements YhlUserDetailsService {
   @Autowired
   private YhlUserDetailsDao yhlUserDetailsDao;
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        WhereCondition whereCondition =new WhereCondition();
        whereCondition.and().addEq("userName",userName);
        List<YhlUserDetails> list =yhlUserDetailsDao.findByParams(whereCondition);
        return list.isEmpty()?null: list.get(0);
    }
}

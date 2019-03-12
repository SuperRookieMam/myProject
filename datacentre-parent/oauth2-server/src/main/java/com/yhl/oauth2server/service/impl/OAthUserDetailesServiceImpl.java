package com.yhl.oauth2server.service.impl;

import com.yhl.base.baseService.impl.BaseServiceImpl;
import com.yhl.baseorm.component.constant.WhereCondition;
import com.yhl.oauth2server.entity.OAthUserDetailes;
import com.yhl.oauth2server.service.OAthUserDetailesService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OAthUserDetailesServiceImpl extends BaseServiceImpl<OAthUserDetailes,String> implements OAthUserDetailesService {
    private  final String USERNAME ="userName";
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        WhereCondition whereCondition =new WhereCondition();
        whereCondition.and().addEq(USERNAME,username);
        List<OAthUserDetailes> list =(List<OAthUserDetailes>)findByParams(whereCondition).getData();
        if (list.isEmpty()){
            throw  new UsernameNotFoundException("没找到用户名");
        }
        return list.get(0) ;
    }
}

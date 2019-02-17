package com.yhl.oauthserver.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.yhl.base.baseService.impl.BaseServiceImpl;
import com.yhl.baseorm.component.constant.SelecteParam;
import com.yhl.oauthserver.entity.YhlUserDetails;
import com.yhl.oauthserver.service.YhlUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class YhlUserDetailsServiceImpl extends BaseServiceImpl<YhlUserDetails,String> implements YhlUserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        SelecteParam selecteParam =new SelecteParam();
        JSONObject jsonObject =new JSONObject();


        return null;
    }
}

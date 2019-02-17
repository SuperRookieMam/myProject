package com.yhl.oauthserver.service.impl;

import com.yhl.base.baseService.impl.BaseServiceImpl;
import com.yhl.oauthserver.entity.UserApprovalStore;
import com.yhl.oauthserver.service.UserApprovalStoreService;
import org.springframework.security.oauth2.provider.approval.Approval;
import org.springframework.stereotype.Service;

import java.util.Collection;

//那个客户段对应用户的权限域的存储
@Service
public class UserApprovalStoreServiceImpl extends BaseServiceImpl<UserApprovalStore,String> implements UserApprovalStoreService {

    @Override
    public boolean addApprovals(Collection<Approval> collection) {
        return false;
    }

    @Override
    public boolean revokeApprovals(Collection<Approval> collection) {
        return false;
    }

    @Override
    public Collection<Approval> getApprovals(String s, String s1) {
        return null;
    }
}

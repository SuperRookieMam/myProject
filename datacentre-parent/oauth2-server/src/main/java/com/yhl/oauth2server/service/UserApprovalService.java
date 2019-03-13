package com.yhl.oauth2server.service;

import com.yhl.base.baseService.BaseService;
import com.yhl.oauth2server.entity.UserApproval;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;

public interface UserApprovalService extends BaseService<UserApproval,String>,ApprovalStore {
}

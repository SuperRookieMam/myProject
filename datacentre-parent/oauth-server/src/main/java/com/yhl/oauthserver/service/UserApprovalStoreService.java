package com.yhl.oauthserver.service;

import com.yhl.base.baseService.BaseService;
import com.yhl.oauthserver.entity.UserApprovalStore;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;

public interface UserApprovalStoreService  extends ApprovalStore, BaseService<UserApprovalStore,String> {
}
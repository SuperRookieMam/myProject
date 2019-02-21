package com.yhl.oauthserver.service.impl;

import com.yhl.base.baseService.impl.BaseServiceImpl;
import com.yhl.baseorm.component.constant.WhereCondition;
import com.yhl.oauthserver.entity.UserApprovalStore;
import com.yhl.oauthserver.service.UserApprovalStoreService;
import org.springframework.security.oauth2.provider.approval.Approval;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

//那个客户段对应用户的权限域的存储
@Service
public class UserApprovalStoreServiceImpl extends BaseServiceImpl<UserApprovalStore,String> implements UserApprovalStoreService {

    @Override
    @Transactional(value ="transactionManagerPrimary")
    public boolean addApprovals(Collection<Approval> collection) {
        try {
            insertByList(UserApprovalStore.approvalToUserApprovalStore(collection));
        }catch (Exception e){
            return false;
        }
        return true;
    }

    @Override
    @Transactional(value ="transactionManagerPrimary")
    public boolean revokeApprovals(Collection<Approval> collection) {
        try {
            if (collection.isEmpty()){
                return  false;
            }
            String[] userIds =new String[collection.size()];
            String[] clientIds =new String[collection.size()];
            String[] scopes =new String[collection.size()];
            Iterator<Approval> iterator =collection.iterator();
            int i=0;
            while (iterator.hasNext()){
                Approval approval =iterator.next();
                userIds[i] =approval.getUserId();
                clientIds[i] =approval.getClientId();
                scopes[i] =approval.getScope();
                i++;
            }
            WhereCondition whereCondition =new WhereCondition();
            whereCondition.and().addIn("userId",userIds)
                                .addIn("clientId",clientIds)
                                .addIn("scope",scopes);
            deleteByWhereCondition(whereCondition);
        }catch (Exception e){
            return false;
        }
        return false;
    }

    @Override
    public Collection<Approval> getApprovals(String userId, String clientId) {
        WhereCondition whereCondition =new WhereCondition();
        whereCondition.and().addEq("userId",userId)
                            .addEq("clientId",clientId);

        Object object =findByParams(whereCondition).getData();
        return (List)object;
    }


}

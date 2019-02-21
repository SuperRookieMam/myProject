package com.yhl.oauthserver.entity;

import com.yhl.base.baseEntity.BaseEntity;
import lombok.Data;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.oauth2.provider.approval.Approval;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity
@Table(name = "user_approval_store",uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id","client_id","scope"})})
// @IdClass(UserApprovalPK.class) 联合主键用这个，但是我先在继承勒baseentity 不用这个用table的属性
//EntityListeners在jpa中使用，如果你是mybatis是不可以用的
//对实体属性变化的跟踪，它提供了保存前，保存后，更新前，更新后，删除前，删除后等状态，就像是拦截器一样，
// 你可以在拦截方法里重写你的个性化逻辑。
@EntityListeners(AuditingEntityListener.class)
@Data
public class UserApprovalStore extends BaseEntity<String> implements Serializable {

    private static final long serialVersionUID = 138296080858479529L;

    @Column(name = "user_id", length = 50)
    private String userId;

    @Column(name = "client_id", length = 50)
    private String clientId;

    @Column(name = "scope", length = 100)
    private String scope;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Approval.ApprovalStatus status;

    @Column(name = "expires_at")
    private Date expiresAt;

    @Column(name = "last_update_at")
    @LastModifiedDate
    private Date lastUpdatedAt;

    public  static List<UserApprovalStore> approvalToUserApprovalStore(Collection collection){
        List<UserApprovalStore> list=new ArrayList<>();
        Iterator<Approval> iterator = collection.iterator();
        while (iterator.hasNext()){
            Approval approval =iterator.next();
            UserApprovalStore userApprovalStore =new UserApprovalStore();
            userApprovalStore.setClientId(approval.getClientId());
            userApprovalStore.setExpiresAt(approval.getExpiresAt());
            userApprovalStore.setLastUpdatedAt(approval.getLastUpdatedAt());
            userApprovalStore.setScope(approval.getScope());
            userApprovalStore.setUserId(approval.getUserId());
            list.add(userApprovalStore);
        }
        return list;
    }
}

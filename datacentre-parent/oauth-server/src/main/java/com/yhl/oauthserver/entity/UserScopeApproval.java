package com.yhl.oauthserver.entity;

import com.yhl.authoritycommom.entity.Department;
import com.yhl.authoritycommom.entity.Organization;
import com.yhl.base.baseEntity.BaseEntity;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "user_scope_approval")
@Data
public class UserScopeApproval extends BaseEntity<String> {
    private static final long serialVersionUID = -768981844367825170L;

    @Column(name = "user_id", length = 50)
    private String userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organization_id")
    private Organization organizationId;

    @Column(name = "scope")
    private String scope;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department")
    private Department department;

}

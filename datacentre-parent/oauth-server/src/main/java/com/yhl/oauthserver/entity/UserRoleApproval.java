package com.yhl.oauthserver.entity;

import com.yhl.authoritycommom.entity.Department;
import com.yhl.authoritycommom.entity.Organization;
import com.yhl.authoritycommom.entity.RoleInfo;
import com.yhl.base.baseEntity.BaseEntity;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "user_role_approval")
@Data
public class UserRoleApproval extends BaseEntity<String> {
    private static final long serialVersionUID = -768981844367825170L;

    @Column(name = "user_id", length = 50)
    private String userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organization_id")
    private Organization organizationId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role")
    private RoleInfo role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department")
    private Department department;

}

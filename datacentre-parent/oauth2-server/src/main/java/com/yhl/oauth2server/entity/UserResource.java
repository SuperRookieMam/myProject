package com.yhl.oauth2server.entity;

import com.yhl.base.baseEntity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * 存储客户可以访问那些资源服务器
 * */
@Entity
@Getter
@Setter
@Table(name = "user_resource",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"user_name","clientId"})},
        indexes = {@Index(columnList = "user_name")})
public class UserResource  extends BaseEntity<String> {
    private static final long serialVersionUID = -2359280941309892014L;

    @Column(name = "user_name",nullable = false)
    private  String userName;

    @Column(name = "client_id", nullable = false, length = 50)
    private String clientId;

    @Column(name = "role", nullable = false, length = 50)
    private String role;
}

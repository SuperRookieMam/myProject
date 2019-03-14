package com.yhl.oauth2server.entity;

import com.yhl.base.baseEntity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "resource_server_client",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"client_id","resource_id"})},
        indexes = {@Index(columnList = "client_id")})
public class ResourceServerClient  extends BaseEntity<String>  {

    @Column(name = "client_id")
    private String clientId;


    @Column(name = "resource_id")
    private String resourceId;
}

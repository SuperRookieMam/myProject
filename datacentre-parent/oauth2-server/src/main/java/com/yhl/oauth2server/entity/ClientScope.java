package com.yhl.oauth2server.entity;

import com.yhl.base.baseEntity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "client_scope",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"client_id","scope"})},
        indexes = {@Index(columnList = "client_id")})
public class ClientScope  extends BaseEntity<String> {

    @Column(name = "client_id")
    private String clientId;

    @Column(name = "scope")
    private String scope;
}

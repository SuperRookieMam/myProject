package com.yhl.oauth2server.entity;

import javax.persistence.Column;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Table(name = "resource_server_client",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"client_id","resource_id"})},
        indexes = {@Index(columnList = "client_id")})
public class ResourceServerClient {

    @Column(name = "client_id")
    private String clientId;


    @Column(name = "resource_id")
    private String resourceId;
}

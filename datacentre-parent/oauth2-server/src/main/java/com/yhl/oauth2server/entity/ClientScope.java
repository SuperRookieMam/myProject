package com.yhl.oauth2server.entity;

import javax.persistence.Column;
import javax.persistence.Table;

@Table(name = "client_scope")
public class ClientScope {

    @Column(name = "client_id")
    private String clientId;

    @Column(name = "scope")
    private String scope;
}

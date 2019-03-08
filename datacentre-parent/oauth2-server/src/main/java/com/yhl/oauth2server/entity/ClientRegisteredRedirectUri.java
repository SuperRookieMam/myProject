package com.yhl.oauth2server.entity;

import javax.persistence.Column;
import javax.persistence.Table;

@Table(name = "client_registered_redirect_uri")
public class ClientRegisteredRedirectUri {

    @Column(name = "client_id", nullable = false, length = 50)
    private String clientId;

    @Column(name = "redirect_uri", nullable = false, length = 50)
    private String redirectUri;
}

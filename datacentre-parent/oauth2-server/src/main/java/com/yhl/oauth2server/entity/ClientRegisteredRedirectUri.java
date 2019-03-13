package com.yhl.oauth2server.entity;

import com.yhl.base.baseEntity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "client_registered_redirect_uri",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"client_id","redirect_uri"})},
        indexes = {@Index(columnList = "client_id")})
public class ClientRegisteredRedirectUri extends BaseEntity<String>  implements com.yhl.authoritycommom.entity.ClientRegisteredRedirectUri {

    private static final long serialVersionUID = 5659395845747258201L;
    @Column(name = "client_id", nullable = false, length = 50)
    private String clientId;

    @Column(name = "redirect_uri", nullable = false, length = 50)
    private String redirectUri;
}

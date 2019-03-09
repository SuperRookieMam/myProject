package com.yhl.oauthserver.entity;

import com.yhl.base.baseEntity.BaseEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "clinet_resource")
@Data
public class ClinentResource extends BaseEntity<String>{
    private static final long serialVersionUID = -6444299595553304443L;

    @Column(name = "client_id", nullable = false, length = 50)
    private String clientId;

    @Column(name = "resource", nullable = false, length = 50)
    private String resource;

    @Column(name = "register_url", nullable = false, length = 50)
    private String registerUrl;
}

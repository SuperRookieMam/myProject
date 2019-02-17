package com.yhl.oauthserver.entity;

import com.yhl.base.baseEntity.BaseEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "client_resource_id")  // 此客户端可以访问的资源地址
@Data
public class ClientResourceId extends BaseEntity<String> implements Serializable {

    @Column(name = "client_id", nullable = false, length = 50)
    private String clientId;

    @Column(name = "resource_id", nullable = false, length = 50)
    private String resource_id;
}

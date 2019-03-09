package com.yhl.oauthserver.entity;

import com.yhl.base.baseEntity.BaseEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;


/**
 * clinet_resource 是按照客户端普通权限的的方式来限制
 * 这个针对某个客户端只能访问某些东西，当这个不为空时取交集
 * */
@Entity
@Table(name = "client_scope")
@Data
public class ClientScope extends BaseEntity<String> {

    @Column(name = "client_id", nullable = false, length = 50)
    private String clientId;

    @Column(name = "client_scope", nullable = false, length = 50)
    private String clientScope;
}

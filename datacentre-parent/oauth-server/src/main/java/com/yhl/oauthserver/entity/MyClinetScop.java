package com.yhl.oauthserver.entity;

import com.yhl.base.baseEntity.BaseEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "my_clinet_scop") //
@Data
public class MyClinetScop extends BaseEntity<String> implements Serializable{
    private static final long serialVersionUID = -6444299595553304443L;

    @Column(name = "client_id", nullable = false, length = 50)
    private String clientId;

    @Column(name = "scop", nullable = false, length = 50)
    private String scop;


}

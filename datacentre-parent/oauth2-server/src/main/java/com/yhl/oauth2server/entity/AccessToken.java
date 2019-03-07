package com.yhl.oauth2server.entity;

import com.yhl.base.baseEntity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter
@Table(name = "access_token",
       uniqueConstraints = {@UniqueConstraint(columnNames = {"user_info","client_info"})}
)
public class AccessToken extends BaseEntity<String> {
     private static final long serialVersionUID = -5620808551700545738L;

     @ManyToOne(fetch = FetchType.EAGER)
     @JoinColumn(name = "user_info")
     private UserInfo userInfo;

     @ManyToOne(fetch = FetchType.EAGER)
     @JoinColumn(name = "client_info")
     private ClientInfo clientInfo;
}

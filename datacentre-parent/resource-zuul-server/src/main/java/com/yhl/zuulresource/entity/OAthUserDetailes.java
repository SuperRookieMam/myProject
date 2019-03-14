package com.yhl.zuulresource.entity;

import com.yhl.base.baseEntity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "oath_user_detailes")
public class OAthUserDetailes extends BaseEntity<String> {

    private static final long serialVersionUID = 9056596580975978130L;
    @Column(name = "user_name")
    private  String userName;

    @Column(name = "pass_word")
    private  String passWord;

    @Column(name = "is_expired")
    private boolean isExpired;

    @Column(name = "is_lock")
    private boolean isLock;

    @Column(name = "credentials")
    private String credentials;

    @Column(name = "is_enabled")
    private boolean isEnabled;


}

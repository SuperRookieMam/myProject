package com.yhl.oauth2server.entity;

import com.yhl.base.baseEntity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "resource_server")
public class ResourceServer  extends BaseEntity<String>  {

    /**
     * 资源服务器名字
     * */
    @Column(name="name")
    private  String name;
    /**
     * 注册地址
     * */
    @Column(name="register_url")
    private  String registerUrl;
    /**
     * 说明
     * */
    @Column(name="remark")
    private  String remark;
    /**
     *是否使用
     * */
    @Column(name="is_use")
    private Integer isUse= 1;
}

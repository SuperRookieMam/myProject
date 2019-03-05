package com.yhl.oauthserver.entity;

import com.alibaba.fastjson.JSONObject;
import com.yhl.base.baseEntity.BaseEntity;
import lombok.Data;
import org.springframework.data.domain.Persistable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

/**
 * 自定义客户端信息
 * */
@Entity
@Table(name ="my_client_detail")
@Data
public class MyClientDetail extends BaseEntity<String>  implements Persistable<String>,ClientDetails,Serializable {

    private static final long serialVersionUID = -6186893015772300645L;

    //此客户端可以访问的资源。如果为空，调用方可以忽略。
    @Column(name = "client_id", nullable = false, length = 50)
    private String clientId;

    @Column(name = "client_secret")
    private String clientSecret;

    @Column(name = "name")
    private String name;

    //客户端的作用域？可以访问哪些资源服务器
    @ElementCollection(fetch = FetchType.EAGER)
    @Column(name = "scope")
    @CollectionTable(name = "my_clinet_scop", joinColumns = {
            @JoinColumn(name = "client_id",referencedColumnName = "client_id")
    })
    //@OrderColumn(name="Priority")  要排序可以这样处理
    private Set<String> scope;

    //授权类型，验证方式
    @ElementCollection(fetch = FetchType.EAGER)
    @Column(name = "grant_type")
    @CollectionTable(name = "authorized_grant_type", joinColumns = {
            @JoinColumn(name = "client_id",referencedColumnName = "client_id")
    })
    private Set<String> authorizedGrantTypes;

    //注册地址,跳转地址
    @ElementCollection(fetch = FetchType.EAGER)
    @Column(name = "register_url")
    @CollectionTable(name = "authorized_grant_type", joinColumns = {
            @JoinColumn(name = "client_id",referencedColumnName = "client_id")
    })
    private Set<String> registeredRedirectUri;

    //资源对应的资源服务器的Id
    @ElementCollection(fetch = FetchType.EAGER)
    @Column(name = "resource_id")
    @CollectionTable(name = "client_resource_id", joinColumns = {
            @JoinColumn(name = "client_id",referencedColumnName = "client_id")
    })
    private Set<String> resourceIds;


    // token 存货时间
    @Column(name = "access_token_validity_seconds")
    private Integer accessTokenValiditySeconds;
    //token时间
    @Column(name = "refresh_token_validity_seconds")
    private Integer refreshTokenValiditySeconds;

    @Column(name = "auto_approve", nullable = false)
    private boolean autoApprove = false;


    @Override
    public boolean isNew() {
        return StringUtils.isEmpty(clientId);
    }

    @Override  // ClientDetails 此客户是否需要秘钥验证
    public boolean isSecretRequired() {
        return this.clientSecret != null;
    }

    @Override // ClientDetails   客户的作用域
    public boolean isScoped() {
        return this.scope != null && !this.scope.isEmpty();
    }
    // ClientDetails
    // 反悔客户端的权限不能为null
    // 暂时不需要服务器做这个认证，
    // 这些信息待给客户端比较危险，由资源服务器验证
    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return  Collections.emptyList();
    }
    // ClientDetails
    // Test whether client needs user approval for a particular scope.
    // 测试客户端是否需要用户批准特定的范围。
    @Override
    public boolean isAutoApprove(String s) {
        return autoApprove;
    }
    /**
     * 此客户端的附加信息，普通OAuth协议不需要，但可能有用，例如，
     *用于存储描述性信息，但是要建立个超级用户的话就需要这个了，暂时不需要
     * */
    @Override // ClientDetails
    public Map<String, Object> getAdditionalInformation() {
        return new HashMap<>();
    }
}

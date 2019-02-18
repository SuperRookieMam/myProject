package com.yhl.oauthserver.entity;

import com.yhl.base.baseEntity.BaseEntity;
import lombok.Data;
import org.springframework.data.domain.Persistable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * 自定义客户端信息
 * */
@Entity
@Table(name ="my_client_detail")
@Data
public class MyClientDetail extends BaseEntity<String>  implements Persistable<String>,ClientDetails,Serializable {

    private static final long serialVersionUID = -6186893015772300645L;

    @Column(name = "client_id", nullable = false, length = 50)
    private String clientId;

    @Column(name = "client_secret")
    private String clientSecret;

    @Column(name = "name")
    private String name;

    //读写权限
    //@CollectionTable：给出关联表格信息。表格Employee_Phone的外键Employee指向
    // entity对应表格的EmployeeId列。
    //@Column 读取CollectionTable中的scope列信息
    // @OrderColumn：以Priority为排序，
    // 存放到集合（List）中。表格中的priority或是List的index，即0,1,2,...
    @ElementCollection(fetch = FetchType.EAGER)
    @Column(name = "scope")
    //@OrderColumn(name="Priority")  要排序可以这样处理
    @CollectionTable(name = "my_clinet_scop", joinColumns = {
            @JoinColumn(name = "client_id")
    })
    private Set<String> scope;

    @ElementCollection(fetch = FetchType.EAGER)
    @Column(name = "grant_type")
    @CollectionTable(name = "authorized_grant_type", joinColumns = {
            @JoinColumn(name = "client_id")
    })
    private Set<String> authorizedGrantTypes;


    @ElementCollection(fetch = FetchType.EAGER)
    @Column(name = "register_url")
    @CollectionTable(name = "authorized_grant_type", joinColumns = {
            @JoinColumn(name = "client_id")
    })
    private Set<String> registeredRedirectUri;


    @ElementCollection(fetch = FetchType.EAGER)
    @Column(name = "resource_id_")
    @CollectionTable(name = "client_resource_id", joinColumns = {
            @JoinColumn(name = "client_id")
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
    @Override
    public boolean isSecretRequired() {
        return false;
    }

    @Override
    public boolean isScoped() {
        return false;
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public boolean isAutoApprove(String s) {
        return false;
    }

    @Override
    public Map<String, Object> getAdditionalInformation() {
        return null;
    }
}

package com.yhl.oauth2server.entity;

import com.yhl.base.baseEntity.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.util.*;

@Entity
@Getter
@Setter
@Table(name ="client_info")
public class ClientInfo extends BaseEntity<String> implements ClientDetails {

    private static final long serialVersionUID = -6981484509412993417L;

    @Column(name = "client_id", nullable = false, length = 50)
    private String clientId;

    @Column(name = "client_secret")
    private String clientSecret;

    /*
     * 反悔特定的资源
     * */
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "client_scope", joinColumns = {
            @JoinColumn(name = "client_id",referencedColumnName = "client_id")
    })//链表client_scope的外键client_id指向 词表的client_id
    @Column(name = "client_scope")  // 读取client_scope 放入到集合中
    //@OrderColumn(name="Priority")  要排序可以这样处理
    private Set<String> scopes;

    @ElementCollection(fetch = FetchType.EAGER)
    @Column(name = "grant_type")
    @CollectionTable(name = "authorized_grant_type", joinColumns = {
            @JoinColumn(name = "client_id",referencedColumnName = "client_id")
    })
    private Set<String> authorizedGrantTypes;

    @ElementCollection(fetch = FetchType.EAGER)
    @Column(name = "resource")
    @CollectionTable(name = "clinet_resource", joinColumns = {
            @JoinColumn(name = "client_id",referencedColumnName = "client_id")
    })
    @OrderColumn(name = "id")
    private Set<String>  resourceIds;

    @ElementCollection(fetch = FetchType.EAGER)
    @Column(name = "register_url")
    @CollectionTable(name = "clinet_resource", joinColumns = {
            @JoinColumn(name = "client_id",referencedColumnName = "client_id")
    })
    @OrderColumn(name = "id")//两个按照ID对应顺序呢
    private Set<String> registeredRedirectUri;

    @Column(name = "access_token_validity_seconds")
    private Integer accessTokenValiditySeconds;

    @Column(name = "refresh_token_validity_seconds")
    private Integer refreshTokenValiditySeconds;

    @Column(name = "auto_approve", nullable = false)
    private boolean autoApprove = false;

    @Column(name = "organization_id")
    private String organizationId;

    @Override
    public String getClientId() {
        return clientId;
    }
    /**The resources that this client can access.
     *Can be ignored by callers if empty.
     */
    @Override
    public Set<String> getResourceIds() {
        return resourceIds;
    }
    //Whether a secret is required to authenticate this client.
    @Override
    public boolean isSecretRequired() {
        return  !StringUtils.isEmpty(clientSecret);
    }

    @Override
    public String getClientSecret() {
        return clientSecret;
    }
    /*
     * hether this client is limited to a specific(特殊) scope.
     * If false, the scope of the authentication request will be
     * ignored.
     * 这个客户被限制在一个特定的范围内。
     * 如果为false，则身份验证请求的范围为*忽略
     * */
    @Override
    public boolean isScoped() {
        return  this.scopes != null && !this.scopes.isEmpty();
    }

    @Override
    public Set<String> getScope() {
        return scopes;
    }

    /**
     * The grant types for which this client is authorized.
     * 此客户端当前的授权类型。
     * */
    @Override
    public Set<String> getAuthorizedGrantTypes() {
        return authorizedGrantTypes;
    }
    /**
     * The pre-defined redirect URI for this client to use during the "authorization_code" access grant.
     * 此客户端在“authorization_code”访问授权成功后使用的预定义URI。
     * */
    @Override
    public Set<String> getRegisteredRedirectUri() {
        return registeredRedirectUri;
    }
    /**
     * Returns the authorities that are granted to the OAuth client. Cannot return <code>null</code>.
     * Note that these are NOT the authorities that are granted to the user with an authorized access token.
     * Instead, these authorities are inherent to the client itself.
     * 返回授予OAuth客户端的权限。不能返null。
     * 请注意，这些不是授予具有授权访问令牌的用户的权限。
     * 相反，这些权限是客户本身固有的。
     * */
    @Override//比如说可以返回用户的
    public Collection<GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> list = Collections.emptyList();
        //没有特定返回资源
        if (scopes==null||scopes.isEmpty()) {

        }else {//有特定返回特定

        }
        return list;
    }
    /**
     * The access token validity period for this client.
     * Null if not set explicitly（明确）
     * (implementations might use that fact to provide a default value for instance).
     * token的有效期
     * */
    @Override
    public Integer getAccessTokenValiditySeconds() {
        return accessTokenValiditySeconds;
    }
    /**
     * The refresh token validity period for this client.
     * Null for default value set by token service, and
     * zero or negative for non-expiring tokens.
     * 此客户端的刷新令牌有效期。 Null表示令牌服务设置的默认值，和非过期令牌为零或负数。
     * */
    @Override
    public Integer getRefreshTokenValiditySeconds() {
        return refreshTokenValiditySeconds;
    }
    /**
     * Test whether client needs user approval for a particular scope
     * 客户端是否需要用户对特定范围的批准
     * */
    @Override
    public boolean isAutoApprove(String scope) {
        return autoApprove;
    }
    /**
     * Additional information for this client,
     * not needed by the vanilla OAuth protocol but might be useful, for example,
     * for storing descriptive information.
     * 此客户端的附加信息，普通OAuth协议不需要，但可能有用，例如，
     * 用于存储描述性信息。
     * */
    @Override
    public Map<String, Object> getAdditionalInformation() {
        return null;
    }
}

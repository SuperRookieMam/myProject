package com.yhl.oauthserver.entity;

import com.yhl.base.baseEntity.BaseEntity;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Collection;
import java.util.Collections;


@Entity
@Table(name = "user_details_info")
@Data
public class UserDetailsInfo extends BaseEntity<String> implements UserDetails {
    private static final long serialVersionUID = -2272863665496205308L;

    @Column(name = "user_name")
    private  String userName;

    @Column(name = "password")
    private String password;

    @Column(name = "nick_name")
    private String nickName;

    @Column(name = "is_expired")
    private Integer isExpired;

    @Column(name = "is_locked")
    private Integer isLocked;

    @Column(name = "is_enable")
    private Integer isEnable;

    //凭证
    @Column(name = "credentia")
    private String credentia;

    /*//那些公司的那些角色
    @OneToMany
    @CollectionTable(name = "user_role_approval", joinColumns = {
            @JoinColumn(name = "user_id",referencedColumnName = "id")
    })
    private List<UserRoleApproval> roles;
    @OneToMany
    @CollectionTable(name = "user_scope_approval", joinColumns = {
            @JoinColumn(name = "user_id",referencedColumnName = "id")
    })
    private List<UserScopeApproval> scopes;*/
    /**
     *Returns the authorities granted to the user.
     * Cannot return <code>null</code>
     * 返回客户固有的权限
     * 准备用户为基本信息，名字唯一，根据名字返回对应的公司，和服务器资源的
     * 暂时不做先做个初版
     * */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName;
    }
    /**
     * Indicates whether the user's account has expired.
     * An expired account cannot be authenticated.
     * 指示用户的帐户是否已过期。过期帐户无法验证
     * */
    @Override
    public boolean isAccountNonExpired() {
        return isExpired==1;
    }

    /**
     * Indicates whether the user is locked or unlocked.
     * A locked user cannot be authenticated.
     * */
    @Override
    public boolean isAccountNonLocked() {
        return isLocked==1;
    }
    /**
     * Indicates whether the user's credentials (password) has expired.
     * Expired credentials prevent authentication.
     * 指示用户的凭据(密码)是否已过期。
     * 过期凭证阻止身份验证
     * */
    @Override
    public boolean isCredentialsNonExpired() {
        return StringUtils.isEmpty(credentia);
    }
    /**
     * Indicates whether the user is enabled or disabled.
     * A disabled user cannot be authenticated.
     * */
    @Override
    public boolean isEnabled() {
        return isEnable==0;
    }
}

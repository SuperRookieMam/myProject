package com.yhl.oauth2server.entity;

import com.yhl.base.baseEntity.BaseEntity;
import com.yhl.oauth2server.componet.ouathConverter.doman.YGrantedAuthority;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "user_info",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"user_name"})},
        indexes = {@Index(columnList = "user_name")})
public class UserInfo  extends BaseEntity<String> implements UserDetails {
    private static final long serialVersionUID = 1527498052245857231L;

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

    //当前客户可以访问那些资源服务器
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "resources")
    private List<UserResource> resources;
    /**
     *Returns the authorities granted to the user.
     * Cannot return <code>null</code>
     * 返回客户固有的权限
     * 返回客户可以访问那些资源服务器key
     * 返回客户在这个资源服务器的角色
     * */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<YGrantedAuthority> list = Collections.emptyList();
        for (int i = 0; i < resources.size(); i++) {
            UserResource userResource =resources.get(i);
            YGrantedAuthority yGrantedAuthority =new YGrantedAuthority();
            yGrantedAuthority.setKey(userResource.getClientId());
            yGrantedAuthority.setValues(userResource.getRole());
            list.add(yGrantedAuthority);
        }
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

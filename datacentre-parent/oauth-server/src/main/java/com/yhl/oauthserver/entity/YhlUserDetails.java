package com.yhl.oauthserver.entity;

import com.yhl.authoritycommom.entity.Department;
import com.yhl.authoritycommom.entity.RoleInfo;
import com.yhl.base.baseEntity.BaseEntity;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 *Security 对user用户权限管理
 * */
@Entity
@Table(name = "yhl_user_details")
@Data
public class YhlUserDetails extends BaseEntity<String> implements UserDetails {

    private static final long serialVersionUID = 5284636444102939763L;

    @Column(name = "user_name")
    private  String userName;

    @Column(name = "password")
    private String password;

    @Column(name = "nick_name")
    private String nickName;

    @ManyToOne
    @Column(name = "role")
    private RoleInfo role;

    @Column(name = "is_expired")
    private Integer isExpired;

    @Column(name = "is_locked")
    private Integer isLocked;

    @Column(name = "is_enable")
    private Integer isEnable;

    //凭证
    @Column(name = "credentia")
    private String credentia;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> list = new ArrayList<>();
        /*list.add(new SimpleGrantedAuthority(role));*/
        return list;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return isExpired==0;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isLocked==1;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentia==null;
    }

    @Override
    public boolean isEnabled() {
        return isEnable==1;
    }

}

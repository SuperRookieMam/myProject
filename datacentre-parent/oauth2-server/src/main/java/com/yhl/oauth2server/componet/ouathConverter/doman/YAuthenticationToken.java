package com.yhl.oauth2server.componet.ouathConverter.doman;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class YAuthenticationToken extends AbstractAuthenticationToken {


    private static final long serialVersionUID = 7337173326156960541L;
    private final String principal;
    private Object credentials;

    public YAuthenticationToken(String principal, Object credentials) {
        super(null);
        this.principal =principal;
        this.credentials = credentials;
        setAuthenticated(false);
    }

    public YAuthenticationToken(String principal, Object credentials,
                                               Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        this.credentials = credentials;
        super.setAuthenticated(true); // must use super, as we override
    }
    @Override
    public Object getCredentials() {
        return credentials;
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        if (isAuthenticated) {
            throw new IllegalArgumentException(
                    "Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        }

        super.setAuthenticated(false);
    }

    @Override
    public void eraseCredentials() {
        super.eraseCredentials();
        credentials = null;
    }
}

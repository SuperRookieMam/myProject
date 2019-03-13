package com.yhl.oauth2server.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import org.springframework.security.core.GrantedAuthority;

@JsonInclude(Include.NON_NULL)
public class OAthGrantedAuthority implements GrantedAuthority {
	private static final long serialVersionUID = 4062924753193768577L;
	private String authority;
	private Long id;

	public OAthGrantedAuthority(String authority, Long id) {
		super();
		this.authority = authority;
		this.id = id;
	}

	public OAthGrantedAuthority(String authority) {
		this(authority, null);
	}

	public OAthGrantedAuthority() {
		this(null);
	}

	@Override
	public String getAuthority() {
		return authority;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}

}

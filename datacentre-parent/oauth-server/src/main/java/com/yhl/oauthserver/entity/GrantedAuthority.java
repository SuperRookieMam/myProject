package com.yhl.oauthserver.entity;

import com.yhl.base.baseEntity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GrantedAuthority extends BaseEntity<String> implements org.springframework.security.core.GrantedAuthority {

    private static final long serialVersionUID = 1213161405563891544L;

    private String typekey;

    private String value;

    public GrantedAuthority(String typekey,String value){
        this.typekey=typekey;
        this.value =value;
    }


    public GrantedAuthority(){
        this.typekey="";
        this.value ="";
    }

    @Override
    public String getAuthority() {
        return typekey+"-"+value;
    }
}

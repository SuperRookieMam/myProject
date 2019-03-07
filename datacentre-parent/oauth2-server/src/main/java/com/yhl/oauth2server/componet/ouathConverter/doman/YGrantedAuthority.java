package com.yhl.oauth2server.componet.ouathConverter.doman;

import com.alibaba.fastjson.JSONObject;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

@Getter
@Setter
public class YGrantedAuthority implements GrantedAuthority {

    private String key;

    private String values;

    @Override
    public String toString(){
        JSONObject jsonObject =new JSONObject();
        jsonObject.put(key,values);
        return jsonObject.toJSONString();
    }
    @Override
    public String getAuthority() {
        return toString();
    }
}

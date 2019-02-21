package com.yhl.secritycommon.access;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.ConfigAttribute;

import java.util.Set;
@Getter
@Setter
public class RequestAuthorityAttribute implements ConfigAttribute {
    private static final long serialVersionUID = 5861389645003319286L;

    /**
     * 资源的匹配模式
     * @author
     */
    public enum MatchType {
        /**
         * 路径匹配
         */
        ANT_PATH,
        /**
         * 正则表达式匹配
         */
        REGEXP,
    }
    /**
     * 授权角色
     */
    private String authority;

    private String pattern;

    private MatchType matchType;

    private HttpMethod method;

    private Boolean accessable;

    private Set<String> scope;


    public RequestAuthorityAttribute() {
        super();
    }
    public RequestAuthorityAttribute(String authority, String pattern, MatchType matchType, Set<String> scope, HttpMethod method, Boolean accessable) {
        super();
        this.authority = authority;
        this.scope = scope;
        this.pattern = pattern;
        this.matchType = matchType;
        this.method = method;
        this.accessable = accessable;
    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((accessable == null) ? 0 : accessable.hashCode());
        result = prime * result + ((authority == null) ? 0 : authority.hashCode());
        result = prime * result + ((matchType == null) ? 0 : matchType.hashCode());
        result = prime * result + ((method == null) ? 0 : method.hashCode());
        result = prime * result + ((pattern == null) ? 0 : pattern.hashCode());
        result = prime * result + ((scope == null) ? 0 : scope.hashCode());
        return result;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        RequestAuthorityAttribute other = (RequestAuthorityAttribute) obj;
        if (accessable == null) {
            if (other.accessable != null)
                return false;
        } else if (!accessable.equals(other.accessable))
            return false;
        if (authority == null) {
            if (other.authority != null)
                return false;
        } else if (!authority.equals(other.authority))
            return false;
        if (matchType != other.matchType)
            return false;
        if (method != other.method)
            return false;
        if (pattern == null) {
            if (other.pattern != null)
                return false;
        } else if (!pattern.equals(other.pattern))
            return false;
        if (scope == null) {
            if (other.scope != null)
                return false;
        } else if (!scope.equals(other.scope))
            return false;
        return true;
    }
    @Override
    public String getAttribute() {
        return toString();
    }
}

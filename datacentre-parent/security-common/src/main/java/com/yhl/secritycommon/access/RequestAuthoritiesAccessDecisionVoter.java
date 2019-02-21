package com.yhl.secritycommon.access;

import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.web.FilterInvocation;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 资源服务对客户端器验证token的自定义验证
 * 其实就是一个验证规则，暂时就一个吧，以后照倒这个加
 * */
public class RequestAuthoritiesAccessDecisionVoter implements AccessDecisionVoter<FilterInvocation> {
    @Override
    public boolean supports(ConfigAttribute attribute) {
         return attribute instanceof RequestAuthorityAttribute;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return FilterInvocation.class.isAssignableFrom(clazz);
    }
    /**
     * 如何投票的
     * */
    @Override
    public int vote(Authentication authentication, FilterInvocation object, Collection<ConfigAttribute> attributes) {
        // 如果没有找到相匹配的信息，弃权
        if (CollectionUtils.isEmpty(attributes)) {
            return ACCESS_ABSTAIN;
        }
        List<RequestAuthorityAttribute> rAttributes = attributes.stream()
                .map(attribute -> (RequestAuthorityAttribute) attribute)//authentication数组转为RequestAuthorityAttribute
                .filter(attribute -> !Objects.isNull(attribute.getAccessable()))//过滤袋NUll
                .collect(Collectors.toList());//转换成为list
        int grantCount = 0;
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        for (ConfigAttribute _attribute : rAttributes) {
            RequestAuthorityAttribute attribute = (RequestAuthorityAttribute) _attribute;
            for (GrantedAuthority authority : authorities) {
                if (Objects.equals(authority.getAuthority(), attribute.getAuthority())) {
                    Boolean accessable = attribute.getAccessable();
                    if (!Objects.isNull(accessable)) {
                        if (accessable && validScope(attribute, authentication)) {
                            grantCount++;
                        } else {
                            return ACCESS_DENIED;
                        }
                    }
                }
            }
        }
        // 如果过程中没有产生成功的投票，弃权
        if (grantCount == 0) {
            return ACCESS_ABSTAIN;
        }
        return grantCount > 0 ? ACCESS_GRANTED : ACCESS_DENIED;
    }


    /**
     * 验证Scope
     *
     * @param attribute
     * @param authentication
     * @return
     */
    private boolean validScope(RequestAuthorityAttribute attribute, Authentication authentication) {
        if (authentication instanceof OAuth2Authentication) {
            Set<String> resourceScope = attribute.getScope();
            Set<String> requestScopes = ((OAuth2Authentication) authentication).getOAuth2Request().getScope();
            if (!CollectionUtils.isEmpty(resourceScope)) {
                return CollectionUtils.containsAny(requestScopes, resourceScope);
            }
        }
        return true;
    }
}

package com.yhl.oauth2server.componet.ouathConverter.feature;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.resource.OAuth2AccessDeniedException;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.util.Assert;

import java.util.Collection;
import java.util.Set;


@Setter
public class AuthenticationManagerConverter implements AuthenticationManager {

    private ResourceServerTokenServices tokenServices;

    private ClientDetailsService clientInfoService;

    private String resourceId;

    public void afterPropertiesSet() {
        Assert.state(tokenServices != null, "TokenServices are required");
    }

    /**
     * Expects the incoming authentication request to have a principal value that is an access token value (e.g. from an
     * authorization header). Loads an authentication from the {@link ResourceServerTokenServices} and checks that the
     * resource id is contained in the {link AuthorizationRequest} (if one is specified). Also copies authentication
     * details over from the input to the output (e.g. typically so that the access token value and request details can
     * be reported later).
     * 期望传入的身份验证请求具有访问令牌值的主体值(例如，来自授权头)。
     * 从{@link ResourceServerTokenServices}加载身份验证，
     * 并检查 资源id包含在{link AuthorizationRequest}中(如果指定了一个)。
     * 也认证副本 从输入到输出的详细信息(例如，通常使访问令牌值和请求详细信息可以)稍后报告)。
     *
     * @param authentication an authentication request containing an access token value as the principal
     */
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        if (authentication == null) {
            throw new InvalidTokenException("Invalid token (token not found)");
        }
        String token = (String) authentication.getPrincipal();
        OAuth2Authentication auth = tokenServices.loadAuthentication(token);
        if (auth == null) {
            throw new InvalidTokenException("Invalid token: " + token);
        }

        Collection<String> resourceIds = auth.getOAuth2Request().getResourceIds();
        if (resourceId != null && resourceIds != null && !resourceIds.isEmpty() && !resourceIds.contains(resourceId)) {
            throw new OAuth2AccessDeniedException("Invalid token does not contain resource id (" + resourceId + ")");
        }

        checkClientDetails(auth);

        if (authentication.getDetails() instanceof OAuth2AuthenticationDetails) {
            OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) authentication.getDetails();
            // Guard against a cached copy of the same details
            if (!details.equals(auth.getDetails())) {
                // Preserve the authentication details from the one loaded by token services
                details.setDecodedDetails(auth.getDetails());
            }
        }
        auth.setDetails(authentication.getDetails());
        auth.setAuthenticated(true);
        return auth;

    }

    private void checkClientDetails(OAuth2Authentication auth) {
        if (clientInfoService != null) {
            ClientDetails client;
            try {
                client = clientInfoService.loadClientByClientId(auth.getOAuth2Request().getClientId());
            }
            catch (ClientRegistrationException e) {
                throw new OAuth2AccessDeniedException("Invalid token contains invalid client id");
            }
            Set<String> allowed = client.getScope();
            for (String scope : auth.getOAuth2Request().getScope()) {
                if (!allowed.contains(scope)) {
                    throw new OAuth2AccessDeniedException(
                            "Invalid token contains disallowed scope (" + scope + ") for this client");
                }
            }
        }
    }

}

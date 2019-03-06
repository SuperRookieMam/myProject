package com.yhl.oauthserver.componet.oauthImpl;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.resource.OAuth2AccessDeniedException;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.util.Assert;

import java.util.Collection;
import java.util.Set;

@Getter
@Setter
public class AuthenticationManager implements org.springframework.security.authentication.AuthenticationManager , InitializingBean {

    private ResourceServerTokenServices tokenServices;
    @Autowired
    private ClientDetailsService clientDetailsService;

    private String resourceId;

    public AuthenticationManager(){
        tokenServices =new ResourceServerTokenServiciys();
    }
    public AuthenticationManager(ResourceServerTokenServices tokenServices,ClientDetailsService clientDetailsService,String resourceId){
            this.tokenServices =tokenServices;
            this.clientDetailsService =clientDetailsService;
            this.resourceId =resourceId;
    }


    @Override
    public void afterPropertiesSet() {
        Assert.state(tokenServices != null, "TokenServices are required");
    }
    /**
     * 自定义的
     * 如果帐户被禁用，则必须抛出DisabledException
     * 如果帐户被锁定，则必须抛出LockedException}
     *如果凭证不正确，必须抛出 BadCredentialsException}
     * 如果身份验证失败，则抛出AuthenticationException
     * */
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
        if (clientDetailsService != null) {
            ClientDetails client;
            try {
                client = clientDetailsService.loadClientByClientId(auth.getOAuth2Request().getClientId());
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

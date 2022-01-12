package com.algaworks.algafood.core.security.authorizationserver;

import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import java.util.HashMap;
import java.util.Map;

public class JwtCustomClaimsTokenEnhancer implements TokenEnhancer {

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        if (!(authentication.getPrincipal() instanceof AuthUser)) {
            return accessToken;
        }

        AuthUser authUser = (AuthUser) authentication.getPrincipal();

        Map<String, Object> info = new HashMap<>();
        info.put("nome_completo", authUser.getFullName());
        info.put("usuario_id", authUser.getUserId());

        DefaultOAuth2AccessToken defaultAccessToken = (DefaultOAuth2AccessToken) accessToken;
        defaultAccessToken.setAdditionalInformation(info);

        return accessToken;
    }

}

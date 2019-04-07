package com.github.kubenext.uaa.security;

import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author shangjin.li
 */
@Component
public class IatTokenEnhancer implements TokenEnhancer {
    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        addClaims((DefaultOAuth2AccessToken) accessToken);
        return accessToken;
    }

    private void addClaims(DefaultOAuth2AccessToken accessToken) {
        Map<String, Object> additionalInfomation = accessToken.getAdditionalInformation();
        if (additionalInfomation.isEmpty()) {
            additionalInfomation = new LinkedHashMap<>();
        }
        additionalInfomation.put("iat", Instant.now().getEpochSecond());
        accessToken.setAdditionalInformation(additionalInfomation);
    }

}

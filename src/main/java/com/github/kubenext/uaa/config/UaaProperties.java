package com.github.kubenext.uaa.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author shangjin.li
 */
@Component
@ConfigurationProperties(prefix = "uaa", ignoreUnknownFields = false)
public class UaaProperties {

    private final KeyStore keyStore = new KeyStore();

    private final ClientConfiguration client = new ClientConfiguration();

    public KeyStore getKeyStore() {
        return keyStore;
    }

    public ClientConfiguration getClient() {
        return client;
    }

    public static class KeyStore {
        private String name = "config/tls/keystore.p12";
        private String password = "password";
        private String alias = "selfsigned";

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getAlias() {
            return alias;
        }

        public void setAlias(String alias) {
            this.alias = alias;
        }
    }

    public static class ClientConfiguration {
        private int accessTokenValidityInSeconds = 5 * 60;
        private int refreshTokenValidityInSecondsForRememberMe = 7 * 24 * 60 * 60;
        private String clientId = "web_app";
        private String secret = "changeit";

        public int getAccessTokenValidityInSeconds() {
            return accessTokenValidityInSeconds;
        }

        public void setAccessTokenValidityInSeconds(int accessTokenValidityInSeconds) {
            this.accessTokenValidityInSeconds = accessTokenValidityInSeconds;
        }

        public int getRefreshTokenValidityInSecondsForRememberMe() {
            return refreshTokenValidityInSecondsForRememberMe;
        }

        public void setRefreshTokenValidityInSecondsForRememberMe(int refreshTokenValidityInSecondsForRememberMe) {
            this.refreshTokenValidityInSecondsForRememberMe = refreshTokenValidityInSecondsForRememberMe;
        }

        public String getClientId() {
            return clientId;
        }

        public void setClientId(String clientId) {
            this.clientId = clientId;
        }

        public String getSecret() {
            return secret;
        }

        public void setSecret(String secret) {
            this.secret = secret;
        }
    }

}

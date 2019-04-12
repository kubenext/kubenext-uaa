package com.github.kubenext.uaa.domain;

import com.vladmihalcea.hibernate.type.json.JsonStringType;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * @author shangjin.li
 */
@TypeDef(name = "json", typeClass = JsonStringType.class)
@Entity
public class Client extends AbstractAuditingEntity{

    /**
     * 名称
     */
    @Id
    @Column(length = 50)
    private String name;

    /**
     * 简介
     */
    private String summary;

    /**
     * 图标
     */
    private String icon;

    /**
     * 内部
     */
    @Column(nullable = false)
    private boolean internal;

    /**
     * 激活状态
     */
    @Column(nullable = false)
    private boolean activated;

    /**
     * Client ID
     */
    @Column(length = 60)
    private String clientId;

    /**
     * Client Secret
     */
    @Column(length = 60)
    private String clientSecret;

    /**
     * 用户自动授权
     */
    @Column(nullable = false)
    private boolean autoApprove;

    /**
     * 授权范围,逗号分割
     */
    @Type( type = "json" )
    @Column( columnDefinition = "json" )
    private String[] scope;

    /**
     * 可访问的资源ID
     */
    @OneToMany(fetch = FetchType.EAGER)
    private Set<Client> resourceIds = Collections.emptySet();

    /**
     * 可授权的方式，逗号分割
     */
    @Type( type = "json" )
    @Column( columnDefinition = "json" )
    private String[] authorizedGrantTypes;

    /**
     * 授权回掉的URI,逗号分割
     */
    @Type( type = "json" )
    @Column( columnDefinition = "json" )
    private String[] registeredRedirectUris;

    /**
     * 令牌有效时长
     */
    @Column(nullable = false)
    private Integer accessTokenValiditySeconds;

    @Type( type = "json" )
    @Column( columnDefinition = "json" )
    private String[] authorities;

    /**
     * 刷新令牌有效时长
     */
    @Column(nullable = false)
    private Integer refreshTokenValiditySeconds;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public boolean isInternal() {
        return internal;
    }

    public void setInternal(boolean internal) {
        this.internal = internal;
    }

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public boolean isAutoApprove() {
        return autoApprove;
    }

    public void setAutoApprove(boolean autoApprove) {
        this.autoApprove = autoApprove;
    }

    public String[] getScope() {
        return scope;
    }

    public void setScope(String[] scope) {
        this.scope = scope;
    }

    public Set<Client> getResourceIds() {
        return resourceIds;
    }

    public void setResourceIds(Set<Client> resourceIds) {
        this.resourceIds = resourceIds;
    }

    public String[] getAuthorizedGrantTypes() {
        return authorizedGrantTypes;
    }

    public void setAuthorizedGrantTypes(String[] authorizedGrantTypes) {
        this.authorizedGrantTypes = authorizedGrantTypes;
    }

    public String[] getRegisteredRedirectUris() {
        return registeredRedirectUris;
    }

    public void setRegisteredRedirectUris(String[] registeredRedirectUris) {
        this.registeredRedirectUris = registeredRedirectUris;
    }

    public Integer getAccessTokenValiditySeconds() {
        return accessTokenValiditySeconds;
    }

    public void setAccessTokenValiditySeconds(Integer accessTokenValiditySeconds) {
        this.accessTokenValiditySeconds = accessTokenValiditySeconds;
    }

    public String[] getAuthorities() {
        return authorities;
    }

    public void setAuthorities(String[] authorities) {
        this.authorities = authorities;
    }

    public Integer getRefreshTokenValiditySeconds() {
        return refreshTokenValiditySeconds;
    }

    public void setRefreshTokenValiditySeconds(Integer refreshTokenValiditySeconds) {
        this.refreshTokenValiditySeconds = refreshTokenValiditySeconds;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Client client = (Client) o;
        return name.equals(client.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}

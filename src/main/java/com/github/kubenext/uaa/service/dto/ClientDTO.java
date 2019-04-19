package com.github.kubenext.uaa.service.dto;

import com.github.kubenext.uaa.domain.Client;

import javax.validation.constraints.NotBlank;
import java.util.Collections;
import java.util.Set;

/**
 * @author shangjin.li
 */
public class ClientDTO {

    /**
     * 名称
     */
    @NotBlank
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
    private boolean internal;

    /**
     * 激活状态
     */
    private boolean activated;

    /**
     * Client ID
     */
    private String clientId;

    /**
     * 用户自动授权
     */
    private boolean autoApprove;

    /**
     * 授权范围,逗号分割
     */
    private String[] scope;

    /**
     * 可访问的资源ID
     */
    private Set<Client> resourceIds = Collections.emptySet();

    /**
     * 可授权的方式，逗号分割
     */
    private String[] authorizedGrantTypes;

    /**
     * 授权回掉的URI,逗号分割
     */
    private String[] registeredRedirectUris;

    /**
     * 令牌有效时长
     */
    private Integer accessTokenValiditySeconds;

    private String[] authorities;

    /**
     * 刷新令牌有效时长
     */
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



}

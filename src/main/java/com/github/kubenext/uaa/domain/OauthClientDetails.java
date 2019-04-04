package com.github.kubenext.uaa.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Oauth2 客户端
 * @author lishangjin
 */
@Entity
public class OauthClientDetails {

    /**
     * 客服端ID
     */
    @Id
    private String clientId;

    /**
     * 客户端密钥
     */
    private String clientSecret;

    /**
     * 授权可访问的资源ID
     */
    private String resourceIds;

    /**
     * 授权范围
     */
    private String scope;

    /**
     * 授权方式
     */
    private String authorizedGrantTypes;

    /**
     * 客户端回调地址
     */
    private String webServerRedirectUri;

    /**
     * 系统权限
     */
    private String authorities;

    /**
     * 附加信息
     */
    private String additionalInformation;

    /**
     * 登录后是否自动绕过批准询问
     */
    private Boolean autoapprove;

    /**
     * 认证令牌有效时长
     */
    private Integer accessTokenValidity;

    /**
     * 刷新令牌有效时长
     */
    private Integer refreshTokenValidity;

    /**
     * 客户端标签，用于对客户端分类
     */
    private String label;

}

package com.github.kubenext.uaa.service.dto;

import com.github.kubenext.uaa.config.Constants;
import com.github.kubenext.uaa.domain.Authority;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.Instant;
import java.util.Arrays;
import java.util.Set;

/**
 * @author shangjin.li
 */
@ApiModel("用户")
public class UserDTO {

    @ApiModelProperty("用户ID，创建时请不要携带此参数")
    private Long id;

    @ApiModelProperty(value = "用户登录帐号，帐号规则`^[_.@A-Za-z0-9-]*$`", required = true)
    @NotBlank
    @Pattern(regexp = Constants.LOGIN_REGEX)
    @Size(min = 1, max = 50)
    private String login;

    @ApiModelProperty(value = "用户姓氏", required = true)
    @Size(min = 1, max = 50)
    private String firstName;

    @ApiModelProperty(value = "用户名字", required = true)
    @Size(min = 1, max = 50)
    private String lastName;

    @ApiModelProperty(value = "电子邮件", required = true)
    @Email
    @Size(min = 5, max = 254)
    private String email;

    @ApiModelProperty("头像地址")
    @Size(max = 256)
    private String imageUrl;

    @ApiModelProperty("激活状态")
    private Boolean activated;

    @ApiModelProperty("语种")
    @Size(min = 2, max = 6)
    private String langKey;

    @ApiModelProperty("创建者，由系统自动维护")
    private String createdBy;

    @ApiModelProperty("创建时间，由系统自动维护")
    private Instant createdDate;

    @ApiModelProperty("最后修改者，由系统自动维护")
    private String lastModifiedBy;

    @ApiModelProperty("最后修改日期，由系统自动维护")
    private Instant lastModifiedDate;

    @ApiModelProperty("用户角色")
    private String[] authorities;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Boolean getActivated() {
        return activated;
    }

    public void setActivated(Boolean activated) {
        this.activated = activated;
    }

    public String getLangKey() {
        return langKey;
    }

    public void setLangKey(String langKey) {
        this.langKey = langKey;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Instant getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public String[] getAuthorities() {
        return authorities;
    }

    public void setAuthorities(String[] authorities) {
        this.authorities = authorities;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", activated=" + activated +
                ", langKey='" + langKey + '\'' +
                ", createdBy='" + createdBy + '\'' +
                ", createdDate=" + createdDate +
                ", lastModifiedBy='" + lastModifiedBy + '\'' +
                ", lastModifiedDate=" + lastModifiedDate +
                ", authorities=" + Arrays.toString(authorities) +
                '}';
    }
}

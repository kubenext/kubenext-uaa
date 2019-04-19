package com.github.kubenext.uaa.service.dto;

import com.github.kubenext.uaa.config.Constants;
import com.github.kubenext.uaa.validation.constraints.AuthorityExist;
import com.github.kubenext.uaa.validation.constraints.LoginDuplicate;
import com.github.kubenext.uaa.validation.constraints.UserIdExist;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Arrays;

/**
 * @author shangjin.li
 */
public class UpdateUserDTO {

    @ApiModelProperty(value = "用户ID", required = true)
    @UserIdExist
    private Long id;

    @ApiModelProperty(value = "登录帐号")
    @Pattern(regexp = Constants.LOGIN_REGEX)
    @Length(min = 6, max = 32)
    @LoginDuplicate
    private String login;

    @ApiModelProperty(value = "电子邮件")
    @Email
    @Size(min = 5, max = 254)
    private String email;

    @ApiModelProperty(value = "名字")
    @Length(max = 50)
    private String firstName;

    @ApiModelProperty(value = "姓氏")
    @Length(max = 50)
    private String lastName;

    @ApiModelProperty(value = "头像")
    @Length(max = 256)
    private String imageUrl;

    @ApiModelProperty("激活状态")
    private Boolean activated;

    @ApiModelProperty(value = "语种")
    @Length(min = 2, max = 6)
    private String langKey;

    @ApiModelProperty(value = "用户角色")
    @AuthorityExist
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String[] getAuthorities() {
        return authorities;
    }

    public void setAuthorities(String[] authorities) {
        this.authorities = authorities;
    }

    @Override
    public String toString() {
        return "UpdateUserDTO{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", activated=" + activated +
                ", langKey='" + langKey + '\'' +
                ", authorities=" + Arrays.toString(authorities) +
                '}';
    }
}

package com.github.kubenext.uaa.service.dto;

import com.github.kubenext.uaa.config.Constants;
import com.github.kubenext.uaa.validation.constraints.AuthorityMustExist;
import com.github.kubenext.uaa.validation.constraints.EmailMustNotExist;
import com.github.kubenext.uaa.validation.constraints.LoginMustNotExist;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Arrays;

/**
 * 新建用户
 *
 * @author shangjin.li
 */
@ApiModel("新建用户")
public class CreateUserDTO {

    @ApiModelProperty(value = "登录帐号", required = true)
    @NotBlank
    @Pattern(regexp = Constants.LOGIN_REGEX)
    @Length(min = 6, max = 32)
    @LoginMustNotExist
    private String login;

    @ApiModelProperty(value = "用户角色", required = true)
    @AuthorityMustExist
    private String[] authorities;

    @ApiModelProperty(value = "电子邮箱")
    @Email
    @EmailMustNotExist
    @Length(min = 5, max = 254)
    private String email;

    @ApiModelProperty(value = "名字")
    @Length(max = 50)
    private String firstName;

    @ApiModelProperty(value = "姓氏")
    @Length(max = 50)
    private String lastName;

    @ApiModelProperty(value = "语种")
    @Length(min = 2, max = 6)
    private String langKey;

    @ApiModelProperty(value = "头像")
    @Length(max = 256)
    private String avatar;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String[] getAuthorities() {
        return authorities;
    }

    public void setAuthorities(String[] authorities) {
        this.authorities = authorities;
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

    public String getLangKey() {
        return langKey;
    }

    public void setLangKey(String langKey) {
        this.langKey = langKey;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Override
    public String toString() {
        return "CreateUserDTO{" +
                "login='" + login + '\'' +
                ", authorities=" + Arrays.toString(authorities) +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", langKey='" + langKey + '\'' +
                ", avatar='" + avatar + '\'' +
                '}';
    }
}

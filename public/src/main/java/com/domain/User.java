package com.domain;

import com.domain.Feature;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author 今昔
 * @description 用户实体类
 * @date 2022/11/8 20:29
 */
@ApiModel(value = "用户类")
//@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS,include = JsonTypeInfo.As.PROPERTY,property = "@class")
public class User {
    @ApiModelProperty(notes = "用户id")
    private int id;
    @ApiModelProperty(notes = "用户昵称")
    private String name;
    @ApiModelProperty(notes = "用户密码")
    private String password;
    @ApiModelProperty(notes = "用户头像")
    private String avatar;
    @ApiModelProperty(notes = "用户性别")
    private String sex;
    @ApiModelProperty(notes = "用户邮箱")
    private String email;
    @ApiModelProperty(notes = "用户个人介绍")
    private String introduction;
//    @ApiModelProperty(notes = "用户关注列表")
//    private List<UserVo> followList;
    @ApiModelProperty(notes = "用户生日")
    private long birthday;
//    @ApiModelProperty(notes = "用户的文章列表")
//    private List<Article> articleList;
    @ApiModelProperty(notes = "用户的空间图片")
    private String spacePicture;
//    @ApiModelProperty(notes = "用户收藏列表")
//    private List<Article> collection;
    @ApiModelProperty(notes = "用户身份，1为用户，0为管理员")
    private int identity;
    private List<Feature> features;

    public User() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }


    public long getBirthday() {
        return birthday;
    }

    public void setBirthday(long birthday) {
        this.birthday = birthday;
    }


    public String getSpacePicture() {
        return spacePicture;
    }

    public void setSpacePicture(String spacePicture) {
        this.spacePicture = spacePicture;
    }



    public int getIdentity() {
        return identity;
    }

    public void setIdentity(int identity) {
        this.identity = identity;
    }

    public List<Feature> getFeatures() {
        return features;
    }

    public void setFeatures(List<Feature> features) {
        this.features = features;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", avatar='" + avatar + '\'' +
                ", sex='" + sex + '\'' +
                ", email='" + email + '\'' +
                ", introduction='" + introduction + '\'' +
                ", birthday=" + birthday +
                ", spacePicture='" + spacePicture + '\'' +
                ", identity=" + identity +
                ", features=" + features +
                '}';
    }
}

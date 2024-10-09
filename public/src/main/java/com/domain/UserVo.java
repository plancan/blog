package com.domain;

/**
 * @author 今昔
 * @description 登陆成功返回用户信息
 * @date 2022/11/12 15:35
 */
public class UserVo {
    private int id;
    private String name;
    private int identity;
    private String token;

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

    public int getIdentity() {
        return identity;
    }

    public void setIdentity(int identity) {
        this.identity = identity;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "UserVo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", identity=" + identity +
                ", token='" + token + '\'' +
                '}';
    }
}

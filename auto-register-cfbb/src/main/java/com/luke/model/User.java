package com.luke.model;

/**
 * Created by yangf on 2017/12/13/0013.
 */
public class User {
    private String name;
    private String password;
    private String phone;
    private String authCode;

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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    @Override
    public String toString() {
        return "User{" + "name='" + name + '\'' + ", password='" + password + '\'' + ", phone='" + phone + '\'' + ", authCode='" + authCode + '\'' + '}';
    }
}

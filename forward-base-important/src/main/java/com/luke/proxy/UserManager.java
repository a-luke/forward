package com.luke.proxy;

/**
 * Created by yangf on 2016/7/20.
 */
public interface UserManager {
    public void addUser(String id, String password);
    public void delUser(String id);
}
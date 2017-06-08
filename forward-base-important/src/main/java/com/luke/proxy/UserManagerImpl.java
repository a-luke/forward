package com.luke.proxy;

/**
 * Created by yangf on 2016/7/20.
 */
public class UserManagerImpl implements UserManager{
    @Override
    public void addUser(String id, String password) {
        System.out.println("I'm UserManagerImpl.addUser()");
    }

    @Override
    public void delUser(String id) {
        System.out.println("I'm UserManagerImpl.delUser()");
    }
}

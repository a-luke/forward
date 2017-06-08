package com.luke.proxy;

/**
 * Created by yangf on 2016/7/20.
 */
public class TestMain {
    public static void main(String[] args) {


        UserManager um = (UserManager) new JDKProxy().newProxy(new UserManagerImpl());
//        um.addUser("123", "123");
        um.delUser("123");
    }
}

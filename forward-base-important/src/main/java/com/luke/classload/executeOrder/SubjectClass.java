package com.luke.classload.executeOrder;

/**
 * Created by yangf on 2017/5/25.
 */
public class SubjectClass {
    public final static String  STR = "412345";

    {
        System.out.println("代码块！");
    }

    static {
        System.out.println("静态代码块！");
    }

    public SubjectClass(){
        System.out.println("构造方法！");
    }

    public static void staticMethod(){
        System.out.println("静态方法！");
    }


}

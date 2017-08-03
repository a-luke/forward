package com.luke.enums;

/**
 * Created by yangf on 2017/8/3/0003.
 */
public enum KeyWordType {
    PUBLIC("public"), PRIVATE("private"), PROTECTED("protected"), DEFAULT("!@*&"), INTERFACE("interface"), CLASS("class"), ENUM("enum"), ABSTRACT("abstract"),
    IMPORT("import"), PACKAGE("package"), STATIC("static"), FINAL("final"), VOLATILE("volatile"), VOID("void"), WD("!@*&");

    private String value;

    KeyWordType(String value) {
        this.value = value;
    }

    public static KeyWordType has(String value) {
        KeyWordType[] keyWordTypes = KeyWordType.values();
        for (KeyWordType keyWordType : keyWordTypes) {
            if (keyWordType.value.equals(value)) {
                return keyWordType;
            }
        }
        return WD;
    }
    
    public static boolean isClass(KeyWordType kt){
        return kt == KeyWordType.CLASS || kt == KeyWordType.ENUM || kt == KeyWordType.INTERFACE;
    }

    public static boolean isAccess(KeyWordType kt){
        return kt == KeyWordType.PUBLIC || kt == KeyWordType.PRIVATE || kt == KeyWordType.PROTECTED || kt == KeyWordType.DEFAULT;
    }

    public static boolean isModifier(KeyWordType kt){
        return kt == KeyWordType.STATIC || kt == KeyWordType.FINAL || kt == KeyWordType.VOLATILE || kt == KeyWordType.ABSTRACT;
    }
}

package com.luke.enums;

/**
 * Created by yangf on 2017/8/1/0001.
 */
public enum AccessType {
    PUBLIC("public"),
    PRIVATE("private"),
    PROTECTED("protected"),
    DEFAULT("");

    private String value;

    AccessType(String value){
        this.value = value;
    }

    public static AccessType has(String value) {
        AccessType[] accessTypes = AccessType.values();
        for (AccessType accessType : accessTypes) {
            if (accessType.value.equals(value)) {
                return accessType;
            }
        }
        return null;
    }
}

package com.luke.enums;

/**
 * Created by yangf on 2017/8/1/0001.
 */
public enum ClassType {
    INTERFACE("interface"), CLASS("class"), ENUM("enum"), ABSTRACT("abstract"), IMPORT("import"), PACKAGE("package");

    private String value;

    ClassType(String value) {
        this.value = value;
    }

    public static ClassType has(String value) {
        ClassType[] classTypes = ClassType.values();
        for (ClassType classType : classTypes) {
            if (classType.value.equals(value)) {
                return classType;
            }
        }
        return null;
    }
}

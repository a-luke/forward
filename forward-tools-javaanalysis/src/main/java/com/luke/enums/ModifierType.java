package com.luke.enums;

/**
 * Created by yangf on 2017/8/1/0001.
 */
public enum ModifierType {
    STATIC("static"),
    FINAL("final"),
    VOLATILE("volatile"),
    VOID("void");

    private String value;

    ModifierType(String value){
        this.value = value;
    }


    public static ModifierType has(String value) {
        ModifierType[] modifierTypes = ModifierType.values();
        for (ModifierType modifierType : modifierTypes) {
            if (modifierType.value.equals(value)) {
                return modifierType;
            }
        }
        return null;
    }
}

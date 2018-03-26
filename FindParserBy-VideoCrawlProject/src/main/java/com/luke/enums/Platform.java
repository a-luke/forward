package com.luke.enums;

public enum Platform {
    ANDROID("android"), IOS("ios"), MOBILE("mobile"), OTT("ott"), OTV("otv"), PAD("pad"), PC("pc"), UNKNOW("不清楚");

    private String value;

    Platform(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public Platform getType(String value) {
        for (Platform platform : Platform.values()) {
            if (platform.value.equals(value)) {
                return platform;
            }
        }
        return PC;
    }
}

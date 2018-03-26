package com.luke.model;

import com.luke.enums.Platform;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ClassInfo {
    private String className;

    private List<String> strList;

    private List<WordModel> wordModels;

    private Platform platform;

    /**
     * 1：搜索 2: 解析 3: 其他
     */
    private int type;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public List<String> getStrList() {
        return strList;
    }

    public void setStrList(List<String> strList) {
        this.strList = strList;
    }


    public void addStrList(String str) {
        if (this.strList == null) {
            this.strList = new ArrayList<>();
        }
        this.strList.add(str);
    }

    public List<WordModel> getWordModels() {
        return wordModels;
    }

    public void setWordModels(List<WordModel> wordModels) {
        this.wordModels = wordModels;
    }

    public Platform getPlatform() {
        if (this.className.endsWith("Android")) {
            platform = Platform.ANDROID;
        } else if (this.className.endsWith("IOS")) {
            platform = Platform.IOS;
        } else if (this.className.endsWith("Mobile")) {
            platform = Platform.MOBILE;
        } else if (this.className.endsWith("OTT")) {
            platform = Platform.OTT;
        } else if (this.className.endsWith("OTV")) {
            platform = Platform.OTV;
        } else if (this.className.endsWith("Pad") || this.className.endsWith("pad")) {
            platform = Platform.PAD;
        } else if (this.className.endsWith("PC")) {
            platform = Platform.PC;
        } else {
            platform = Platform.UNKNOW;
        }
        return platform;
    }

    public void setPlatform(Platform platform) {
        this.platform = platform;
    }

    public int getType() {
        if (this.className.startsWith("Analy") || this.className.startsWith("Anayly")) {
            type = 2;
        } else if (this.className.startsWith("Search") || this.className.startsWith("Get")) {
            type = 1;
        } else {
            type = 3;
        }

        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClassInfo classInfo = (ClassInfo) o;
        return Objects.equals(className, classInfo.className);
    }

    @Override
    public int hashCode() {

        return Objects.hash(className);
    }
}

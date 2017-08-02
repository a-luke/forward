package com.luke.model;

import com.luke.enums.GSType;

public class WordModel {

    /**
     * 单词的类型
     */
    private Object wdType;

    /**
     * 类型
     */
    private GSType type;

    /**
     * 单词内容
     */
    private String word;

    public Object getWdType() {
        return wdType;
    }

    public void setWdType(Object wdType) {
        this.wdType = wdType;
    }

    public GSType getType() {
        return type;
    }

    public WordModel setType(GSType type) {
        this.type = type;
        return this;
    }

    public String getWord() {
        return word;
    }

    public WordModel setWord(String word) {
        this.word = word;
        return this;
    }

    @Override
    public String toString() {
        return "WordModel{" + "wdType=" + wdType + ", type=" + type + ", word='" + word + '\'' + '}';
    }
}

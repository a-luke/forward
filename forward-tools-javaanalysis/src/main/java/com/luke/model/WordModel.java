package com.luke.model;

import com.luke.enums.GSType;
import com.luke.enums.KeyWordType;

public class WordModel {

    /**
     * 单词的类型
     */
    private KeyWordType wdType;

    /**
     * 类型
     */
    private GSType type;

    /**
     * 单词内容
     */
    private String word;

    public KeyWordType getWdType() {
        return wdType;
    }

    public WordModel setWdType(KeyWordType wdType) {
        this.wdType = wdType;
        return this;
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

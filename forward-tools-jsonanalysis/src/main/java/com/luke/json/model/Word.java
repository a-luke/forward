package com.luke.json.model;

/**
 * Created by yangf on 2017/3/31.
 */
public class Word {

    private WordType type;
    private String value;

    public Word(WordType type, String value){
        this.type = type;
        this.value = value;
    }

    public WordType getType() {
        return type;
    }

    public void setType(WordType type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Word{" + "type=" + type + ", value='" + value + '\'' + '}';
    }
}

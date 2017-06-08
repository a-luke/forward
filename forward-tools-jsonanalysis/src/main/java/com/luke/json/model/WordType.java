package com.luke.json.model;

/**
 * Created by yangf on 2017/3/30.
 */
public enum WordType {
    LZK('['),
    RZK(']'),
    LDK('{'),
    RDK('}'),
    DY('\''),
    SY('"'),
    MH(':'),
    DH(','),
    STR(' '),
    NUM(' ');

    private char c;

    WordType(char c){
        this.c = c;
    }

    public static WordType getType(char c){
        for (WordType type : WordType.values()) {
            if (type.c == c) {
                return type;
            }
        }
        return null;
    }

    public boolean equals(String c){
        return String.valueOf(this.c).equals(c);
    }
}

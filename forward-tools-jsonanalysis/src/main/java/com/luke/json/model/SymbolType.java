package com.luke.json.model;

/**
 * Created by yangf on 2017/3/31.
 */
public enum  SymbolType {
    LZK('['),
    RZK(']'),
    LDK('{'),
    RDK('}'),
    DY('\''),
    SY('"'),
    MH(':'),
    DH(',');

    private char c;

    SymbolType(char c){
        this.c = c;
    }

    public boolean equal(char c){
        return this.c == c;
    }
}

package com.luke.json.model;

import java.util.HashMap;

/**
 * Created by yangf on 2017/3/30.
 */
public class JsonObject extends HashMap {
    private Node node;

    private String value;

    public JsonObject(){

    }

    public JsonObject(Node node){
        value = node.getStrValue();
        this.node = node;
    }

    public JsonObject  get(String name){
        for(Node sub : node.getSubs()){
            if(name.equals(sub.getKey())){
                value = sub.getStrValue();
                node = sub;
                break;
            }
        }
        return this;
    }

    public String getValue(){
        return value;
    }

}

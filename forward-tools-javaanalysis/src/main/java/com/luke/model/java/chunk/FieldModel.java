package com.luke.model.java.chunk;

import com.luke.model.java.BaseModel;

/**
 * Created by yangf on 2017/8/1/0001.
 */
public class FieldModel extends BaseModel {
    private boolean isEnum;

    public boolean isEnum() {
        return isEnum;
    }

    public void setEnum(boolean anEnum) {
        isEnum = anEnum;
    }
}

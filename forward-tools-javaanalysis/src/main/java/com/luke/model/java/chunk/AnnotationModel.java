package com.luke.model.java.chunk;

import com.luke.model.java.CounterModel;

/**
 * Created by yangf on 2017/8/1/0001.
 * 注解信息
 */
public class AnnotationModel extends CounterModel {
    /**
     * 注解的内容
     */
    private String content;



    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


}

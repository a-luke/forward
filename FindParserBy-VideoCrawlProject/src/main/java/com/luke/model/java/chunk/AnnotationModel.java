package com.luke.model.java.chunk;

/**
 * Created by yangf on 2017/8/1/0001.
 * 注解信息
 */
public class AnnotationModel {
    public AnnotationModel(String content) {
        this.content = content;
    }

    /**
     * 注解的内容
     */
    private String content;

    public String getContent() {
        return content;
    }

    public AnnotationModel setContent(String content) {
        this.content = content;
        return this;
    }
}

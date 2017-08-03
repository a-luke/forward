package com.luke.model.java.chunk;

/**
 * Created by yangf on 2017/8/1/0001.
 * 注释信息
 */
public class Note {
    /**
     * 原始内容
     */
    private String content;

    /**
     * 格式化之后的内容
     */
    private String formatContent;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFormatContent() {
        return formatContent;
    }

    public void setFormatContent(String formatContent) {
        this.formatContent = formatContent;
    }
}

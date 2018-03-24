package com.luke.model.java.chunk;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yangf on 2017/8/1/0001.
 * 注释信息
 */
public class Note {
    /**
     * 原始内容
     */
    private List<String> content;

    /**
     * 格式化之后的内容
     */
    private String formatContent;

    public List<String> getContent() {
        if (content == null) {
            content = new ArrayList<>();
        }
        return content;
    }

    public void setContent(List<String> content) {
        this.content = content;
    }

    public Note addContent(String content) {
        getContent().add(content);
        return this;
    }

    public String getFormatContent() {
        return formatContent;
    }

    public void setFormatContent(String formatContent) {
        this.formatContent = formatContent;
    }

    public boolean isEmpty() {
        return getContent().isEmpty();
    }

    public void clear(){
        this.content.clear();
    }

    public void addAll(Note note){
        getContent().addAll(note.getContent());
        formatContent = note.formatContent;
    }
}

package com.luke.model;

/**
 * Created by yangf on 2017/8/3/0003.
 */
public class FileModel {
    private String path;
    private String name;
    private StringBuilder content;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public StringBuilder getContent() {
        if (content == null) {
            content = new StringBuilder();
        }
        return content;
    }

    public void setContent(StringBuilder content) {
        this.content = content;
    }

    public FileModel appendContent(String content) {
        getContent().append(content);
        return this;
    }


}

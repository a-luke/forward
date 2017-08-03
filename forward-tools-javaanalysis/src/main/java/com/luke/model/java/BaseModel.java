package com.luke.model.java;

import com.luke.enums.KeyWordType;
import com.luke.model.java.chunk.AnnotationModel;
import com.luke.model.java.chunk.ClassModel;
import com.luke.model.java.chunk.Note;

import java.util.ArrayList;
import java.util.List;

public class BaseModel extends CounterModel {
    /**
     * 当前块的上一层
     */
    private ClassModel prev;

    /**
     * 名称
     */
    private String name;

    /**
     * 定义的内容
     */
    private String header;

    /**
     * 内容
     */
    private StringBuilder content;

    /**
     * 注解
     */
    private List<AnnotationModel> annotationModels;

    /**
     * 注释
     */
    private Note note;
    /**
     * 访问类型
     */
    private KeyWordType accessType;

    /**
     * 修饰词
     */
    private List<KeyWordType> modifierTypes;

    /**
     * 是否是静态的
     */
    private boolean isStatic;

    public ClassModel getPrev() {
        return prev;
    }

    public BaseModel setPrev(ClassModel prev) {
        this.prev = prev;
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
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

    public BaseModel appendContent(String content) {
        getContent().append(content);
        return this;
    }

    public List<AnnotationModel> getAnnotationModels() {
        if(annotationModels == null){
            annotationModels = new ArrayList<>();
        }
        return annotationModels;
    }

    public void setAnnotationModels(List<AnnotationModel> annotationModels) {
        this.annotationModels = annotationModels;
    }

    public BaseModel addAnnotationModels(AnnotationModel annotationModel) {
        getAnnotationModels().add(annotationModel);
        return this;
    }

    public Note getNote() {
        return note;
    }

    public void setNote(Note note) {
        this.note = note;
    }

    public KeyWordType getAccessType() {
        if(accessType == null){
            accessType = KeyWordType.DEFAULT;
        }
        return accessType;
    }

    public void setAccessType(KeyWordType accessType) {
        this.accessType = accessType;
    }

    public List<KeyWordType> getModifierTypes() {
        if(modifierTypes == null){
            modifierTypes = new ArrayList<>();
        }
        return modifierTypes;
    }

    public void setModifierTypes(List<KeyWordType> modifierTypes) {
        this.modifierTypes = modifierTypes;
    }

    public BaseModel addModifierTypes(KeyWordType modifierType) {
        getModifierTypes().add(modifierType);
        return this;
    }

    public boolean isStatic() {
        return isStatic;
    }

    public void setStatic(boolean aStatic) {
        isStatic = aStatic;
    }
}

package com.luke.model;

import com.luke.enums.AccessType;
import com.luke.enums.ModifierType;

import java.util.List;

/**
 * Created by yangf on 2017/8/1/0001.
 */
public class MethodModel extends CounterModel{

    /**
     * 方法名
     */
    private  String name;

    /**
     * 方法的定义内容
     */
    private  String content;

    /**
     * 注解
     */
    private List<AnnotationModel> annotation;

    /**
     * 注释
     */
    private Note note;

    /**
     * 访问类型
     */
    private AccessType accessType;

    /**
     * 修饰词
     */
    private ModifierType modifierType;

    public List<AnnotationModel> getAnnotation() {
        return annotation;
    }

    public void setAnnotation(List<AnnotationModel> annotation) {
        this.annotation = annotation;
    }

    public Note getNote() {
        return note;
    }

    public void setNote(Note note) {
        this.note = note;
    }

    public AccessType getAccessType() {
        return accessType;
    }

    public void setAccessType(AccessType accessType) {
        this.accessType = accessType;
    }

    public ModifierType getModifierType() {
        return modifierType;
    }

    public void setModifierType(ModifierType modifierType) {
        this.modifierType = modifierType;
    }
}

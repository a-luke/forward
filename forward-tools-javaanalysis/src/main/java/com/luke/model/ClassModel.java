package com.luke.model;

import com.luke.enums.AccessType;
import com.luke.enums.ClassType;
import com.luke.enums.ModifierType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yangf on 2017/8/1/0001.
 */
public class ClassModel extends CounterModel {

    /**
     * 包的路径
     */
    private String packageStr;

    /**
     * 类名
     */
    private  String name;

    /**
     * 类的定义内容
     */
    private  String content;

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
    private AccessType accessType;

    /**
     * 类的类型，是接口还是抽象
     */
    private ClassType classType;

    /**
     * 是否是静态的
     */
    private boolean isStatic;

    /**
     * 修饰词
     */
    private List<ModifierType> modifierTypes;

    /**
     * 依赖包名
     */
    private List<String> dependNames;

    /**
     * 所有的字段列表
     */
    private List<FieldModel> fieldModels;

    /**
     * 所有的方法列表
     */
    private List<MethodModel> methodModels;

    /**
     * 所有的内部类列表
     */
    private List<ClassModel> classModels;

    public String getPackageStr() {
        return packageStr;
    }

    public void setPackageStr(String packageStr) {
        this.packageStr = packageStr;
    }

    public List<AnnotationModel> getAnnotationModels() {
        return annotationModels;
    }

    public void setAnnotationModels(List<AnnotationModel> annotationModels) {
        this.annotationModels = annotationModels;
    }

    public void addAnnotationModels(AnnotationModel annotationModel) {
        if (this.annotationModels == null) {
            this.annotationModels = new ArrayList<>();
        }
        this.annotationModels.add(annotationModel);
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

    public boolean isStatic() {
        return isStatic;
    }

    public void setStatic(boolean aStatic) {
        isStatic = aStatic;
    }

    public ClassType getClassType() {
        return classType;
    }

    public void setClassType(ClassType classType) {
        this.classType = classType;
    }

    public List<ModifierType> getModifierTypes() {
        return modifierTypes;
    }

    public void setModifierTypes(List<ModifierType> modifierTypes) {
        this.modifierTypes = modifierTypes;
    }

    public void addModifierTypes(ModifierType modifierType) {
        if (this.modifierTypes == null) {
            this.modifierTypes = new ArrayList<>();
        }
        this.modifierTypes.add(modifierType);
    }

    public List<String> getDependNames() {
        return dependNames;
    }

    public void setDependNames(List<String> dependNames) {
        this.dependNames = dependNames;
    }

    public void addDependNames(String dependName) {
        if (this.dependNames == null) {
            this.dependNames = new ArrayList<>();
        }
        this.dependNames.add(dependName);
    }

    public List<FieldModel> getFieldModels() {
        return fieldModels;
    }

    public void setFieldModels(List<FieldModel> fieldModels) {
        this.fieldModels = fieldModels;
    }

    public void addFieldModels(FieldModel fieldModel) {
        if (this.fieldModels == null) {
            this.fieldModels = new ArrayList<>();
        }
        this.fieldModels.add(fieldModel);
    }

    public List<MethodModel> getMethodModels() {
        return methodModels;
    }

    public void setMethodModels(List<MethodModel> methodModels) {
        this.methodModels = methodModels;
    }

    public void addMethodModels(MethodModel methodModel) {
        if (this.methodModels == null) {
            this.methodModels = new ArrayList<>();
        }
        this.methodModels.add(methodModel);
    }

    public List<ClassModel> getClassModels() {
        return classModels;
    }

    public void setClassModels(List<ClassModel> classModels) {
        this.classModels = classModels;
    }

    public void addClassModels(ClassModel classModel) {
        if (this.classModels == null) {
            this.classModels = new ArrayList<>();
        }
        this.classModels.add(classModel);
    }
}

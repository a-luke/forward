package com.luke.model.java.chunk;

import com.luke.enums.KeyWordType;
import com.luke.model.java.BaseModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yangf on 2017/8/1/0001.
 */
public class ClassModel extends BaseModel {

    /**
     * 包的路径
     */
    private String packageStr;

    /**
     * 类的类型，是接口还是抽象
     */
    private KeyWordType classType;

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

    /**
     * 代码块
     */
    private List<CodeChunkModel> codeChunkModels;

    public String getPackageStr() {
        return packageStr;
    }

    public void setPackageStr(String packageStr) {
        this.packageStr = packageStr;
    }

    public KeyWordType getClassType() {
        return classType;
    }

    public void setClassType(KeyWordType classType) {
        this.classType = classType;
    }

    public List<String> getDependNames() {
        if(dependNames == null){
            dependNames = new ArrayList<>();
        }
        return dependNames;
    }

    public void setDependNames(List<String> dependNames) {
        this.dependNames = dependNames;
    }

    public ClassModel addDependNames(String dependName) {
        getDependNames().add(dependName);
        return this;
    }

    public List<FieldModel> getFieldModels() {
        if(fieldModels == null){
            fieldModels = new ArrayList<>();
        }
        return fieldModels;
    }

    public void setFieldModels(List<FieldModel> fieldModels) {
        this.fieldModels = fieldModels;
    }

    public ClassModel addFieldModels(FieldModel fieldModel) {
        getFieldModels().add(fieldModel);
        return this;
    }

    public List<MethodModel> getMethodModels() {
        if(methodModels == null){
            methodModels = new ArrayList<>();
        }
        return methodModels;
    }

    public void setMethodModels(List<MethodModel> methodModels) {
        this.methodModels = methodModels;
    }

    public ClassModel addMethodModels(MethodModel methodModel) {
        getMethodModels().add(methodModel);
        return this;
    }

    public List<ClassModel> getClassModels() {
        if(classModels == null){
            classModels = new ArrayList<>();
        }
        return classModels;
    }

    public void setClassModels(List<ClassModel> classModels) {
        this.classModels = classModels;
    }


    public ClassModel addClassModels(ClassModel classModel) {
        getClassModels().add(classModel);
        return this;
    }

    public List<CodeChunkModel> getCodeChunkModels() {
        if(codeChunkModels == null){
            codeChunkModels = new ArrayList<>();
        }
        return codeChunkModels;
    }

    public void setCodeChunkModels(List<CodeChunkModel> codeChunkModels) {
        this.codeChunkModels = codeChunkModels;
    }

    public ClassModel addCodeChunkModels(CodeChunkModel codeChunkModel) {
        getCodeChunkModels().add(codeChunkModel);
        return this;
    }
}

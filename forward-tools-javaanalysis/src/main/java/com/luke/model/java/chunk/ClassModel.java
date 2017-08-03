package com.luke.model.java.chunk;

import com.luke.enums.ClassType;
import com.luke.model.java.BaseModel;

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
    private ClassType classType;

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

    public ClassType getClassType() {
        return classType;
    }

    public void setClassType(ClassType classType) {
        this.classType = classType;
    }

    public List<String> getDependNames() {
        return dependNames;
    }

    public void setDependNames(List<String> dependNames) {
        this.dependNames = dependNames;
    }

    public List<FieldModel> getFieldModels() {
        return fieldModels;
    }

    public void setFieldModels(List<FieldModel> fieldModels) {
        this.fieldModels = fieldModels;
    }

    public List<MethodModel> getMethodModels() {
        return methodModels;
    }

    public void setMethodModels(List<MethodModel> methodModels) {
        this.methodModels = methodModels;
    }

    public List<ClassModel> getClassModels() {
        return classModels;
    }

    public void setClassModels(List<ClassModel> classModels) {
        this.classModels = classModels;
    }

    public List<CodeChunkModel> getCodeChunkModels() {
        return codeChunkModels;
    }

    public void setCodeChunkModels(List<CodeChunkModel> codeChunkModels) {
        this.codeChunkModels = codeChunkModels;
    }
}

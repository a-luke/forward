package com.luke.model.java;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yangf on 2017/8/9/0009.
 */
public class ClassNames {
    private String packages;

    private List<String> classNames;

    public String getPackages() {
        return packages;
    }

    public void setPackages(String packages) {
        this.packages = packages;
    }

    public List<String> getClassNames() {
        if (classNames == null) {
            classNames = new ArrayList<>();
        }
        return classNames;
    }

    public ClassNames setClassNames(List<String> classNames) {
        this.classNames = classNames;
        return this;
    }

    public ClassNames addClassNames(String className) {
        getClassNames().add(className);
        return this;
    }

    @Override
    public String toString() {
        return "ClassNames{" + "packages='" + packages + '\'' + ", classNames=" + classNames + '}';
    }
}

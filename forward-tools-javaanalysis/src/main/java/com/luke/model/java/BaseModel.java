package com.luke.model.java;

import com.luke.enums.AccessType;
import com.luke.enums.ModifierType;
import com.luke.model.java.chunk.AnnotationModel;
import com.luke.model.java.chunk.ClassModel;
import com.luke.model.java.chunk.Note;

import java.util.List;

public class BaseModel extends CounterModel{
    /**
     * 当前类的上一层
     */
    private ClassModel prev;

    /**
     * 类名
     */
    private String name;

    /**
     * 类的定义内容
     */
    private String content;

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
     * 修饰词
     */
    private ModifierType modifierType;

    /**
     * 是否是静态的
     */
    private boolean isStatic;

}

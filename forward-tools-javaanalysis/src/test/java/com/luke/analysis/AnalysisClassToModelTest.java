package com.luke.analysis;

import com.luke.analysis.classes.AnalysisClassToModel;
import com.luke.model.java.chunk.ClassModel;

/**
 * Created by yangf on 2017/8/3/0003.
 */
public class AnalysisClassToModelTest {

    public static void main(String[] args) {
        AnalysisClassToModel analysisClassToModel = new AnalysisClassToModel(TraverseSourceTest.PATH);
        ClassModel classModel = analysisClassToModel.getRootClass();
        System.out.println(123);
    }
}

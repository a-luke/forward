package com.luke.analysis;

import com.luke.model.java.chunk.ClassModel;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestC {
    public static void main(String[] args) {
        ClassModel classModel = new AnalysisClassToModel(TraverseSourceTest.PATH).getRootClass();
        {

        }
        List<ClassModel> classModels = classModel.getClassModels();
        for(ClassModel cm : classModels){
            if(!cm.getClassModels().isEmpty()){
                List<ClassModel> cms1 = cm.getClassModels();
                for(ClassModel cm1: cms1){
                    System.out.println(cm1.getPackageStr());
                }

            }
        }

    }


    public static void matcher(){
        Pattern pattern = Pattern.compile("^[^<]*");
        Matcher matcher = pattern.matcher("ByteArray<T");
        matcher.find();
        System.out.println(matcher.group());
    }
}

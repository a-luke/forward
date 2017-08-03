package com.luke.export;

import com.luke.analysis.AnalysisClassToModel;
import com.luke.model.FileModel;
import com.luke.model.java.BaseModel;
import com.luke.model.java.chunk.ClassModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yangf on 2017/8/3/0003.
 */
public class ExportClassModel {

    private ClassModel classModel = null;
    private List<FileModel> fileModels = new ArrayList<>();

    public ExportClassModel(String path) {
        classModel = new AnalysisClassToModel(path).getRootClass();
        loadRootClassModel(classModel);
    }

    public void loadRootClassModel(ClassModel classModel){
        FileModel fileModel = new FileModel();
        fileModel.appendContent("package " + classModel.getPackageStr());
        fileModel.appendContent(trasImport(classModel));
        loadFileModel(classModel, fileModel);
    }

    public void loadFileModel(ClassModel classModel, FileModel fileModel) {
        fileModel.setPath(classModel.getPackageStr().replace(".", "/"));

        fileModel.appendContent(trasNote(classModel));
        fileModel.appendContent(trasAnnotation(classModel));
        fileModel.appendContent(classModel.getHeader());
        fileModel.appendContent("{\n");


        fileModel.appendContent("}");
    }

    private String trasImport(ClassModel classModel){
        StringBuilder sb = new StringBuilder();
        List<String> imports = classModel.getDependNames();
        for(String im: imports){
            sb.append(im);
            sb.append("\n");
        }
        return sb.toString();
    }

    private String trasNote(BaseModel baseModel){
        StringBuilder sb = new StringBuilder();


        return sb.toString();
    }

    private String trasAnnotation(BaseModel baseModel){
        StringBuilder sb = new StringBuilder();


        return sb.toString();
    }

    private String trasField(ClassModel classModel) {
        StringBuilder sb = new StringBuilder();


        return sb.toString();
    }

    private String trasMethod(ClassModel classModel) {
        StringBuilder sb = new StringBuilder();


        return sb.toString();
    }

    private void trasClass(ClassModel classModel, FileModel fileModel){
        for(ClassModel cm : classModel.getClassModels()){
            if(cm.isStatic()){
                FileModel fm = new FileModel();
                fileModels.add(fm);
                loadFileModel(cm, fm);
            }else{
                loadFileModel(cm, fileModel);
            }
        }
    }

}

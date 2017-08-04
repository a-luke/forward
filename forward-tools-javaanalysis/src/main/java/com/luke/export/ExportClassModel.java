package com.luke.export;

import com.luke.analysis.AnalysisClassToModel;
import com.luke.enums.KeyWordType;
import com.luke.model.FileModel;
import com.luke.model.java.BaseModel;
import com.luke.model.java.chunk.*;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by yangf on 2017/8/3/0003.
 */
public class ExportClassModel {

    private List<FileModel> fileModels = new ArrayList<>();

    public List<FileModel> getFileModels() {
        return fileModels;
    }

    public ExportClassModel(String path) {
        loadRootClassModel(new AnalysisClassToModel(path).getRootClass());
    }

    public void loadRootClassModel(ClassModel classModel) {
        FileModel fileModel = new FileModel();
        fileModels.add(fileModel);
        fileModel.appendContent("package " + classModel.getPackageStr() + "\n");
        fileModel.appendContent(trasImport(classModel));
        loadFileModel(classModel, fileModel);

        Pattern pattern = Pattern.compile("^[^<]*");
        Matcher matcher = pattern.matcher(classModel.getName());
        matcher.find();
        fileModel.setName(matcher.group());

        fileModel.setPath(classModel.getPackageStr().replace(".", "/").replace(";", "").trim());
    }

    public void loadFileModel(ClassModel classModel, FileModel fileModel) {
        fileModel.appendContent(trasNote(classModel));
        fileModel.appendContent(trasAnnotation(classModel));
        fileModel.appendContent(classModel.getHeader().replace(KeyWordType.STATIC.getValue(), ""));
        fileModel.appendContent("{\n");
        fileModel.appendContent(trasChunk(classModel));
        fileModel.appendContent(trasField(classModel));
        trasClass(classModel, fileModel);
        fileModel.appendContent(trasMethod(classModel));
        fileModel.appendContent("}");
    }

    private String trasImport(ClassModel classModel) {
        StringBuilder sb = new StringBuilder();
        List<String> imports = classModel.getDependNames();
        for (String im : imports) {
            sb.append("import ");
            sb.append(im);
            sb.append("\n");
        }
        return sb.toString();
    }

    private String trasNote(BaseModel baseModel) {
        if (baseModel.getNote() == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        List<String> notes = baseModel.getNote().getContent();
        for (String noteContent : notes) {
            sb.append(noteContent);
            sb.append("\n");
        }
        return sb.toString();
    }

    private String trasAnnotation(BaseModel baseModel) {
        StringBuilder sb = new StringBuilder();
        List<AnnotationModel> annotationModels = baseModel.getAnnotationModels();
        for (AnnotationModel annotationModel : annotationModels) {
            sb.append(annotationModel.getContent());
            sb.append("\n");
        }
        return sb.toString();
    }

    private String trasChunk(ClassModel classModel) {
        StringBuilder sb = new StringBuilder();
        List<CodeChunkModel> codeChunkModels = classModel.getCodeChunkModels();
        for (CodeChunkModel codeChunkModel : codeChunkModels) {
            sb.append(trasNote(codeChunkModel));
            sb.append(trasAnnotation(codeChunkModel));
            sb.append(codeChunkModel.getHeader());
            sb.append(codeChunkModel.getContent() + "\n");
        }
        return sb.toString();
    }

    private String trasField(ClassModel classModel) {
        StringBuilder sb = new StringBuilder();
        List<FieldModel> fieldModels = classModel.getFieldModels();
        for (FieldModel fieldModel : fieldModels) {
            sb.append(trasNote(fieldModel));
            sb.append(trasAnnotation(fieldModel));
            sb.append(fieldModel.getHeader() + "\n");
            sb.append(fieldModel.getContent() + "\n");
        }
        return sb.toString();
    }

    private String trasMethod(ClassModel classModel) {
        StringBuilder sb = new StringBuilder();
        List<MethodModel> methodModels = classModel.getMethodModels();
        for (MethodModel methodModel : methodModels) {
            sb.append(trasNote(methodModel));
            sb.append(trasAnnotation(methodModel));
            sb.append(methodModel.getHeader() + "\n");
            sb.append(methodModel.getContent() + "\n");
        }

        return sb.toString();
    }

    private void trasClass(ClassModel classModel, FileModel fileModel) {
        for (ClassModel cm : classModel.getClassModels()) {
            if (cm.isStatic() && prevIsStatic(cm)) {
                loadRootClassModel(cm);
            } else {
                loadFileModel(cm, fileModel);
            }
        }
    }

    private boolean prevIsStatic(ClassModel classModel) {
        ClassModel cm = classModel;
        while ((cm = cm.getPrev()) != null && cm.getPrev() != null) {
            if (!cm.isStatic()) {
                return false;
            }
        }
        return true;
    }
}

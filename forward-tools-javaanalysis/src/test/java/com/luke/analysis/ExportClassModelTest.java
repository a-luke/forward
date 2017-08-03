package com.luke.analysis;

import com.luke.export.ExportClassModel;
import com.luke.model.FileModel;
import com.luke.utils.FileHandle;

import java.util.List;

public class ExportClassModelTest {

    public static void main(String[] args) {
        ExportClassModel exportClassModel = new ExportClassModel(TraverseSourceTest.PATH);
        List<FileModel> fileModelList = exportClassModel.getFileModels();
        for (FileModel fieldModel : fileModelList) {
            try {
                FileHandle.writeJava(fieldModel);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}

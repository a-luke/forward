package com.luke.analysis.load.impl;

import com.luke.analysis.load.LoadSourceAbstract;
import com.luke.analysis.traverse.TraverseSource;
import com.luke.enums.ChunkType;
import com.luke.enums.GSType;
import com.luke.model.WordModel;
import com.luke.model.java.chunk.AnnotationModel;
import com.luke.model.java.chunk.ClassModel;
import com.luke.model.java.chunk.FieldModel;
import com.luke.model.java.chunk.Note;

import java.util.List;

/**
 * Created by yangf on 2017/8/4/0004.
 * 枚举字段的处理实现
 */
public class LoadSourceToEnum extends LoadSourceAbstract<FieldModel, List<WordModel>, ClassModel> {

    public LoadSourceToEnum(TraverseSource<List<WordModel>> traverseLine, Note note, List<AnnotationModel> annotationModel) {
        super(traverseLine, note, annotationModel);
    }

    @Override
    public void addNewChunk(ClassModel classModel, List<WordModel> wordModels) {
        FieldModel fieldModel = new FieldModel();
        fieldModel.setPrev(classModel);
        fieldModel.setEnum(true);
        classModel.addFieldModels(fieldModel);
        load(fieldModel, wordModels);
        analysis(fieldModel);
    }

    @Override
    public void load(FieldModel fieldModel, List<WordModel> wordModels) {
        addNoteAndAnnotation(fieldModel);
        fieldModel.setHeader(join(wordModels));
    }

    @Override
    public void analysis(FieldModel fieldModel) {
        ChunkType chunkType = getType(1, ChunkType.METHOD);
        if (fieldModel.getHeader().endsWith(";") || chunkType != ChunkType.LD) {
            return;
        }

        if (chunkType == ChunkType.LD) {
            fieldModel.addStep();
        } else if (chunkType == ChunkType.RD) {
            fieldModel.minusStep();
        }
        if (!fieldModel.isEnd()) {
            fieldModel.appendContent(join(traverseLine.next()));
            analysis(fieldModel);
        } else if (fieldModel.getContent() != null) {
            fieldModel.appendContent(join(traverseLine.next()));
            //有可能有},的形式，结尾的,在下一行，所以需要加给当前的enum内容
            List<WordModel> wms = traverseLine.next(1);
            GSType type = wms.get(0).getType();
            if(wms.size() == 1 && (type == GSType.DH || type == GSType.FH)){
                fieldModel.appendContent(join(traverseLine.next()));
            }
        }
    }
}

package com.luke.analysis.load.impl;

import com.luke.analysis.load.LoadSourceAbstract;
import com.luke.analysis.traverse.TraverseSource;
import com.luke.analysis.traverse.impl.TraverseList;
import com.luke.enums.ChunkType;
import com.luke.enums.GSType;
import com.luke.enums.KeyWordType;
import com.luke.model.WordModel;
import com.luke.model.java.chunk.AnnotationModel;
import com.luke.model.java.chunk.ClassModel;
import com.luke.model.java.chunk.FieldModel;
import com.luke.model.java.chunk.Note;

import java.util.List;

/**
 * Created by yangf on 2017/8/4/0004.
 * 字段的处理实现
 */
public class LoadSourceToField extends LoadSourceAbstract<FieldModel, List<WordModel>, ClassModel> {
    public LoadSourceToField(TraverseSource<List<WordModel>> traverseLine, Note note, List<AnnotationModel> annotationModel) {
        super(traverseLine, note, annotationModel);
    }

    @Override
    public void addNewChunk(ClassModel classModel, List<WordModel> wordModels) {
        FieldModel fieldModel = new FieldModel();
        fieldModel.setPrev(classModel);
        classModel.addFieldModels(fieldModel);
        fieldModel.setEnum(false);
        load(fieldModel, wordModels);
        analysis(fieldModel);
    }

    @Override
    public void load(FieldModel fieldModel, List<WordModel> wordModels) {
        TraverseSource<WordModel> traverse = new TraverseList(wordModels);
        while (traverse.next(1) != null) {
            WordModel wordModel = traverse.next();
            KeyWordType type = wordModel.getWdType();

            if (KeyWordType.isAccess(type)) {
                fieldModel.setAccessType(type);
            } else if (KeyWordType.isModifier(type)) {
                fieldModel.addModifierTypes(type);
                if (type == KeyWordType.STATIC) {
                    fieldModel.setStatic(true);
                }
            } else if (wordModel.getType() == GSType.LX && fieldModel.getName() != null) {
                fieldModel.setName(traverse.next(1).getWord());
            }
        }

        addNoteAndAnnotation(fieldModel);

        fieldModel.setHeader(join(wordModels));
    }

    @Override
    public void analysis(FieldModel fieldModel) {
        ChunkType chunkType = getType(1, ChunkType.METHOD);
        if (fieldModel.getHeader().endsWith(GSType.FH.value())|| chunkType != ChunkType.LD) {
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
        } else {
            List<WordModel> nwms = traverseLine.next(1);
            WordModel nwm = nwms.get(nwms.size() - 1);
            fieldModel.appendContent(join(traverseLine.next()));
            if (nwm.getType() != GSType.FH) {
                analysis(fieldModel);
            }
        }
    }
}

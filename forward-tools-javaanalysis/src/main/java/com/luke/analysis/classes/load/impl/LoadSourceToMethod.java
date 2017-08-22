package com.luke.analysis.classes.load.impl;

import com.luke.analysis.classes.load.LoadSourceAbstract;
import com.luke.traverse.TraverseSource;
import com.luke.traverse.impl.TraverseList;
import com.luke.enums.ChunkType;
import com.luke.enums.GSType;
import com.luke.enums.KeyWordType;
import com.luke.model.WordModel;
import com.luke.model.java.chunk.AnnotationModel;
import com.luke.model.java.chunk.ClassModel;
import com.luke.model.java.chunk.MethodModel;
import com.luke.model.java.chunk.Note;

import java.util.List;

/**
 * Created by yangf on 2017/8/4/0004.
 * 方法的处理实现
 */
public class LoadSourceToMethod extends LoadSourceAbstract<MethodModel, List<WordModel>, ClassModel> {
    public LoadSourceToMethod(TraverseSource<List<WordModel>> traverseLine, Note note, List<AnnotationModel> annotationModel) {
        super(traverseLine, note, annotationModel);
    }

    @Override
    public void addNewChunk(ClassModel classModel, List<WordModel> wordModels) {
        MethodModel methodModel = new MethodModel();
        methodModel.setPrev(classModel);
        classModel.addMethodModels(methodModel);
        load(methodModel, wordModels);
        analysis(methodModel);
    }

    @Override
    public void load(MethodModel methodModel, List<WordModel> wordModels) {
        TraverseSource<WordModel> traverse = new TraverseList(wordModels);
        while (traverse.next(1) != null) {
            WordModel wordModel = traverse.next();
            KeyWordType type = wordModel.getWdType();

            if (KeyWordType.isAccess(type)) {
                methodModel.setAccessType(type);
            } else if (KeyWordType.isModifier(type)) {
                methodModel.addModifierTypes(type);
                if (type == KeyWordType.STATIC) {
                    methodModel.setStatic(true);
                }
            } else if (wordModel.getType() == GSType.LX && methodModel.getName() == null) {
                methodModel.setName(traverse.next(-1).getWord());
            }
        }

        addNoteAndAnnotation(methodModel);

        methodModel.setHeader(join(wordModels));
    }

    @Override
    public void analysis(MethodModel methodModel) {
        if (methodModel.getHeader().endsWith(GSType.FH.value())) {
            return;
        }
        ChunkType chunkType = getType(1, ChunkType.METHOD);
        if (chunkType == ChunkType.LD) {
            methodModel.addStep();
        } else if (chunkType == ChunkType.RD) {
            methodModel.minusStep();
        }
        if (!methodModel.isEnd()) {
            methodModel.appendContent(join(traverseLine.next()));
            analysis(methodModel);
        } else if (methodModel.getContent() != null) {
            methodModel.appendContent(join(traverseLine.next()));
        }
    }
}

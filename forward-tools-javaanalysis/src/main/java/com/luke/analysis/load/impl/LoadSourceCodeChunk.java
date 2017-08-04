package com.luke.analysis.load.impl;

import com.luke.analysis.load.LoadSourceAbstract;
import com.luke.analysis.traverse.TraverseSource;
import com.luke.enums.ChunkType;
import com.luke.enums.KeyWordType;
import com.luke.model.WordModel;
import com.luke.model.java.chunk.AnnotationModel;
import com.luke.model.java.chunk.ClassModel;
import com.luke.model.java.chunk.CodeChunkModel;
import com.luke.model.java.chunk.Note;

import java.util.List;

/**
 * Created by yangf on 2017/8/4/0004.
 * 代码块的处理实现
 */
public class LoadSourceCodeChunk extends LoadSourceAbstract<CodeChunkModel, List<WordModel>, ClassModel> {
    public LoadSourceCodeChunk(TraverseSource<List<WordModel>> traverseLine, Note note, List<AnnotationModel> annotationModel) {
        super(traverseLine, note, annotationModel);
    }

    @Override
    public void addNewChunk(ClassModel classModel, List<WordModel> wordModels) {
        CodeChunkModel codeChunkModel = new CodeChunkModel();
        classModel.addCodeChunkModels(codeChunkModel);
        codeChunkModel.setPrev(classModel);
        load(codeChunkModel, wordModels);
        analysis(codeChunkModel);
    }

    @Override
    public void load(CodeChunkModel codeChunkModel, List<WordModel> wordModels) {
        KeyWordType type = wordModels.get(0).getWdType();
        if (type == KeyWordType.STATIC) {
            codeChunkModel.setStatic(true);
            codeChunkModel.addModifierTypes(type);
            codeChunkModel.setHeader(KeyWordType.STATIC.getValue());
        }
    }

    @Override
    public void analysis(CodeChunkModel codeChunkModel) {
        ChunkType chunkType = getType(1);
        if (chunkType == ChunkType.LD) {
            codeChunkModel.addStep();
        } else if (chunkType == ChunkType.RD) {
            codeChunkModel.minusStep();
        }
        if (!codeChunkModel.isEnd()) {
            codeChunkModel.appendContent(join(traverseLine.next()));
            analysis(codeChunkModel);
        } else if (codeChunkModel.getContent() != null) {
            codeChunkModel.appendContent(join(traverseLine.next()));
        }
    }
}

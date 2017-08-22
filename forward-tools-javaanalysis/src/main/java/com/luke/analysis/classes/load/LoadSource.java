package com.luke.analysis.classes.load;

import com.luke.analysis.classes.load.impl.*;
import com.luke.traverse.TraverseSource;
import com.luke.enums.ChunkType;
import com.luke.model.WordModel;
import com.luke.model.java.chunk.AnnotationModel;
import com.luke.model.java.chunk.Note;

import java.util.List;

/**
 * Created by yangf on 2017/8/4/0004.
 */
public interface LoadSource<T, D, O> {

    void addNewChunk(O o, D d);

    void load(T t, D d);

    void analysis(T t);

    ChunkType getType(int index);

    String join(List<WordModel> wordModels);

    static LoadSource newInstance(ChunkType chunkType, TraverseSource<List<WordModel>> traverseLine, Note note, List<AnnotationModel> annotationModel) {
        switch (chunkType) {
            case ClASS:
                return new LoadSourceToClass(traverseLine, note, annotationModel);
            case FIELD:
                return new LoadSourceToField(traverseLine, note, annotationModel);
            case ENUM:
                return new LoadSourceToEnum(traverseLine, note, annotationModel);
            case METHOD:
                return new LoadSourceToMethod(traverseLine, note, annotationModel);
            case CHUNK:
                return new LoadSourceCodeChunk(traverseLine, note, annotationModel);
        }
        return null;
    }
}

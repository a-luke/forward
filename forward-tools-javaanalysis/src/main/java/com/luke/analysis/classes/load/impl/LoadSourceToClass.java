package com.luke.analysis.classes.load.impl;

import com.luke.analysis.classes.load.LoadSource;
import com.luke.analysis.classes.load.LoadSourceAbstract;
import com.luke.traverse.TraverseSource;
import com.luke.traverse.impl.TraverseList;
import com.luke.enums.ChunkType;
import com.luke.enums.KeyWordType;
import com.luke.model.WordModel;
import com.luke.model.java.chunk.AnnotationModel;
import com.luke.model.java.chunk.ClassModel;
import com.luke.model.java.chunk.Note;

import java.util.List;

/**
 * Created by yangf on 2017/8/4/0004.
 * 类的处理实现
 */
public class LoadSourceToClass extends LoadSourceAbstract<ClassModel, List<WordModel>, ClassModel> {
    public LoadSourceToClass(TraverseSource<List<WordModel>> traverseLine, Note note, List<AnnotationModel> annotationModel) {
        super(traverseLine, note, annotationModel);
    }

    @Override
    public void addNewChunk(ClassModel classModel, List<WordModel> wordModels) {
        //加载类的基本信息
        ClassModel nodeCM = new ClassModel();
        classModel.addClassModels(nodeCM);
        nodeCM.setPrev(classModel);
        load(nodeCM, wordModels);
        analysis(nodeCM);
    }

    @Override
    public void load(ClassModel classModel, List<WordModel> wordModels) {
        TraverseSource<WordModel> traverse = new TraverseList(wordModels);
        while (traverse.next(1) != null) {
            WordModel wordModel = traverse.next();
            KeyWordType type = wordModel.getWdType();

            if (KeyWordType.isAccess(type)) {
                classModel.setAccessType(type);
            } else if (KeyWordType.isModifier(type)) {
                classModel.addModifierTypes(type);
                if (type == KeyWordType.STATIC) {
                    classModel.setStatic(true);
                }
            } else if (KeyWordType.isClass(type)) {
                classModel.setClassType(type);
                classModel.setName(traverse.next(1).getWord());
            }
        }

        addNoteAndAnnotation(classModel);

        classModel.setHeader(join(wordModels));
    }

    @Override
    public void analysis(ClassModel classModel) {
        List<WordModel> wordModels = traverseLine.next();

        ChunkType chunkType = getType(0);
        if (chunkType == ChunkType.LD) {
            classModel.addStep();
        } else if (chunkType == ChunkType.RD) {
            classModel.minusStep();
        } else if (chunkType == ChunkType.NOTE) {
            note.addContent(join(wordModels));
        } else if (chunkType == ChunkType.ANNOTATION) {
            annotationModel.add(new AnnotationModel(join(wordModels)));
        } else {
            LoadSource loadSource = LoadSource.newInstance(chunkType, traverseLine, note, annotationModel);
            if (loadSource != null) {
                loadSource.addNewChunk(classModel, wordModels);
            }
        }

        if (!classModel.isEnd()) {
            this.analysis(classModel);
            return;
        }
        //当前类结束后回到上一层
        if (classModel.getPrev() != null) {
            this.analysis(classModel.getPrev());
        }
    }
}

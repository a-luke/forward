package com.luke.analysis.load;

import com.luke.analysis.traverse.TraverseSource;
import com.luke.analysis.traverse.impl.TraverseList;
import com.luke.enums.ChunkType;
import com.luke.enums.GSType;
import com.luke.enums.KeyWordType;
import com.luke.model.WordModel;
import com.luke.model.java.BaseModel;
import com.luke.model.java.chunk.AnnotationModel;
import com.luke.model.java.chunk.Note;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yangf on 2017/8/4/0004.
 */
public abstract class LoadSourceAbstract<T, D, O> implements LoadSource<T, D, O> {

    protected TraverseSource<List<WordModel>> traverseLine;
    protected Note note;
    protected List<AnnotationModel> annotationModel;

    public LoadSourceAbstract(TraverseSource<List<WordModel>> traverseLine, Note note, List<AnnotationModel> annotationModel) {
        this.traverseLine = traverseLine;
        this.note = note;
        this.annotationModel = annotationModel;
    }

    /**
     * 将wordModels拼接转化为字符串
     *
     * @param wordModels
     * @return
     */
    public String join(List<WordModel> wordModels) {
        StringBuilder sb = new StringBuilder();

        TraverseSource<WordModel> traverse = new TraverseList(wordModels);
        WordModel wordModel = null;
        while ((wordModel = traverse.next()) != null) {
            KeyWordType type = wordModel.getWdType();
            if (type == KeyWordType.PACKAGE || type == KeyWordType.IMPORT) {
                continue;
            }
            sb.append(wordModel.getWord());
            if (traverse.next(1) != null && !GSType.isX(traverse.next(1).getType()) && !GSType.isX(traverse.next(0).getType())) {
                sb.append(" ");
            }
        }
        return sb.toString();
    }

    /**
     * 获取下index行的类型
     *
     * @param index
     * @return
     */
    @Override
    public ChunkType getType(int index) {
        List<WordModel> wordModels = traverseLine.next(index);

        if (wordModels == null) {
            return null;
        }

        TraverseSource<WordModel> traverse = new TraverseList(wordModels);

        //是注解的开始
        WordModel wordModel = wordModels.get(0);
        if (wordModel.getWord().startsWith("@") && wordModel.getWdType() != KeyWordType.ANNOCLASS) {
            return ChunkType.ANNOTATION;
        }

        if (wordModels.size() == 1) {
            //是代码块的开始
            if (wordModel.getWdType() == KeyWordType.STATIC) {
                return ChunkType.CHUNK;
            } else if (wordModel.getType() == GSType.LD) {
                List<WordModel> pWMs = null;
                int whileI = -1;
                while ((pWMs = traverseLine.next(index + whileI)).get(0).getType() == GSType.LXG) {
                    whileI--;
                }
                WordModel pWM = pWMs.get(0);
                if (GSType.isChunkGist(pWM.getType()) || pWMs.get(pWMs.size() - 1).getType() == GSType.FH) {
                    return ChunkType.CHUNK;
                }
            }

            //是注释或者括号
            return wordModel.getType() == GSType.LD ?
                ChunkType.LD :
                (wordModel.getType() == GSType.RD ? ChunkType.RD : (wordModel.getType() == GSType.LXG ? ChunkType.NOTE : ChunkType.ENUM));
        }

        //判断是类或者方法
        while (traverse.next(1) != null) {
            WordModel whileWM = traverse.next();
            KeyWordType keyWordType = whileWM.getWdType();
            //是类的开始
            if (KeyWordType.isClass(keyWordType)) {
                return ChunkType.ClASS;
            }

            GSType type = whileWM.getType();
            if (type != GSType.LX) {
                continue;
            }
            boolean methodFlag = true;
            for (int i = 0, len = traverse.getIndex(); i < len; i++) {
                WordModel wm = wordModels.get(i);
                //判断当前是不是枚举的属性
                if (wm.getType() == GSType.DH || traverse.getIndex() == 1) {
                    return ChunkType.ENUM;
                }

                if (wm.getType() != GSType.SY && wm.getWord().contains("=")) {
                    methodFlag = false;
                    break;
                }
            }

            //是方法的开始
            if (methodFlag) {
                return ChunkType.METHOD;
            }

        }

        return ChunkType.FIELD;
    }

    public ChunkType getType(int index, ChunkType type) {
        List<WordModel> wordModels = traverseLine.next(index);

        if (wordModels == null) {
            return null;
        }

        if (type == ChunkType.METHOD) {
            TraverseSource<WordModel> traverse = new TraverseList(wordModels);

            WordModel wordModel = wordModels.get(0);
            //是括号或者代码块
            return wordModel.getType() == GSType.LD ? ChunkType.LD : (wordModel.getType() == GSType.RD ? ChunkType.RD : ChunkType.CHUNK);

        }
        return null;
    }

    /**
     * 添加注释和注解
     *
     * @param baseModel 类、方法、字段, 枚举
     */
    protected void addNoteAndAnnotation(BaseModel baseModel) {
        if (!annotationModel.isEmpty()) {
            List<AnnotationModel> annotationModels = new ArrayList<>();
            annotationModels.addAll(annotationModel);
            baseModel.setAnnotationModels(annotationModels);
            annotationModel.clear();
        }

        if (!note.isEmpty()) {
            Note n = new Note();
            n.addAll(note);
            baseModel.setNote(n);
            note.clear();
        }
    }

}

package com.luke.analysis;

import com.luke.analysis.traverse.TraverseSource;
import com.luke.analysis.traverse.impl.TraverseList;
import com.luke.enums.ChunkType;
import com.luke.enums.GSType;
import com.luke.enums.KeyWordType;
import com.luke.model.WordModel;
import com.luke.model.java.chunk.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yangf on 2017/8/2/0002.
 */
public class AnalysisClassToModel {

    private TraverseSource<List<WordModel>> traverseLine;

    private ClassModel rootClass;

    //拼接注释
    private Note note;
    private List<AnnotationModel> annotationModel;

    public AnalysisClassToModel(String path) {
        //初始化变量，因为直接在构造函数里调用，在字段初始化已经迟了,字段赋值发生在对象构造之后；
        note = new Note();
        annotationModel = new ArrayList<>();
        traverseLine = new TraverseList(new AnalysisSentence(path).getSentence());
        rootClass = new ClassModel();
        analysis();
    }

    public ClassModel getRootClass() {
        return rootClass;
    }

    //主类的头部的处理
    private void analysis() {
        //注释对象
        Note note = new Note();
        //加载主类的头信息,package, import, note
        while (getType(1) != ChunkType.ClASS) {
            List<WordModel> wordModels = traverseLine.next();
            WordModel wordModel = wordModels.get(0);

            if (wordModel.getType() == GSType.LXG) {
                //注释
                note.addContent(join(wordModels));
                rootClass.setNote(note);
            } else if (wordModel.getWdType() == KeyWordType.PACKAGE) {
                //包路径
                rootClass.setPackageStr(join(wordModels));
            } else if (wordModel.getWdType() == KeyWordType.IMPORT) {
                //依赖
                rootClass.addDependNames(join(wordModels));
            } else if (wordModel.getWord().startsWith("@")) {
                //注解
                rootClass.addAnnotationModels(new AnnotationModel(join(wordModels)));
            }
        }

        //加载类的基本信息
        loadClassModel(rootClass, traverseLine.next());

        //递归处理类和方法
        analysisClass(rootClass);
    }

    /**
     * 处理类
     *
     * @param classModel
     */
    private void analysisClass(ClassModel classModel) {
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
        } else if (chunkType == ChunkType.ClASS) {
            //加载类的基本信息
            ClassModel nodeCM = new ClassModel();
            classModel.addClassModels(nodeCM);
            nodeCM.setPrev(classModel);
            loadClassModel(nodeCM, wordModels);
            analysisClass(nodeCM);
        } else if (chunkType == ChunkType.METHOD) {
            MethodModel methodModel = new MethodModel();
            classModel.addMethodModels(methodModel);
            methodModel.setPrev(classModel);
            loadMethodModel(methodModel, wordModels);
            analysisMethod(methodModel);
        } else if (chunkType == ChunkType.CHUNK) {
            CodeChunkModel codeChunkModel = new CodeChunkModel();
            classModel.addCodeChunkModels(codeChunkModel);
            codeChunkModel.setPrev(classModel);
            loadCodeChunk(codeChunkModel, wordModels);
            analysisCodeChunk(codeChunkModel);
        } else if (chunkType == ChunkType.FIELD) {
            FieldModel fieldModel = new FieldModel();
            classModel.addFieldModels(fieldModel);
            fieldModel.setPrev(classModel);
            loadField(fieldModel, wordModels);

            analysisField(fieldModel);
        }

        if (!classModel.isEnd()) {
            analysisClass(classModel);
            return;
        }
        //当前类结束后回到上一层
        if (classModel.getPrev() != null) {
            analysisClass(classModel.getPrev());
        }

    }

    /**
     * 装载类信息
     *
     * @param classModel
     * @param wordModels
     */
    private void loadClassModel(ClassModel classModel, List<WordModel> wordModels) {
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

        if (!annotationModel.isEmpty()) {
            classModel.setAnnotationModels(annotationModel);
            annotationModel = new ArrayList<>();
        }

        if (!note.isEmpty()) {
            classModel.setNote(note);
            note = new Note();
        }

        classModel.setHeader(join(wordModels));
    }

    /**
     * 处理方法
     *
     * @param methodModel
     */
    private void analysisMethod(MethodModel methodModel) {
        ChunkType chunkType = getType(1);
        if (chunkType == ChunkType.LD) {
            methodModel.addStep();
        } else if (chunkType == ChunkType.RD) {
            methodModel.minusStep();
        }
        if (!methodModel.isEnd()) {
            methodModel.appendContent(join(traverseLine.next()));
            analysisMethod(methodModel);
        } else if (methodModel.getContent() != null) {
            methodModel.appendContent(join(traverseLine.next()));
        }
    }

    /**
     * 加载方法
     *
     * @param methodModel
     * @param wordModels
     */
    private void loadMethodModel(MethodModel methodModel, List<WordModel> wordModels) {

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

        if (!annotationModel.isEmpty()) {
            methodModel.setAnnotationModels(annotationModel);
            annotationModel = new ArrayList<>();
        }

        if (!note.isEmpty()) {
            methodModel.setNote(note);
            note = new Note();
        }

        methodModel.setHeader(join(wordModels));
    }

    /**
     * 加载字段
     */
    private void loadField(FieldModel fieldModel, List<WordModel> wordModels) {
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

        if (!annotationModel.isEmpty()) {
            fieldModel.setAnnotationModels(annotationModel);
            annotationModel = new ArrayList<>();
        }

        if (!note.isEmpty()) {
            fieldModel.setNote(note);
            note = new Note();
        }

        fieldModel.setHeader(join(wordModels));
    }

    /**
     * 处理字段
     *
     * @param fieldModel
     */
    private void analysisField(FieldModel fieldModel) {
        if (fieldModel.getHeader().endsWith(";")) {
            return;
        }

        ChunkType chunkType = getType(1);
        if (chunkType == ChunkType.LD) {
            if (fieldModel.getContent().length() == 0) {
                fieldModel.appendContent("{");
            }
            fieldModel.addStep();
        } else if (chunkType == ChunkType.RD) {
            fieldModel.minusStep();
        }

        if (!fieldModel.isEnd()) {
            fieldModel.appendContent(join(traverseLine.next()));
            analysisField(fieldModel);
        } else {
            List<WordModel> nwms = traverseLine.next(1);
            WordModel nwm = nwms.get(nwms.size() - 1);
            fieldModel.appendContent(join(traverseLine.next()));
            if (nwm.getType() != GSType.FH) {
                analysisField(fieldModel);
            }
        }

    }

    private void loadCodeChunk(CodeChunkModel codeChunkModel, List<WordModel> wordModels) {
        KeyWordType type = wordModels.get(0).getWdType();
        if (type == KeyWordType.STATIC) {
            codeChunkModel.setStatic(true);
            codeChunkModel.addModifierTypes(type);
        }
    }

    /**
     * 处理代码块
     */
    private void analysisCodeChunk(CodeChunkModel codeChunkModel) {
        ChunkType chunkType = getType(1);
        if (chunkType == ChunkType.LD) {
            codeChunkModel.addStep();
        } else if (chunkType == ChunkType.RD) {
            codeChunkModel.minusStep();
        }
        if (!codeChunkModel.isEnd()) {
            codeChunkModel.appendContent(join(traverseLine.next()));
            analysisCodeChunk(codeChunkModel);
        } else if (codeChunkModel.getContent() != null) {
            codeChunkModel.appendContent(join(traverseLine.next()));
        }
    }

    /**
     * 将wordModels拼接转化为字符串
     *
     * @param wordModels
     * @return
     */
    private String join(List<WordModel> wordModels) {
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
    private ChunkType getType(int index) {
        List<WordModel> wordModels = traverseLine.next(index);

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
                while ((pWMs = traverseLine.next(index - 1)).get(0).getType() == GSType.LXG) {
                }
                WordModel pWM = pWMs.get(0);
                if (GSType.isChunkGist(pWM.getType()) || pWMs.get(pWMs.size() - 1).getType() == GSType.FH) {
                    return ChunkType.CHUNK;
                }
            }

            //是注释或者括号
            return wordModel.getType() == GSType.LD ?
                    ChunkType.LD :
                    (wordModel.getType() == GSType.RD ? ChunkType.RD : (wordModel.getType() == GSType.LXG ? ChunkType.NOTE : null));
        }

        while (traverse.next(1) != null) {
            WordModel whileWM = traverse.next();
            KeyWordType keyWordType = whileWM.getWdType();
            //是类的开始
            if (KeyWordType.isClass(keyWordType)) {
                return ChunkType.ClASS;
            }

            //是方法的开始
            GSType type = whileWM.getType();
            if (type != GSType.LX) {
                continue;
            }
            boolean flag = true;
            for (int i = 0, len = traverse.getIndex(); i < len; i++) {
                WordModel wm = wordModels.get(i);
                if (wm.getType() != GSType.SY && wm.getWord().contains("=")) {
                    flag = false;
                }
            }
            if (flag) {
                return ChunkType.METHOD;
            }

        }

        return ChunkType.FIELD;
    }
}

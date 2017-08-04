package com.luke.analysis;

import com.luke.analysis.load.LoadSource;
import com.luke.analysis.traverse.TraverseSource;
import com.luke.analysis.traverse.impl.TraverseList;
import com.luke.enums.ChunkType;
import com.luke.enums.GSType;
import com.luke.enums.KeyWordType;
import com.luke.model.WordModel;
import com.luke.model.java.chunk.AnnotationModel;
import com.luke.model.java.chunk.ClassModel;
import com.luke.model.java.chunk.Note;

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

        LoadSource loadSource = LoadSource.newInstance(ChunkType.ClASS, traverseLine, note, annotationModel);

        //注释对象
        Note note = new Note();
        //加载主类的头信息,package, import, note
        while (loadSource.getType(1) != ChunkType.ClASS) {
            List<WordModel> wordModels = traverseLine.next();
            WordModel wordModel = wordModels.get(0);

            if (wordModel.getType() == GSType.LXG) {
                //注释
                note.addContent(loadSource.join(wordModels));
                rootClass.setNote(note);
            } else if (wordModel.getWdType() == KeyWordType.PACKAGE) {
                //包路径
                rootClass.setPackageStr(loadSource.join(wordModels));
            } else if (wordModel.getWdType() == KeyWordType.IMPORT) {
                //依赖
                rootClass.addDependNames(loadSource.join(wordModels));
            } else if (wordModel.getWord().startsWith("@")) {
                //注解
                rootClass.addAnnotationModels(new AnnotationModel(loadSource.join(wordModels)));
            }
        }

        //加载类的基本信息
        loadSource.load(rootClass, traverseLine.next());

        //递归处理类和方法
        loadSource.analysis(rootClass);
    }
}

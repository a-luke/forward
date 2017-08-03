package com.luke.analysis;

import com.luke.analysis.traverse.TraverseSource;
import com.luke.analysis.traverse.impl.TraverseList;
import com.luke.model.WordModel;

import java.util.List;

/**
 * Created by yangf on 2017/8/2/0002.
 */
public class AnalysisClassToModel {

    private TraverseSource<List<WordModel>> traverseLine;

    public AnalysisClassToModel(String path){
        traverseLine = new TraverseList(new AnalysisSentence(path).getSentence());
        analysis();
    }

    private void analysis(){
        List<WordModel> wordModels = traverseLine.next();

    }


}

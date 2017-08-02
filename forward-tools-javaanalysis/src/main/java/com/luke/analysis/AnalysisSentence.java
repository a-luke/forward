package com.luke.analysis;

import com.luke.enums.GSType;
import com.luke.model.WordModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yangf on 2017/8/2/0002.
 */
public class AnalysisSentence extends AbstractSentenceAnalysis {
    public List<List<WordModel>> sentence;


    public AnalysisSentence(String path) {
        super(path);
    }

    @Override
    protected void analysis(){
        if(sentence == null){
            sentence = new ArrayList<>();
        }
        List<WordModel> wordModels = new ArrayList<>();
        WordModel wordModel = next();
        //如果是回车就重新获取下一个
        if(wordModel.getType() == GSType.RXG){
            analysis();
            return;
        }

        wordModels.add(wordModel);

        if(!GSType.isLineEnd( wordModel.getType())){
            while (next(0).getType() != GSType.FH && !GSType.isLineEnd(next(1).getType())){
                WordModel nwm = next();
                if(nwm.getType() != GSType.RXG){
                    wordModels.add(nwm);
                }
            }
        }
        sentence.add(wordModels);

        if(!isEnd()){
            analysis();
        }

    }

    public List<List<WordModel>> getSentence(){
        return this.sentence;
    }

}

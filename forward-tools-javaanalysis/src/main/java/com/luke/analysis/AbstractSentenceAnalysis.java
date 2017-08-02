package com.luke.analysis;

import com.luke.model.WordModel;

import java.util.List;

/**
 * Created by yangf on 2017/8/1/0001.
 */
public abstract class AbstractSentenceAnalysis {
    protected List<WordModel> source;
    private Integer index = -1;

    public AbstractSentenceAnalysis(String path) {
        source = new AnalysisWord(path).getWordModels();
        analysis();
    }

    protected abstract void analysis();
    protected abstract List<List<WordModel>> getSentence();

    public WordModel next() {
        if (index >= source.size() - 1) {
            return null;
        }
        return source.get(++index);
    }

    public WordModel next(int index) {
        int step = (this.index == -1 ? 0 : this.index) + index;
        if (step < 0 || step >= source.size()) {
            return null;
        }
        return source.get(step);
    }

    public boolean isEnd(){
        return index == source.size() - 1;
    }

}

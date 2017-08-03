package com.luke.analysis;

import com.luke.analysis.traverse.TraverseSource;
import com.luke.analysis.traverse.impl.TraverseList;
import com.luke.enums.GSType;
import com.luke.model.WordModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yangf on 2017/8/2/0002.
 */
public class AnalysisSentence {
    private List<List<WordModel>> sentence;
    private TraverseSource<WordModel> traverse;
    private Integer index = -1;

    public AnalysisSentence(String path) {
        sentence = new ArrayList<>();
        traverse = new TraverseList(new AnalysisWord(path).getWordModels());
        analysis();
    }

    private void analysis() {
        List<WordModel> wordModels = new ArrayList<>();
        WordModel wordModel = traverse.next();
        //如果是回车就重新获取下一个
        if (wordModel.getType() == GSType.RXG) {
            analysis();
            return;
        }

        wordModels.add(wordModel);

        if (wordModel.getWord().startsWith("@")) {
            Integer step = -10;
            while (step != 0) {
                if (step == -10) {
                    step = 0;
                }
                if (traverse.next(1).getType() == GSType.LX) {
                    ++step;
                }
                if (traverse.next(1).getType() == GSType.RX) {
                    --step;
                }
                wordModels.add(traverse.next());
            }

        } else if (!GSType.isAloneLine(wordModel.getType())) {
            while (traverse.next(0).getType() != GSType.FH && !GSType.isAloneLine(traverse.next(1).getType())) {
                WordModel nwm = traverse.next();
                if (nwm.getType() != GSType.RXG) {
                    wordModels.add(nwm);
                }
            }
        }
        sentence.add(wordModels);

        if (!traverse.isArrayIndexOut()) {
            analysis();
        }

    }

    public List<List<WordModel>> getSentence() {
        return this.sentence;
    }


}

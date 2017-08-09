package com.luke.analysis;

import com.luke.analysis.word.AnalysisWord;

/**
 * Created by yangf on 2017/8/2/0002.
 */
public class AnalysisWordTest {

    public static void main(String[] args) {
        AnalysisWord analysisWord = new AnalysisWord(TraverseSourceTest.PATH);
        System.out.println(analysisWord.getWordModels());
    }
}

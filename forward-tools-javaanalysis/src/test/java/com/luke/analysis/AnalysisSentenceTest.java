package com.luke.analysis;

import com.luke.analysis.classes.AnalysisSentence;

/**
 * Created by yangf on 2017/8/2/0002.
 */
public class AnalysisSentenceTest {

    public static void main(String[] args) {
        AnalysisSentence analysisSentence = new AnalysisSentence(TraverseSourceTest.PATH);
        System.out.println(analysisSentence.getSentence());
    }
}

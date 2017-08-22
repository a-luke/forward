package com.luke.analysis;

import com.luke.analysis.word.AnalysisWord;

/**
 * Created by yangf on 2017/8/2/0002.
 */
public class AnalysisWordTest {


    private static final String PATH = "D:\\WorkSpace\\java\\fbicrawler\\fbicrawler-core\\src\\main\\java\\com\\firstbrave\\crawler\\download\\DownloadWorkerResult.java";

    public static void main(String[] args) {
        AnalysisWord analysisWord = new AnalysisWord(PATH);
        System.out.println(analysisWord.getWordModels());
    }
}

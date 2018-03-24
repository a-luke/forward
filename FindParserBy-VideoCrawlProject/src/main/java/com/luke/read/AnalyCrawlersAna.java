package com.luke.read;

import com.luke.util.FileHandle;

import java.util.List;

public class AnalyCrawlersAna {

    private  AnalysisWord analysisWord;


    public AnalyCrawlersAna(String path){
        this.analysisWord = new AnalysisWord(path);
    }


    public static void main(String[] args) {
        AnalyCrawlersAna analyCrawlersAna = new AnalyCrawlersAna(FileHandle.SOURCE_PATH);
        System.out.println(analyCrawlersAna.analysisWord);
    }
}

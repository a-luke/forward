package com.luke.read;

import com.luke.model.WordModel;
import com.luke.util.CollectionUtils;
import com.luke.util.FileHandle;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AnalysisKhNeiTest {

    private AnalysisKhNei analysisKhNei;

    public AnalysisKhNeiTest() {
        analysisKhNei = new AnalysisKhNei(FileHandle.SOURCE_PATH);
    }

    @Test
    public void sout() {
        analysisKhNei.analysis();
        Set<String> set = new HashSet<>();

        List<List<WordModel>> containsDoit = new ArrayList<>();
        List<List<WordModel>> containsAnaly = new ArrayList<>();
        List<List<WordModel>> containsProcess = new ArrayList<>();
        List<List<WordModel>> containsDoitAndProcess = new ArrayList<>();
        List<List<WordModel>> notContainsDoit = new ArrayList<>();


        for (List<WordModel> wordModels : analysisKhNei.sentenceModel) {
            String str = CollectionUtils.joinWords(wordModels);
            if (str.contains(".doIt(")) {
                String subStr = str.substring(0, str.indexOf(".doIt("));
                if(subStr.contains("MusicProcessor") || subStr.contains("VideoProcessor")){
                    containsDoitAndProcess.add(wordModels);
                }else {
                    containsDoit.add(wordModels);
                }
            } else if(str.contains("::new") || str.contains("new An")){
                if(str.contains("MusicProcessor") || str.contains("VideoProcessor")){
                    containsProcess.add(wordModels);
                }else {
                    containsAnaly.add(wordModels);
                }
            }else {
                notContainsDoit.add(wordModels);
            }

            set.add(wordModels.get(0).getWord());
        }

        System.out.println(set);
        System.out.println(containsDoit);
    }
}
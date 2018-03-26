package com.luke.read;

import com.luke.enums.GSType;
import com.luke.enums.Platform;
import com.luke.model.ClassInfo;
import com.luke.model.MethodChunk;
import com.luke.model.WordModel;
import com.luke.util.CollectionUtils;
import com.luke.util.FileHandle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AnalysisCrawlersAna {

    /**
     * 包含doit方法的块
      */
    public List<List<WordModel>> containsDoit = new ArrayList<>();

    /**
     * 不包含doit，但是包含analy
     */
    public List<List<WordModel>> containsAnaly = new ArrayList<>();

    /**
     * 包含process的
     */
    public List<List<WordModel>> containsProcess = new ArrayList<>();

    /**
     * 既包含doit又包含process
     */
    public List<List<WordModel>> containsDoitAndProcess = new ArrayList<>();

    /**
     * 什么都没有的
     */
    public List<List<WordModel>> notContainsDoit = new ArrayList<>();

    public List<MethodChunk> methodChunks = new ArrayList<>();


    public static void main(String[] args) {
        AnalysisCrawlersAna analysisCrawlersAna = new AnalysisCrawlersAna();
        analysisCrawlersAna.analyDoit();

        List<ClassInfo> allInfo = new ArrayList<>();
        Set<ClassInfo> allSet = new HashSet<>();
        List<ClassInfo> unKnownType = new ArrayList<>();
        List<ClassInfo> unKnownPlatform = new ArrayList<>();

        for(MethodChunk methodChunk: analysisCrawlersAna.methodChunks){
            allInfo.addAll(methodChunk.getClassInfos());
            allSet.addAll(methodChunk.getClassInfos());

            for(ClassInfo classInfo: methodChunk.getClassInfos()){
                if(classInfo.getType() == 3){
                    unKnownType.add(classInfo);
                }

                if(classInfo.getPlatform() == Platform.UNKNOW){
                    unKnownPlatform.add(classInfo);
                }
            }
        }

        System.out.println(analysisCrawlersAna.methodChunks);
    }





    /**
     * 解析Doit方法的内容
     */
    public void analyDoit(){
        classify();
        for(List<WordModel> wordModels: containsDoit){
            MethodChunk methodChunk = new MethodChunk();
            methodChunks.add(methodChunk);
            int flag = 0;
            for(WordModel wordModel: wordModels){

                if("doIt".equals(wordModel.getWord())){
                    flag = 1;
                }

                switch (flag){
                    case 0:
                        methodChunk.addPre(wordModel);
                        break;
                    case 1:
                        methodChunk.addDoitPre(wordModel);
                        break;
                    case 2:
                        methodChunk.addDoit(wordModel);
                        break;
                }

                if(flag == 1 && GSType.LD == wordModel.getType()){
                    flag = 2;
                }
            }
            methodChunk.getDoitContent();
            methodChunk.extract();
        }

        for(List<WordModel> wordModels: containsAnaly){
            MethodChunk methodChunk = new MethodChunk();
            methodChunks.add(methodChunk);
            methodChunk.addAllPre(wordModels);
            methodChunk.setDoitContent(Collections.EMPTY_LIST);
            methodChunk.extract();
        }

    }


    /**
     * 将解析的数据分类
     */
    public void classify(){
        AnalysisKhNei analysisKhNei = new AnalysisKhNei(FileHandle.SOURCE_PATH);
        analysisKhNei.analysis();

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
        }
    }

}

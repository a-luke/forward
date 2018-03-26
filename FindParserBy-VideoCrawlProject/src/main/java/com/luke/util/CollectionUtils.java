package com.luke.util;

import com.luke.model.WordModel;

import java.util.List;

public class CollectionUtils {

    public static String joinWords(List<WordModel> wordModelList){
        StringBuilder stringBuilder = new StringBuilder();
        for(WordModel wordModel: wordModelList){
            stringBuilder.append(wordModel.getWord());
        }
        return stringBuilder.toString();
    }
}

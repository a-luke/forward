package com.luke.read;

import com.luke.enums.GSType;
import com.luke.model.WordModel;
import com.luke.traverse.TraverseSource;
import com.luke.traverse.impl.TraverseList;
import com.luke.util.CollectionUtils;
import com.luke.util.FileHandle;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AnalysisKhNei {

    private AnalysisWord analysisWord;

    private TraverseSource<WordModel> traverseSource;
    public List<List<WordModel>> sentenceModel = new ArrayList<>();
    private List<WordModel> note = new ArrayList<>();


    public AnalysisKhNei(List<WordModel> seed) {
        traverseSource = new TraverseList(seed);
    }

    public AnalysisKhNei(String path) {
        this.analysisWord = new AnalysisWord(path);
        traverseSource = new TraverseList(analysisWord.getWordModels());
    }

    Map<String, Integer> counterMap = new HashMap<String, Integer>() {{
        put(xiao, 0);
        put(zhong, 0);
        put(da, 0);
        put(jian, 0);
    }};

    public void analysis() {
        List<WordModel> split = new ArrayList<>();
        traverse(split);
    }

    public void traverse(List<WordModel> split) {
        WordModel wordModel = traverseSource.next();

        if (wordModel == null) {
            return;
        }

        Pattern pattern = Pattern.compile("(defSrcDR1|defSrcDDirect|defSrcDX|defSrcD|defSrcDC)");

        if(!wordModel.getWord().startsWith("//") && !wordModel.getWord().startsWith("/*")){
            split.add(wordModel);
        }else {
            Matcher m = pattern.matcher(wordModel.getWord());
            if(m.find()){
                note.add(wordModel);
            }
        }
        if (isKhEnd(wordModel) && isKhAllEnd()) {
            if(GSType.FH.equals(traverseSource.next(1).getType())){
                split.add(traverseSource.next());
            }
            sentenceModel.add(split);
            analysis();
        } else {
            traverse(split);
        }
    }


    private static String xiao = "xiao";
    private static String zhong = "zhong";
    private static String da = "da";
    private static String jian = "jian";

    public boolean isKhAllEnd() {
        Integer zero = new Integer(0);
        return zero.equals(counterMap.get(xiao)) && zero.equals(counterMap.get(zhong)) && zero.equals(counterMap.get(da)) && zero.equals(counterMap.get(jian));
    }

    public boolean isKhEnd(WordModel wordModel) {
        WordModel last = traverseSource.next(-1);
        WordModel next = traverseSource.next(1);

        if (GSType.LX.equals(wordModel.getType())) {
            counterMap.put(xiao, counterMap.get(xiao) + 1);
        } else if (GSType.RX.equals(wordModel.getType())) {
            counterMap.put(xiao, counterMap.get(xiao) - 1);
        } else if (GSType.LZ.equals(wordModel.getType())) {
            counterMap.put(zhong, counterMap.get(zhong) + 1);
        } else if (GSType.LZ.equals(wordModel.getType())) {
            counterMap.put(zhong, counterMap.get(zhong) - 1);
        } else if (GSType.LD.equals(wordModel.getType())) {
            counterMap.put(da, counterMap.get(da) + 1);
        } else if (GSType.RD.equals(wordModel.getType())) {
            counterMap.put(da, counterMap.get(da) - 1);
        } else if (GSType.LJK.equals(wordModel.getType())) {
            counterMap.put(jian, counterMap.get(jian) + 1);
        } else if (GSType.RJK.equals(wordModel.getType()) && !GSType.ZDG.equals(last.getType())) {
            counterMap.put(jian, counterMap.get(jian) - 1);
        }

        if (GSType.isRKH(wordModel.getType())) {
            Integer conter = getConter(wordModel.getType());
            if (new Integer(0).equals(conter)) {
                if (GSType.RX.equals(wordModel.getType()) && GSType.DIAN.equals(next.getType())) {
                    return false;
                }
                return true;
            }
        }
        return false;
    }


    public Integer getConter(GSType type) {
        if (GSType.LX.equals(type) || GSType.RX == type) {
            return counterMap.get(xiao);
        } else if (GSType.LZ.equals(type) || GSType.RZ == type) {
            return counterMap.get(zhong);
        } else if (GSType.LD.equals(type) || GSType.RD == type) {
            return counterMap.get(da);
        } else if (GSType.LJK.equals(type) || GSType.RJK == type) {
            return counterMap.get(jian);
        }
        return null;
    }

}

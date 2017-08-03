package com.luke.analysis;

import com.luke.analysis.traverse.TraverseSource;
import com.luke.analysis.traverse.impl.TraverseFileLineStr;
import com.luke.enums.*;
import com.luke.model.WordModel;
import com.luke.utils.FileHandle;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yangf on 2017/8/1/0001.
 */
public class AnalysisWord {

    /**
     * 一个类中所有的单词
     */
    private List<WordModel> wordModels = new ArrayList<>();

    /**
     * 遍历数据源对象
     */
    private TraverseSource<Character> traverse;

    public AnalysisWord(String path) {
        try {
            traverse = new TraverseFileLineStr(FileHandle.readToStr(path));
            analysis();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 分析字符提炼单词
     */
    public void analysis() {
        StringBuilder sb = new StringBuilder();
        Character c = traverse.next();
        if (c == ' ') {
            analysis();
            return;
        }
        sb.append(c);
        GSType type = GSType.has(c);
        //注释类型
        if (type == GSType.LXG && (traverse.next(1) == '/' || traverse.next(1) == '*')) {
            joinNote(sb);
        } else if (type == GSType.SY) {
            //字符串类型
            joinStr(sb);
        } else if (type == GSType.WD) {
            //普通单词
            joinWord(sb);
        }
        String str = sb.toString();
        if (!"".equals(str.replaceAll("( |\t)*", ""))) {
            WordModel wordModel = new WordModel().setType(type);

            //设置单词的类型,并添加到list中
            wordModels.add(wordModel.setWord(str).setWdType(KeyWordType.has(str)));
        }
        if (traverse.next(1) != null) {
            analysis();
        }
    }

    /**
     * 组合注释
     */
    public void joinNote(StringBuilder sb) {
        if (traverse.next(1) == '*') {
            while (!(traverse.next(-1) == '*' && traverse.next(0) == '/')) {
                sb.append(traverse.next());
            }
        } else if (traverse.next(1) == '/') {
            while (traverse.next(0) != '\n') {
                sb.append(traverse.next());
            }
        }
    }

    /**
     * 组合普通的单词
     */
    public void joinWord(StringBuilder sb) {
        Character c = 'a';
        while ((c = traverse.next(1)) != ' ' && GSType.has(c) == GSType.WD) {
            sb.append(traverse.next());
        }
    }

    /**
     * 字符串算作一个单词
     */
    public void joinStr(StringBuilder sb) {
        Character c = 'a';
        while ((c = traverse.next()) != '\"' || traverse.next(-1) == '\\') {
            sb.append(c);
        }
        sb.append(c);
    }

    public List<WordModel> getWordModels() {
        return wordModels;
    }

    public void setWordModels(List<WordModel> wordModels) {
        this.wordModels = wordModels;
    }
}

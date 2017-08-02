package com.luke.analysis;

import com.luke.enums.AccessType;
import com.luke.enums.ClassType;
import com.luke.enums.GSType;
import com.luke.enums.ModifierType;
import com.luke.model.WordModel;

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
     * 数据源对象
     */
    private TraverseSource source;

    public AnalysisWord(String path) {
        source = new TraverseSource(path);
        analysis();
    }


    /**
     * 分析字符提炼单词
     */
    public void analysis() {
        StringBuilder sb = new StringBuilder();
        Character c = source.nextChar();
        if(c == ' '){
            analysis();
            return;
        }
        sb.append(c);
        GSType type = GSType.has(c);
        //注释类型
        if (type == GSType.LXG && (source.nextChar(1) == '/' || source.nextChar(1) == '*')) {
            joinNote(sb);
        } else if (type == GSType.SY) {
            //字符串类型
            joinStr(sb);
        } else if (type == GSType.WD) {
            //普通单词
            joinWord(sb);
        }
        String str = sb.toString();
        if(!"".equals(str.replaceAll("( |\t)*", ""))){
            WordModel wordModel = new WordModel().setType(type);
            AccessType accessType = AccessType.has(str);
            ClassType classType = null;
            ModifierType modifierType = null;
            //设置单词的类型
            wordModel.setWdType(accessType == null ?
                ((classType = ClassType.has(str)) == null ?
                    ((modifierType = ModifierType.has(str)) == null ?
                        null :
                        modifierType) :
                    classType) :
                accessType);
            wordModels.add(wordModel.setWord(str));
        }
        if(source.nextChar(1) != null){
            analysis();
        }
    }

    /**
     * 组合注释
     */
    public void joinNote(StringBuilder sb) {
        if (source.nextChar(1) == '*') {
            while (!(source.nextChar(-1) == '*' && source.nextChar(0) == '/')) {
                sb.append(source.nextChar());
            }
        } else if (source.nextChar(1) == '/') {
            while (source.nextChar(0) != '\n') {
                sb.append(source.nextChar());
            }
        }
    }

    /**
     * 组合普通的单词
     */
    public void joinWord(StringBuilder sb) {
        Character c = 'a';
        while ((c = source.nextChar(1)) != ' ' && GSType.has(c) == GSType.WD) {
            sb.append(source.nextChar());
        }
    }

    /**
     * 字符串算作一个单词
     */
    public void joinStr(StringBuilder sb) {
        while (source.nextChar(0) != '\"' && source.nextChar(-1) != '\\') {
            sb.append(source.nextChar());
        }
    }

    public List<WordModel> getWordModels() {
        return wordModels;
    }

    public void setWordModels(List<WordModel> wordModels) {
        this.wordModels = wordModels;
    }
}

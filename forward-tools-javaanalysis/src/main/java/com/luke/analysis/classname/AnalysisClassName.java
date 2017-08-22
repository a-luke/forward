package com.luke.analysis.classname;

import com.luke.analysis.word.AnalysisWord;
import com.luke.enums.KeyWordType;
import com.luke.model.WordModel;
import com.luke.model.java.ClassNames;
import com.luke.traverse.TraverseSource;
import com.luke.traverse.impl.TraverseList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yangf on 2017/8/9/0009.
 */
public class AnalysisClassName {
    /**
     * 遍历器
     */
    private TraverseSource<WordModel> traverse = null;

    public AnalysisClassName(String path) {
        //加载要遍历的内容
        traverse = new TraverseList(new AnalysisWord(path).getWordModels());
    }

    public ClassNames getClassName() {
        WordModel wordModel = null;
        ClassNames classNames = new ClassNames();
        while ((wordModel = traverse.next()) != null) {
            if (KeyWordType.isClass(KeyWordType.has(wordModel.getWord()))) {
                classNames.addClassNames(traverse.next().getWord());
            } else if (KeyWordType.PACKAGE.getValue().equals(wordModel.getWord())) {
                classNames.setPackages(traverse.next().getWord());
            }
        }
        return classNames;
    }

    public List<String> generateSql() {
        ClassNames classNames = getClassName();
        List<String> result = new ArrayList<>();
        for (String clsNm : classNames.getClassNames()) {
            StringBuilder sb = new StringBuilder();
            sb.append("INSERT INTO usertest(packages, classname) values('");
            sb.append(classNames.getPackages());
            sb.append("', '");
            sb.append(clsNm);
            sb.append("')");
            result.add(sb.toString());
        }
        return result;
    }

}

package com.luke.model;

import com.luke.enums.GSType;
import com.luke.read.AnalysisKhNei;

import java.util.ArrayList;
import java.util.List;

public class MethodChunk {
    private List<WordModel> pre;

    /**
     * doit内容的所单词
     */
    private List<WordModel> doit;

    /**
     * doit调用的部分
     */
    private List<WordModel> doitPre;


    /**
     * doit内容解析后的的句子
     */
    private List<List<WordModel>> doitContent;


    private List<ClassInfo> classInfos;

    public List<WordModel> getPre() {
        return pre;
    }

    public void setPre(List<WordModel> pre) {
        this.pre = pre;
    }

    public void addPre(WordModel wordModel) {
        if (this.pre == null) {
            this.pre = new ArrayList<>();
        }
        pre.add(wordModel);
    }

    public void addAllPre(List<WordModel> wordModelList) {
        if (this.pre == null) {
            this.pre = new ArrayList<>();
        }
        pre.addAll(wordModelList);
    }

    public List<WordModel> getDoit() {
        return doit;
    }

    public void setDoit(List<WordModel> doit) {
        this.doit = doit;
    }

    public void addDoit(WordModel wordModel) {
        if (this.doit == null) {
            this.doit = new ArrayList<>();
        }
        doit.add(wordModel);
    }

    public List<WordModel> getDoitPre() {
        return doitPre;
    }

    public void setDoitPre(List<WordModel> doitPre) {
        this.doitPre = doitPre;
    }

    public void addDoitPre(WordModel wordModel) {
        if (this.doitPre == null) {
            this.doitPre = new ArrayList<>();
        }
        doitPre.add(wordModel);
    }

    public List<List<WordModel>> getDoitContent() {
        if (doitContent == null) {
            AnalysisKhNei analysisKhNei = new AnalysisKhNei(doit);
            analysisKhNei.analysis();
            doitContent = analysisKhNei.sentenceModel;
        }
        return doitContent;
    }

    public void setDoitContent(List<List<WordModel>> doitContent) {
        this.doitContent = doitContent;
    }

    public List<ClassInfo> getClassInfos() {
        return classInfos;
    }

    public void setClassInfos(List<ClassInfo> classInfos) {
        this.classInfos = classInfos;
    }

    public void extract() {
        if (classInfos == null) {
            classInfos = new ArrayList<>();
        }

        extractRow(this.pre);
        for (List<WordModel> row : doitContent) {
            extractRow(row);
        }
    }

    private void extractRow(List<WordModel> row) {

        ClassInfo classInfo = new ClassInfo();
        classInfo.setWordModels(row);

        for (int i = 0, len = row.size(); i < len; i++) {
            WordModel now = row.get(i);
            if ("new".equals(now.getWord())) {
                if (row.get(i - 1).getType() == GSType.MH && row.get(i - 4).getType() == GSType.RJK) {
                    classInfo.setClassName(row.get(i - 3).getWord());
                } else if (row.get(i - 1).getType() == GSType.RJK) {
                    classInfo.setClassName(row.get(i + 1).getWord());
                }
            }

            if ("setBad".equals(now.getWord()) && row.get(i - 1).getType() == GSType.DIAN) {
                classInfo.setClassName(null);
            }

            if (now.getType() == GSType.SY) {
                classInfo.addStrList(now.getWord());
            }
        }
        if (classInfo.getClassName() != null) {
            classInfo.getPlatform();
            classInfo.getType();
            classInfos.add(classInfo);
        }

    }
}

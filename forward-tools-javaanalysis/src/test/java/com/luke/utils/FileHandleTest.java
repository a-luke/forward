package com.luke.utils;

import com.luke.analysis.TraverseSourceTest;
import com.luke.analysis.classname.AnalysisClassName;
import com.luke.mysql.MysqlOprate;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yangf on 2017/8/9/0009.
 */
public class FileHandleTest {

    public static final String PATH = "D:\\WorkSpace\\java\\fbicrawler\\fbicrawler-core\\src\\main\\java\\com\\firstbrave\\crawler";


    @Test
    public void readToStr() throws Exception {
        System.out.println(FileHandle.readToStr(TraverseSourceTest.PATH));
    }


    @Test
    public void traversePath() throws Exception {
        List<String> paths = new ArrayList<>();
        FileHandle.traversePath(paths, PATH);
        for(String path: paths){
            System.out.println(path);
            try {
                MysqlOprate.execute(new AnalysisClassName(path).generateSql());
            }catch (Throwable e){
                e.printStackTrace();
            }
        }

    }

}
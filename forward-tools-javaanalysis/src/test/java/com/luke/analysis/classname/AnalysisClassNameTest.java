package com.luke.analysis.classname;

import com.luke.analysis.TraverseSourceTest;
import org.junit.Test;

/**
 * Created by yangf on 2017/8/9/0009.
 */
public class AnalysisClassNameTest {
    @Test
    public void classNames() throws Exception {
        AnalysisClassName analysisClassName = new AnalysisClassName(TraverseSourceTest.PATH);
        System.out.println(analysisClassName.getClassName());
    }

}
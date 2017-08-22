package com.luke.mysql;

import com.luke.analysis.TraverseSourceTest;
import com.luke.analysis.classname.AnalysisClassName;
import org.junit.Test;

/**
 * Created by yangf on 2017/8/9/0009.
 */
public class MysqlOprateTest {
    @Test
    public void execute() throws Exception {
        MysqlOprate.execute(new AnalysisClassName(TraverseSourceTest.PATH).generateSql());
    }
}
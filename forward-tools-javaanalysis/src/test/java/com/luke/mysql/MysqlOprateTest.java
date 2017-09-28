package com.luke.mysql;

import org.junit.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yangf on 2017/8/9/0009.
 */
public class MysqlOprateTest {
    @Test
    public void execute() throws Exception {
        List<String> sqls = new ArrayList<>();
        sqls.add("INSERT INTO fbi.fbc_littlesite_play_source (url, url_md5, play_url, play_url_md5, source_type, create_time, update_time) VALUES ('123', " 
            + "'123', '123', '123', NULL, '2017-09-01 13:52:06', '2017-09-01 13:52:06')");
        sqls.add("INSERT INTO fbi.fbc_littlesite_play_source (url, url_md5, play_url, play_url_md5, source_type, create_time, update_time) VALUES ('123', "
            + "'123', '123', '123', NULL, '2017-09-01 13:52:06', '2017-09-01 13:52:06')");

//        MysqlOprate.execute(new AnalysisClassName(TraverseSourceTest.PATH).generateSql());
        try {
            MysqlOprate.execute(sqls);
        }catch (SQLException e){
            System.out.println(e.getCause().getCause() == null);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public Throwable getCause(Throwable e, Class<? extends Throwable> target){
        if(e.getClass().equals(target)){
            return e;
        }
        if(e != null){
            return getCause(e.getCause(), target);
        }
        return null;
    }
}
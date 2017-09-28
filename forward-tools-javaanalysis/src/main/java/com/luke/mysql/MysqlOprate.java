package com.luke.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.List;

/**
 * Created by yangf on 2017/8/9/0009.
 */
public class MysqlOprate {

    private static final String url = "jdbc:mysql://localhost:3306/fbi?" + "user=root&password=123qwe@"
        + "&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";

    public static void execute(List<String> sqls) throws Exception {
        Class.forName("com.mysql.jdbc.Driver");
        Connection conn = DriverManager.getConnection(url);
        Statement statement = conn.createStatement();
        for (int i = 0, len = sqls.size(); i < len; i++) {
            String sql = sqls.get(i);
            if (sql.trim().length() == 0) {
                continue;
            }
            statement.addBatch(sql);
            if (i % 20 == 0 || i / 20 == 0) {
                statement.executeBatch();
            }
        }
        conn.close();
    }
}

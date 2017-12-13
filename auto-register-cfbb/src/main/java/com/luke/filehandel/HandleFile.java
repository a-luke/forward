package com.luke.filehandel;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by yangf on 2017/9/29/0029.
 */
public class HandleFile {

    private PrintWriter pw;

    public static final ConcurrentHashMap<String, PrintWriter> CONCURRENT_HASH_MAP = new ConcurrentHashMap();
    public static final Object lock = new Object();

    public HandleFile(String fileName) {
        File file = new File(fileName);
        if (!file.exists()) {
            file.getParentFile().mkdirs();
        }
        try {
            synchronized (lock) {
                pw = CONCURRENT_HASH_MAP.get(fileName);
                if (pw == null) {
                    pw = new PrintWriter(new FileWriter(file, true));
                    CONCURRENT_HASH_MAP.put(fileName, pw);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void write(String content) {
        PrintWriter pw = this.pw;
        try {
            pw.print(getNow() + content);
            pw.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void writeLine(String content) {
        PrintWriter pw = this.pw;
        try {
            pw.println(getNow() + content);
            pw.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void writeException(Throwable ex) {
        StringWriter sw = new StringWriter();
        PrintWriter printWriter = new PrintWriter(sw);
        ex.printStackTrace(printWriter);
        PrintWriter pw = this.pw;
        try {
            pw.println(getNow() + sw.toString());
            pw.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getNow() {
        Date now = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(now);
        return "<-" + dateString + "-> ";
    }

}

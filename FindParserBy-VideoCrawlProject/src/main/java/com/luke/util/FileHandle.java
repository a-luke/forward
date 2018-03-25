package com.luke.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yangf on 2017/8/1/0001.
 */
public class FileHandle {
    public static String PATH = "";
    public static String SOURCE_PATH = "D:\\Workspace\\Java\\gitHub\\forward\\FindParserBy-VideoCrawlProject\\src\\main\\resources\\crawlersAna-data.java";

    static {
        PATH = FileHandle.class.getResource("").getPath();
        String pro = "forward-tools-javaanalysis/";
        PATH = PATH.substring(0, PATH.indexOf(pro) + pro.length()) + "resource/";
    }

    public static List<String> readToStr(String path) throws Exception {
        List<String> result = new ArrayList<String>();
        File file = new File(path);
        FileInputStream fis = new FileInputStream(file);
        InputStreamReader isr = new InputStreamReader(fis);
        BufferedReader bufferedReader = new BufferedReader(isr);
        String line = "";
        while ((line = bufferedReader.readLine()) != null) {
            if (!"".equals(line.trim())) {
                result.add(line);
            }
        }
        isr.close();
        fis.close();
        return result;
    }

    public static void traversePath(List<String> paths, String path) {
        File file = new File(path);
        if (file.isDirectory()) {
            String[] filePaths = file.list();
            for (String filePath : filePaths) {
                traversePath(paths, path + File.separator + filePath);
            }
        } else if (file.isFile()) {
            String absolutePath = file.getAbsolutePath();
            paths.add(absolutePath);
        }
    }

    public static void main(String[] args) throws Exception {
        System.out.println(readToStr("D:\\WorkSpace\\java\\mydemo\\src\\main\\java\\com\\luke\\model\\ClassModel.java"));
//        System.out.println(PATH);
    }
}
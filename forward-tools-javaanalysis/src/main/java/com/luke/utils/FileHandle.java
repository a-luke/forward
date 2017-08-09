package com.luke.utils;

import com.luke.model.FileModel;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yangf on 2017/8/1/0001.
 */
public class FileHandle {
    public static String PATH = "";

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

    public static void writeJava(FileModel fileModel) throws Exception {
        String filePath = PATH + fileModel.getPath() + File.separator;
        File file = new File(filePath);
        if (!file.exists()) {
            file.mkdirs();
        }
        String fileName = filePath + fileModel.getName() + ".java";
        FileOutputStream fos = new FileOutputStream(fileName);
        PrintStream ps = new PrintStream(fos);
        ps.print(fileModel.getContent());
        ps.close();
        fos.close();
    }

    public static void main(String[] args) throws Exception {
        //        System.out.println(readToStr("D:\\WorkSpace\\java\\mydemo\\src\\main\\java\\com\\luke\\model\\ClassModel.java"));
        System.out.println(PATH);
    }
}
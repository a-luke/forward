package com.luke.utils;

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
    public static List<String> readToStr(String path) throws Exception {
        List<String> result = new ArrayList<String>();
        File file = new File(path);
        FileInputStream fis = new FileInputStream(file);
        InputStreamReader isr = new InputStreamReader(fis);
        BufferedReader bufferedReader = new BufferedReader(isr);
        String line = "";
        while ((line = bufferedReader.readLine()) != null) {
            if(!"".equals(line.trim())){
                result.add(line);
            }
        }
        return result;
    }




    public static void main(String[] args) throws Exception {
        System.out.println(readToStr("D:\\WorkSpace\\java\\mydemo\\src\\main\\java\\com\\luke\\model\\ClassModel.java"));
    }

}

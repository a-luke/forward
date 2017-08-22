package com.luke.analysis;

import com.luke.traverse.TraverseSource;
import com.luke.traverse.impl.TraverseFileLineStr;
import com.luke.utils.FileHandle;
import org.junit.Test;

/**
 * Created by yangf on 2017/8/2/0002.
 */
public class TraverseSourceTest {
    //    public static final String PATH = "D:\\TestC.java";
    public static final String PATH = "D:\\WorkSpace\\java\\fbicrawler\\fbicrawler-core\\src\\main\\java\\com\\firstbrave\\crawler\\download\\DownloadWorkerResult.java";

    @Test
    public void traverse() throws Exception {
        TraverseSource<Character> traverseSource = new TraverseFileLineStr(FileHandle.readToStr(PATH));
        //        System.out.println(traverseSource.next());
        Character c = null;
        while ((c = traverseSource.next()) != null) {
            System.out.print(c);
        }
    }


    @Test
    public void traverse1() throws Exception {
        TraverseSource<Character> traverseSource = new TraverseFileLineStr(FileHandle.readToStr(PATH));
        //        System.out.println(traverseSource.next());
        int i = 0;
        Character c = null;
        while ((c = traverseSource.next(i++)) != null) {
            System.out.print(c);
        }
    }
}

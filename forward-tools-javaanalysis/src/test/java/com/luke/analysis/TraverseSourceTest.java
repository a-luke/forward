package com.luke.analysis;

import com.luke.analysis.traverse.TraverseSource;
import com.luke.analysis.traverse.impl.TraverseFileLineStr;
import com.luke.utils.FileHandle;

/**
 * Created by yangf on 2017/8/2/0002.
 */
public class TraverseSourceTest {
//    public static final String PATH = "D:\\TestC.java";
    public static final String PATH = "D:\\workspaces\\VideoCrawlProject-project\\src\\com\\application\\util\\CommonUtil.java";

    public static void main(String[] args) throws Exception {
        new TraverseSourceTest().traverse();
    }

    public void traverse() throws Exception {
        TraverseSource traverseSource = new TraverseFileLineStr(FileHandle.readToStr(PATH));
//        System.out.println(traverseSource.next());
        System.out.print(traverseSource.next());
        System.out.print(traverseSource.next());
        System.out.print(traverseSource.next());
        System.out.print(traverseSource.next());
        System.out.print(traverseSource.next());
        System.out.print(traverseSource.next());
        System.out.print(traverseSource.next());
        System.out.print(traverseSource.next());
        System.out.print(traverseSource.next());
        System.out.print(traverseSource.next());
        System.out.print(traverseSource.next());
        System.out.print(traverseSource.next());
        System.out.print(traverseSource.next());
        System.out.print(traverseSource.next());
        System.out.print(traverseSource.next());
        System.out.print(traverseSource.next());
        System.out.print(traverseSource.next());
        System.out.print(traverseSource.next());
        System.out.print(traverseSource.next());
        System.out.print(traverseSource.next());
        System.out.print(traverseSource.next());
        System.out.print(traverseSource.next());
        System.out.print(traverseSource.next());
        System.out.print(traverseSource.next());
        System.out.print(traverseSource.next());
        System.out.print(traverseSource.next());
        System.out.print(traverseSource.next());
        System.out.print(traverseSource.next());
        System.out.print(traverseSource.next());
        System.out.print(traverseSource.next());
        System.out.print(traverseSource.next());
        System.out.print(traverseSource.next());
        System.out.print(traverseSource.next());
        System.out.print(traverseSource.next(0));
    }
}

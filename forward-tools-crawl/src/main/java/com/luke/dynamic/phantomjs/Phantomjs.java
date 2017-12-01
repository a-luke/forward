package com.luke.dynamic.phantomjs;

import java.io.*;

/**
 * Created by yangf on 2017/11/30/0030.
 */
public class Phantomjs {
    // 如果要更换运行环境，请注意exePath最后的phantom.exe需要更改。因为这个只能在window版本上运行。前面的路径名
    // 也需要和exePath里面的保持一致。否则无法调用
    private static String projectPath = Phantomjs.class.getResource("/").getPath();
    private static String jsPath = "D:\\WorkSpace\\gitHub\\forward\\forward-tools-crawl\\src\\main\\java\\com\\luke\\dynamic\\phantomjs\\js\\phantom.js";
    private static String exePath = "D:\\Tools\\phantomjs-2.1.1-windows\\bin\\phantomjs.exe";

    public static void main(String[] args) throws Exception {

        // 测试调用。传入url即可
        String html = getParseredHtml2("https://pianji.net/play/128814-1-1");
        System.out.println("html: " + html);

    }

    // 调用phantomjs程序，并传入js文件，并通过流拿回需要的数据。
    public static String getParseredHtml2(String url) throws IOException {
        Runtime rt = Runtime.getRuntime();
        String command = exePath + " " + jsPath + " " + url;
        Process p = rt.exec(command);
        InputStream is = p.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        StringBuffer sbf = new StringBuffer();
        String tmp = "";
        while ((tmp = br.readLine()) != null) {
            sbf.append(tmp);
        }
        return sbf.toString().replaceAll("^.*<!DOCTYPE", "<!DOCTYPE");
    }

}

package com.luke.jsoup;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;

import java.util.Map;

/**
 * Created by yangf on 2017/11/30/0030.
 */
public class JsoupTest {

    private static final String useAgent = "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.86 Safari/537.36";

    private static final String http = "http://www.luoqiu.com";

    @Test
    public void testJsoup() throws Exception {
        long now = System.currentTimeMillis();
        //        Document doc = Jsoup.connect("http://www.ttll.cc/dsj/dsj2/shixitianshiguoyu/bf-0-0.html")
        Connection connect = Jsoup.connect("http://www.luoqiu.com/read/10583/");
        connect.header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
        connect.header("Accept-Encoding", "gzip, deflate");
        connect.header("Accept-Language", "zh-CN,zh;q=0.8");
        connect.header("Cache-Control", "max-age=0");
        connect.header("Connection", "keep-alive");
        connect.header("Host", "www.luoqiu.com");
        connect.header("Upgrade-Insecure-Requests", "1");
        connect.header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.86 Safari/537.36");
//        connect.userAgent("Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.86 Safari/537.36");

//        Document doc = connect.userAgent(useAgent).timeout(3000).get();
//        Map<String, String> map = connect.response().cookies();
//        String cookie = mapToStr(map);
//        System.out.println(cookie);

        connect.header("Cookie", "");
        Document doc1 = connect.userAgent(useAgent).timeout(3000).get();

        System.out.println("\n\n=========================================================================================\n\n");
        System.out.println(doc1.toString());


        //        System.out.println("\n\n=========================================================================================\n\n");
        //        Map<String, String> resCoo = connect.response().cookies();
        //        Connection connect1 = Jsoup.connect("http://www.luoqiu.com/_Incapsula_Resource?SWJIYLWA=2977d8d74f63d7f8fedbea018b7a1d05");
        //        connect1.header("Cookie",mapToStr(resCoo)).ignoreContentType(true);
        //        Connection.Response response1 = connect1.userAgent(useAgent).timeout(3000).execute();
        //        System.out.println(response1.body());
        //
        //
        //
        //        System.out.println("\n\n=========================================================================================\n\n");
        //        Map<String, String> resCoo1 = connect.response().cookies();
        //        resCoo1.put("___utmvc","navigator%3Dtrue,navigator.vendor%3DGoogle%20Inc.,navigator.appName%3DNetscape,navigator.plugins.length%3D%3D0%3Dfalse,navigator
        // .platform%3DWin32,navigator.webdriver%3Dundefined,plugin_ext%3Ddll,plugin_ext%3Dno%20extention,ActiveXObject%3Dfalse,webkitURL%3Dtrue,_phantom%3Dfalse,
        // callPhantom%3Dfalse,chrome%3Dtrue,yandex%3Dfalse,opera%3Dfalse,opr%3Dfalse,safari%3Dfalse,awesomium%3Dfalse,puffinDevice%3Dfalse,__nightmare%3Dfalse,
        // _Selenium_IDE_Recorder%3Dfalse,document.__webdriver_script_fn%3Dfalse,document.%24cdc_asdjflasutopfhvcZLmcfl_%3Dfalse,process.version%3Dfalse,navigator
        // .cpuClass%3Dfalse,navigator.oscpu%3Dfalse,navigator.connection%3Dfalse,navigator.language%3D%3D'C'%3Dfalse,window.outerWidth%3D%3D0%3Dfalse,window
        // .outerHeight%3D%3D0%3Dfalse,window.WebGLRenderingContext%3Dtrue,document.documentMode%3Dundefined,eval.toString().length%3D33,digest=83568,
        // s=7185a89e8c85a07aa2b06c7f699e846ba7669a6e859f9fadaea9aa8a6b8fb0689a7fb0837a9c7375");
        //        Connection connect2 = Jsoup.connect("http://www.luoqiu.com/_Incapsula_Resource?SWKMTFSR=1&e=0.3198651694242416");
        //        connect2.header("Cookie",mapToStr(resCoo1));
        //        Connection.Response response2 = connect2.userAgent(useAgent).timeout(3000).execute();
        //        System.out.println(response2.body());
        //
        //        System.out.println("\n\n=========================================================================================\n\n");
        //        Map<String, String> resCoo2 = response2.cookies();
        //        Connection connect3 = Jsoup.connect("http://www.luoqiu.com/_Incapsula_Resource?SWHANEDL=7227782862862517179,12497486478673356992,10991958936485628083,172356");
        //        connect3.header("Cookie",mapToStr(resCoo2));
        //        Connection.Response response3 = connect3.userAgent(useAgent).timeout(3000).execute();
        //        System.out.println(response3.body());
        //
        //        System.out.println("\n\n=========================================================================================\n\n");
        //        Map<String, String> resCoo3 = response3.cookies();
        //        resCoo3.putAll(resCoo1);
        //        Connection connect4 = Jsoup.connect("http://www.luoqiu.com/read/10583/");
        //        connect4.header("Cookie",mapToStr(resCoo3));
        //        Document doc4 = connect4.userAgent(useAgent).timeout(3000).get();
        //        System.out.println(doc4.toString());

        System.out.println(System.currentTimeMillis() - now);
    }

    public String mapToStr(Map<String, String> map) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            stringBuilder.append(entry.getKey()).append("=");
            stringBuilder.append(entry.getValue()).append("; ");
        }
        stringBuilder.delete(stringBuilder.lastIndexOf(";"), stringBuilder.length());
        return stringBuilder.toString();
    }
}

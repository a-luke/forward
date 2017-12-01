package com.luke.jsoup;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;

/**
 * Created by yangf on 2017/11/30/0030.
 */
public class JsoupTest {

    @Test
    public void testJsoup() throws Exception {
        long now = System.currentTimeMillis();
        //        Document doc = Jsoup.connect("http://www.ttll.cc/dsj/dsj2/shixitianshiguoyu/bf-0-0.html")
        Document doc = Jsoup.connect("http://tv.dashijian.cc/nr/886555.html")
            .userAgent("Mozilla")
            .timeout(3000)
            .get();

        System.out.println(doc.toString());
        //        System.out.println(System.currentTimeMillis() - now);
    }
}

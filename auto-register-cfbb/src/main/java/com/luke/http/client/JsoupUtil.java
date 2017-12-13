package com.luke.http.client;

import com.luke.model.Proxy;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

/**
 * Created by yangf on 2017/12/13/0013.
 */
public class JsoupUtil {
    private final static String userAgent = "Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.86 Mobile Safari/537.36";

    public static String getText(String url) throws IOException {
        Document doc = Jsoup.connect(url).userAgent("Mozilla").timeout(3000).get();
        String content = doc.text();
        return content;
    }

    public static String mobileText(String url, String referrer) throws IOException {
        Proxy proxy = ProxyUtil.getProxy();
        Document doc = Jsoup.connect(url).proxy(proxy.getIp(), proxy.getPort()).referrer(referrer).userAgent(userAgent).timeout(3000).get();
        String content = doc.text();
        return content;
    }
}

package com.luke.dynamic.htmlunit;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebClientOptions;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.util.NameValuePair;

import java.io.IOException;
import java.util.List;

/**
 * 抓取动态加载内容
 */
public class Htmlunit {
    public static void main(String[] args) throws IOException, InterruptedException {
        WebClient webClient = new WebClient(BrowserVersion.CHROME);
        WebClientOptions option = webClient.getOptions();
        option.setJavaScriptEnabled(true);
        option.setCssEnabled(false);
        option.setRedirectEnabled(true);
        option.setThrowExceptionOnScriptError(false);
        option.setThrowExceptionOnFailingStatusCode(false);

        option.setUseInsecureSSL(true);
        option.setDownloadImages(false);
        option.setTimeout(30000);
        HtmlPage rootPage = webClient.getPage("http://www.luoqiu.com/read/10583/");

        List<NameValuePair> resResult = rootPage.getWebResponse().getResponseHeaders();
//        List<NameValuePair> resResult = webClient.req

        webClient.waitForBackgroundJavaScript(10000);
        HtmlElement element = rootPage.getDocumentElement();
//        DomNodeList nodeList = element.querySelectorAll("#vlist .thunder_url");
        webClient.close();
        String html = rootPage.asXml();
        System.out.println(rootPage.asXml());
    }
}
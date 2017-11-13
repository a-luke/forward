package com.luke.htmlunit;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import java.io.IOException;

/**
 * 抓取动态加载内容
 */
public class Crawler {
    public static void main(String[] args) throws IOException, InterruptedException {
        WebClient webClient = new WebClient(BrowserVersion.CHROME);
        webClient.getOptions().setJavaScriptEnabled(true);
        webClient.getOptions().setCssEnabled(false);
        webClient.getOptions().setRedirectEnabled(true);
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        webClient.getOptions().setTimeout(30000);
        HtmlPage rootPage = webClient.getPage("http://127.0.0.1/");
        webClient.waitForBackgroundJavaScript(10000);
        System.out.println(rootPage.asXml());
    }
}
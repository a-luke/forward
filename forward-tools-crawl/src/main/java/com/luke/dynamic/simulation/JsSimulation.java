package com.luke.dynamic.simulation;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.IOException;
import java.util.List;

/**
 * Created by yangf on 2017/9/11/0011.
 */
public class JsSimulation {

    public static void main(String args[]) throws IOException {
        System.getProperties().setProperty("webdriver.chrome.driver",
            "C:\\Program Files (x86)\\Google\\Chrome\\Application\\chrome.exe"); //这个参数就是【chrome驱动器的位置】
        WebDriver webDriver = new ChromeDriver();
        webDriver.get("http://www.88520.cc/view/107967.html");
        WebElement webElement = webDriver.findElement(By.id("ColumnContainer"));
        List<WebElement> listLink = webElement.findElements(By.className("thunder_url"));
        for (WebElement element : listLink) {
            System.out.println(element.getText());
        }
        System.out.println("chrome driver");
        // 关闭窗口，释放资源。
        webDriver.close();
    }




}
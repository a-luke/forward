package com.luke.concurrent;

import com.luke.classload.executeOrder.HelloBean;
import com.luke.listener.spring.application.EmailEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class EventTest {
    public static void main(String[] args) {  
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring.xml");

        HelloBean hello = (HelloBean) context.getBean("helloBean");
//        hello.setApplicationContext(context);
        EmailEvent event = new EmailEvent("hello","boylmx@163.com","this is a email text!");
        context.publishEvent(event);
        //System.out.println();  
    }  
} 
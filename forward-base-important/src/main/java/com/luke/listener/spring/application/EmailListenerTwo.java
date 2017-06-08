package com.luke.listener.spring.application;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * <p>Class:EmailListener</p>
 * <p>Description:</p>
 * @author luke
 */
@Component
public class EmailListenerTwo implements ApplicationListener {

    volatile String aa;

    @Override
    public void onApplicationEvent(ApplicationEvent  event) {
        if(event instanceof EmailEvent){
            EmailEvent emailEvent = (EmailEvent)event;
            emailEvent.print();
            System.out.println("test 2");
//            System.out.println("the source is:"+emailEvent.getSource());
//            System.out.println("the address is:"+emailEvent.address);
//            System.out.println("the email's context is:"+emailEvent.text);
        }

    }

}
package com.luke.listener.spring.application;

import org.springframework.context.ApplicationEvent;

/**
 * <p>Class:EmailListEvent</p>
 * <p>Description:</p>
 * @author luke
 */
public class EmailEvent extends ApplicationEvent {
    /**
     * <p>Description：</p>
     */
    private static final long serialVersionUID = 1L;
    public String address;
    public String text;

    public EmailEvent(Object source) {
        super(source);
    }

    public EmailEvent(Object source, String address, String text) {
        super(source);
        this.address = address;
        this.text = text;
    }

    public void print(){
        System.out.println("hello spring event!");
    }

}
package com.luke.concurrent.producter;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by yangf on 2017/6/8.
 */
public class ProducerConsumer {

    static Object lock = new Object();
    static AtomicInteger step = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {

        System.out.println("主线程开始！");
        new Thread(()->{
            step.set(step.addAndGet(1));
            System.out.println("子线1程开始");
            if(step.toString().equals("2")){
                synchronized(lock) {
                    lock.notify();
                }
            }
        }).start();
        new Thread(()->{
            step.set(step.addAndGet(1));
            System.out.println("子线2程开始");
            if(step.toString().equals("2")){
                synchronized(lock) {
                    lock.notify();
                }
            }
        }).start();
        synchronized(lock) {
            lock.wait();
        }
        System.out.println("主线程结束！");
    }

}

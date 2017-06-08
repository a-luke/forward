package com.luke.queue;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by yangf on 2017/5/26.
 */
public class LinkedBlockingQueueTest {
    private static LinkedBlockingQueue<Apple> linkedBlockingQueue = new LinkedBlockingQueue(1);

    static class Apple{
        String model;
        public Apple(String model){
            this.model = model;
        }

        @Override
        public String toString() {
            return "Apple{" + "model='" + model + '\'' + '}';
        }
    }

    public static void main(String[] args) throws Exception {
        linkedBlockingQueue.add(new Apple("iphone4"));
        System.out.println(linkedBlockingQueue.peek());


//        new LinkedBlockingQueueTest().production(new Apple("iphone4"));
//        new LinkedBlockingQueueTest().production(new Apple("iphone4s"));
//        new LinkedBlockingQueueTest().production(new Apple("iphone5"));
//        new LinkedBlockingQueueTest().production(new Apple("iphone6"));
//        new LinkedBlockingQueueTest().production(new Apple("iphone6plus"));
//
//        Thread.sleep(3000);
//        System.out.println(linkedBlockingQueue.toString());
//
//        new LinkedBlockingQueueTest().consumption();
//        new LinkedBlockingQueueTest().consumption();
//        new LinkedBlockingQueueTest().consumption();
//        new LinkedBlockingQueueTest().consumption();
//        new LinkedBlockingQueueTest().consumption();



    }


    //保证插入队列和输出插入信息同步
    private static synchronized void put(Apple apple){
        try {
            linkedBlockingQueue.put(apple);
            System.out.println("生产一个苹果" + apple);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //保证取出队列和输出取出信息同步
    private static synchronized void take() {
        try {
            Apple apple = linkedBlockingQueue.take();
            System.out.println("消费一个苹果" + apple);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //生产方法
    private void production(Apple apple){
        new Thread(()->{
            put(apple);
        }).start();
    }


    //消费方法
    private void consumption(){
        new Thread(()->{
            take();
        }).start();
    }

}

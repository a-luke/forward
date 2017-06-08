package com.luke.lock.examination;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * Created by yangf on 2016/10/26.
 */
public class DelayQueueTest {
    public static void main(String[] args) {
        DelayQueue<MyDelay> delayQueue = new DelayQueue();
        MyDelay myDelay = new MyDelay(3000, 18);
        delayQueue.put(myDelay);
//        myDelay.sayHello();
        try {
            delayQueue.take().sayHello();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}


class MyDelay implements Delayed{

    private long timeStamp;
    private Integer age;

    public MyDelay(long timeStamp, Integer age){
        this.timeStamp = timeStamp + System.currentTimeMillis();
        this.age = age;
    }

    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(this.timeStamp - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
    }

    @Override
    public int compareTo(Delayed o) {
        MyDelay that = (MyDelay)o;
        return this.timeStamp > that.timeStamp ? 0 : (this.timeStamp < that.timeStamp ? -1 : 0);
    }

    public void sayHello(){
        System.out.println("say hello!" + age);
    }
}
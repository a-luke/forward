package com.luke.model;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by yangf on 2017/8/2/0002.
 */
public class CounterModel {
    /**
     * 计数器
     */
    private AtomicInteger step = new AtomicInteger();

    public boolean isEnd() {
        return this.step != null && this.step.intValue() == 0;
    }

    public void addStep() {
        step.getAndSet(step.addAndGet(1));
    }

    public void minusStep() {
        step.getAndSet(step.addAndGet(-1));
    }

    public static void main(String[] args) {
        CounterModel counterModel = new CounterModel();
        counterModel.addStep();
        System.out.println(counterModel.isEnd());
        System.out.println(counterModel.step);
        counterModel.minusStep();
        System.out.println(counterModel.step);
        System.out.println(counterModel.isEnd());
    }
}

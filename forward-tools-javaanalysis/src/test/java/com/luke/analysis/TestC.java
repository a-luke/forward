package com.luke.analysis;

public class TestC {

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {

        }
    };

    class CC {
        void test() {
            System.out.println(123);
        }
    }


    static class BB {
        void test() {
            System.out.println(123123);
        }
    }


    /**
     * ceshi yong
     */
    enum Nnn {
        nn(), nl(), MN()
    }


    @interface Aa {

    }

    @Aa
    public static void test() {

    }


}

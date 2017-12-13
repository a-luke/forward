package com.luke.utils;

import java.util.Random;

/**
 * Created by yangf on 2017/12/13/0013.
 */
public class RandomStr {

    private final static String CHAR_ARR = "abcdefghijklmnopqrstuvwxyz1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public static String getRandomStr(int minLen){
        char[] c = CHAR_ARR.toCharArray();
        Random random = new Random();
        int index = random.nextInt(62);
        int indexStep = random.nextInt(4);
        StringBuilder result = new StringBuilder();
        result.append(c[index]);
        int len = minLen + indexStep;
        for(int i = 0; i < len; i++){
            int cIndex = random.nextInt(62);
            result.append(c[cIndex]);
        }
        return result.toString();
    }

    public static void main(String[] args) {
        System.out.println(getRandomStr(6));
    }
}

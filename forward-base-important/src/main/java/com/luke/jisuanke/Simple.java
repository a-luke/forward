package com.luke.jisuanke;

/**
 * Created by yangf on 2016/10/22.
 */
public class Simple {
    public static void main(String[] args) {
//        判断质数(29);
//        System.out.print("0, 1");
//        简单斐波那契(1, 0, 1, 20);
//        矩阵翻转();


//        System.out.println(Math.pow(2, -3));
//        System.out.println(1/Math.pow(2, 3));

    }

    public static void 判断质数(int number) {
        boolean flg = true;
        for(int i = 2; i < number; i++){
            if(number%i == 0){
                flg = false;
            }
        }
        System.out.println(flg);
    }

    /**
     * 斐波那契数列是一种非常有意思的数列，由 00 和 11 开始，之后的斐波那契系数就由之前的两数相加。用数学公式定义斐波那契数列则可以看成如下形式：
     F​n=Fn−1+F​n−2
     我们约定 F_nF​n
     表示斐波那契数列的第 nn 项，你能知道斐波那契数列中的任何一项吗？
     */
    public static void 简单斐波那契(int index, int one, int two, final int end){
        if(index < end){
            System.out.print(", " + (one + two));
            简单斐波那契(++index, two, two + one, end);
        }
    }


//    矩阵翻转
    public static void 矩阵翻转(){
        String[][] arrs = {{"0", "1", "2", "3"},
                         {"1", "2", "3", "4"},
                         {"2", "3", "4", "5"},
                         {"3", "4", "5", "6"}};
        for(int i = (arrs.length-1); i >= 0; i--){
            for(int j = 0, len = arrs[i].length; j < len; j++){
                System.out.print(arrs[i][j]);
            }
            System.out.println();
        }
    }

    public static void neval(){

    }
}
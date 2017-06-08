package com.luke.sort;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yangf on 2016/10/22.
 */
public class Suanfa {
    public static int j = 1;
    public static int count;
    public static void main(String[] args) {
//        test1();
        //    计算a-z的等差数列有多少个10为数列长度
//        test2(10);
        //计算一个存在平方和立方值的数
        test3(10000);
    }

    public void test(){

    }


//    public static void test4(int i){
//        int start = 'a';
//        int end = 'e';
//        int length=(end-start)/(i-1);
//        int num = 0;
//        for(int c=length;c>=1;c--){
//             num += test3(i,c);
//        }
//        System.out.println(num);
//    }
//
//    public  static int test4(int num,int length){
//        int start = 'a';
//        int end = 'e';//3
//        return  end - start - (num-1)*length;
//    }

    public static void test1(){
        List<Integer> list = new ArrayList<>();
        list.add(2);
        list.add(3);
        list.add(5);

        for(int i = 3; i < 100; i++){
            list.add(list.get(i - 1) + list.get(i - 3) - list.get(i-2));
        }
        System.out.println(list.get(70));
        System.out.println(list.get(99));
    }

//    计算a-z的等差数列有多少个
    public static void test2(int sum){
        int count = 0;
        int start = 'a';
        int end = 'l';
        for(int i = start; i <= (end - sum + 1); i++){
//          从每一个字母开始到倒数第sum个截止循环输出sum长度的不同等差的数组
            count += step(1, i, sum, 0, end);
        }
        System.out.println(count);
    }

    public static int step(int step, final int start, int sum, int count, final int end){
//        根据step打印不同等差的数组的前sum个
        for(int i = start; (i-start) < (step*(sum - 1)+1); i = i + step){
            System.out.print((char) i);
        }
        ++count;
        System.out.println();
        if((end-start)/(sum-1) > step){
            return step(++step, start, sum, count, end);
        }
        return count;
    }

    //计算一个存在平方和立方值的数
    public static  void test3(long bigNumber){
        for(long x = 1; x <= bigNumber; x++){
            int flag=0;
            for(long y = 1; y <= x; y++){
                if(x == y*y){
                    flag++;
                }
                if(x == y*y*y){
                    flag++;
                }
            }
            if(flag==2){
                System.out.println(x);
            }
        }
    }
}

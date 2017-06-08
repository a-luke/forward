package com.luke.sort;

import java.util.Arrays;

/**
 * Created by yangf on 2016/10/21.
 */
class Quick {

    public static void main(String[] args) {
        int arr[] = {1, 42, 31, 41, 51, 60, 70, 12, 34, 25};
        sort(arr, 0, arr.length-1);
        System.out.println(Arrays.toString(arr));
    }

    public static int partition(int[] array, int lo, int hi) {
        //固定的切分方式
        int key = array[lo];
        while (lo < hi) {
            while (array[hi] >= key && hi > lo) {//从后半部分向前扫描
                hi--;
            }
            array[lo] = array[hi];
            while (array[lo] <= key && hi > lo) {//从前半部分向后扫描
                lo++;
            }
            array[hi] = array[lo];
        }
        array[hi] = key;
        return hi;
    }



    public static void sort(int arr[], int low, int high) {
        if(low>=high){
            return ;
        }
        int index = partition(arr, low, high);
        System.out.println(Arrays.toString(arr));
        //        System.out.print("l=" + (l + 1) + "h=" + (h + 1) + "povit=" + povit + "\n");
        sort(arr, low, index - 1);
        sort(arr, index + 1, high);
    }
}
 

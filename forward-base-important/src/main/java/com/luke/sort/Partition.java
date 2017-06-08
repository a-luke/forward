package com.luke.sort;

/**
 * Created by yangf on 2016/10/20.
 */
import java.util.Arrays;

public class Partition {
    private int[] theArray;
    private int nElems;

    public int[] getTheArray() {
        return theArray;
    }

    public void setTheArray(int[] theArray) {
        this.theArray = theArray;
    }

    public Partition(int max) {
        theArray = new int[max];
        nElems = 0;
    }

    public void insert(int value) {
        theArray[nElems] = value;
        nElems++;
    }

    public int size() {
        return nElems;
    }

    public void display() {
        System.out.println("display");
        System.out.println(Arrays.toString(theArray));
    }

    public int partitionIt(int left, int right) {
        long pivot = theArray[left];
        while (true) {
            while (left < right && theArray[right--] >= pivot)
                ;
            if (left == right) {
                if (theArray[right + 1] < pivot) {
                    swap(left, right + 1);
                }
                return left;
            }
            if (left < right) {
                swap(left, right + 1);
                right++;
            }
            while (left < right && theArray[left++] <= pivot)
                ;

            if (left == right) {
                if (theArray[left - 1] > pivot) {
                    swap(left - 1, right);
                }
                return left;
            }
            if (left < right) {
                swap(left - 1, right);
                left--;
            }
        }
    }

    public void swap(int left, int right) {
        int temp;
        temp = theArray[left];
        theArray[left] = theArray[right];
        theArray[right] = temp;
    }

    public void recQuickSort(int left, int right) {
        if (left >= right) {
            return;
        } else {
            System.out.println("partitionIt 前 " + left + " " + right
                + Arrays.toString(theArray));
            int partition = partitionIt(left, right);
            System.out.println("partitionIt 后 " + left + " " + right
                + Arrays.toString(theArray));
            recQuickSort(left, partition - 1);
            recQuickSort(partition + 1, right);
        }
    }

    public static void quickSort(int a[], int start, int end) {
        int i, j;
        i = start;
        j = end;
        if ((a == null) || (a.length == 0))
            return;

        while (i < j) {// 查找基准点下标
            while (i < j && a[i] <= a[j])
                // 以数组start下标的数据为key，右侧扫描
                j--;
            if (i < j) { // 右侧扫描，找出第一个比key小的，交换位置
                int temp = a[i];
                a[i] = a[j];
                a[j] = temp;
            }
            while (i < j && a[i] < a[j])
                // 左侧扫描（此时a[j]中存储着key值）
                i++;
            if (i < j) { // 找出第一个比key大的，交换位置
                int temp = a[i];
                a[i] = a[j];
                a[j] = temp;
            }
        }
        if (i - start > 1) { // 递归调用，把key前面的完成排序
            quickSort(a, 0, i - 1);
        }
        if (end - j > 1) {
            quickSort(a, j + 1, end); // 递归调用，把key后面的完成排序
        }
    }

    public static void main(String[] args) {

        Partition p = new Partition(16);
        for (int i = 0; i < 16; i++) {
            p.insert((int) (Math.random() * 199));
        }
        p.display();
		/*
		 * int leftPtr=p.partitionIt(0, 15, 99); p.display();
		 */
		/* System.out.println("左边界："+leftPtr); */
        p.recQuickSort(0, 15);// 问题在哪？
        p.display();
        quickSort(p.getTheArray(), 0, 15);
        p.display();

    }

}
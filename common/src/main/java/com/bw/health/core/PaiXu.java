package com.bw.health.core;

/**
 * com.bw.health.core
 *
 * @author 李宁康
 * @date 2019 2019/07/29 14:08
 */
public class PaiXu {
    public static void main(String[] args) {
        int[] arr=new int[]{3,5,8,2,1,4};
        for (int i = 0; i < arr.length; ++i) {
            for (int j = 0; j < arr.length - i - 1; ++j) {        //此处你可能会疑问的j<n-i-1，因为冒泡是把每轮循环中较大的数飘到后面，
                // 数组下标又是从0开始的，i下标后面已经排序的个数就得多减1，总结就是i增多少，j的循环位置减多少
                if (arr[j] > arr[j + 1]) {        //即这两个相邻的数是逆序的，交换
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }

   /*     for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i]);
        }
*/


        int[] arr1 = new int[] { 5, 3, 6, 2, 9, 2, 1 };
        selectSort(arr1);
        for (int i = 0; i < arr1.length; i++) {
            System.out.print(arr1[i]);
        }


    }


    public static void selectSort(int[] arr) {
        int minIndex;
        for (int i = 0; i < arr.length ; i++) {
             minIndex = i; // 用来记录最小值的索引位置，默认值为i
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[j] < arr[minIndex]) {
                    minIndex = j; // 遍历 i+1~length 的值，找到其中最小值的位置
                }
            }
            // 交换当前索引 i 和最小值索引 minIndex 两处的值
            if (i != minIndex) {
                int temp = arr[i];
                arr[i] = arr[minIndex];
                arr[minIndex] = temp;
            }
            // 执行完一次循环，当前索引 i 处的值为最小值，直到循环结束即可完成排序
        }
    }
}

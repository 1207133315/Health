package com.bw.health.core;

import java.util.Stack;

/**
 * com.bw.health.core
 *
 * @author 李宁康
 * @date 2019 2019/07/29 14:40
 */
public class Sort {
    public static void main(String[] args) {
        /*int[] arr=new int[]{5,2,4,8,6,1,3};
       selectSort(arr);
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i]);
        }*/
        Stack<Integer> help=new Stack<Integer>();
        help.push(2);
        help.push(5);
        help.push(1);
        help.push(9);
        help.push(7);
        help.push(8);
        help.push(4);
        stackSort(help);
        System.out.println(help.pop());
        System.out.println(help.pop());
        System.out.println(help.pop());
        System.out.println(help.pop());
        System.out.println(help.pop());
        System.out.println(help.pop());
        System.out.println(help.pop());
    }
    //冒泡排序
    public static void maopaoSort(int[] arr){
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr.length - i - 1; j++) {
                if (arr[j]<arr[j+1]){
                    int k=arr[j];
                    arr[j]=arr[j+1];
                    arr[j+1]=k;
                }
            }
        }
    }
    //选择排序
    public static void selectSort(int[] arr){
        int minIndex;
        for (int i = 0; i < arr.length; i++) {
            minIndex=i;
            for (int j = i+1; j < arr.length ; j++) {
                if (arr[j]<arr[minIndex]){
                   minIndex=j;
                }
            }

            if (i!=minIndex){
                int k=arr[i];
                arr[i]=arr[minIndex];
                arr[minIndex]=k;
            }
        }
    }


    public static void stackSort(Stack<Integer> stack){
        Stack<Integer> help=new Stack<Integer>();
        while(!stack.isEmpty()){
            int cur=stack.pop();
            while(!help.isEmpty()&&help.peek()<cur){
                stack.push(help.pop());
            }
            help.push(cur);
        }
        while(!help.isEmpty()){
            stack.push(help.pop());
        }
    }

}

package com.wd.health.activity;

import android.os.Message;

import com.wd.health.circle.R;

public class HandlerText  {

   public static void main(String[] args){
       new Runnable(){
           @Override
           public void run() {
              System.out.print(Thread.currentThread().getName());
           }
       }.run();



       new Thread(new Runnable() {
           @Override
           public void run() {
               System.out.print(Thread.currentThread().getName());
           }
       }).start();
   }

}

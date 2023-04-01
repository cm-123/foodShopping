package com.chen.interview.Thread;

public class thread_text02_Runnable implements Runnable {
    int i = 1 ;
    @Override
    public void run(){
        for (int j = 0; j < 100; j++) {
            System.out.println("今天我吃了第"+ ++i +"次饭");

        }
    }



}

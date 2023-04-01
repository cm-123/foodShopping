package com.chen.interview.Thread;

public class MyThread_Main4_run {

    public static void main(String[] args) {
        try {
            MyThread4 thread = new MyThread4();
            thread.start();
            Thread.sleep(2000);
            thread.interrupt();
        } catch (InterruptedException e) {
            System.out.println("main catch");
            e.printStackTrace();
        }
        System.out.println("end!");
    }

}
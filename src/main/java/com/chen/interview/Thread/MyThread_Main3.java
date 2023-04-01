package com.chen.interview.Thread;

public class MyThread_Main3 {
    public static void main(String[] args) {
        try {
            MyThread3 thread3 = new MyThread3();
            thread3.start();

            Thread.sleep(1000);
//            thread3.interrupt();
//            System.out.println("是否停止1?="+thread3.isInterrupted());
//            System.out.println("是否停止2?="+thread3.isInterrupted());
            Thread.currentThread().interrupt();
            //interrupted()判断当前线程状态,并清除中断标志
            System.out.println("是否停止1?="+Thread.interrupted());
            System.out.println("是否停止2?="+Thread.interrupted());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

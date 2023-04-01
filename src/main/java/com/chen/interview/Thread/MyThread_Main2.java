package com.chen.interview.Thread;

public class MyThread_Main2 {
    public static void main(String[] args) {
        try {
            MyThread2 myThread2 = new MyThread2();
            myThread2.start();

            Thread.sleep(200);
            //线程对象有个boolean变量代表是否有中断请求,interrupt方法将线程的中断状态设置为true,但并没有终止线程
            myThread2.interrupt();
        } catch (InterruptedException e) {
            System.out.println("main catch");
            e.printStackTrace();
        }
        System.out.println("end");
    }
}

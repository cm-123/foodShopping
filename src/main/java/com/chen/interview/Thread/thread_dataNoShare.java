package com.chen.interview.Thread;


public class thread_dataNoShare {
    public static void main(String[] args) {

        //i不共享,每个线程维护自己的i变量
//        MyThread a = new MyThread("A");
//        MyThread b = new MyThread("B");
//        MyThread c = new MyThread("C");
//        a.start();
//        b.start();
//        c.start();
//-----------------------------------------------------------------------
        MyThread myThread = new MyThread();
        //线程abc启动的时候,执行的是myThread的方法,此时数据共享
        Thread a = new Thread(myThread,"A");
        Thread b = new Thread(myThread,"B");
        Thread c = new Thread(myThread,"C");
        a.start();
        b.start();
        c.start();
//-----------------------------------------------------------------------

    }



}

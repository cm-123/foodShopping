package com.chen.interview.Thread;

public class MyThread2 extends Thread {
    @Override
    public void run() {
        super.run();
        for (int i = 0; i < 5000; i++) {
            System.out.println("i = " + (i+1));
        }
    }
}

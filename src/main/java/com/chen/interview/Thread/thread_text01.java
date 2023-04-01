package com.chen.interview.Thread;

public class thread_text01 {
    public static void main(String[] args) {
        T1 t1 = new T1();
        t1.run();

    }



    public  static class T1 extends Thread{
        int i = 1 ;
        public void run(){

            System.out.println("今天我吃了第"+ ++i +"次饭");
        }

    }




}

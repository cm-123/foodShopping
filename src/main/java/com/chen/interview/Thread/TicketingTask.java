package com.chen.interview.Thread;

public class TicketingTask implements Runnable {
    private int quantity = 10;

    @Override
    public void run() {
        while (quantity > 0){
            tickting();
        }

    }


    private synchronized void  tickting(){
        if (quantity > 0){
            System.out.println(quantity + "号票");
            quantity--;
        }

    }
}

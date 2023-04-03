package com.chen.interview.Thread;

public class TicktingTaskMain {
    public static void main(String[] args) {
        TicketingTask ticketingTask = new TicketingTask();
        Thread thread = new Thread(ticketingTask);
        Thread thread1 = new Thread(ticketingTask);
        Thread thread2 = new Thread(ticketingTask);

        thread.run();
        thread1.run();
        thread2.run();


    }
}

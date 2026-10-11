package com.example.sec01;

import java.util.concurrent.CountDownLatch;

public class InboundOutboundTaskDemo {

    private static final int MAX_PLATFORM = 10;
    private static final int MAX_VIRTUAL = 20; // 10, 200_000, 1_000_000

    public static void main(String[] args) throws InterruptedException {

        //platformThreadDemo1();
        //platformThreadDemo2();
        //platformThreadDemo3();
        virtualThreadDemo();
    }


    /*
    To create a simple java platform thread
    */
    private static void platformThreadDemo1() {
        for (int i = 0; i < MAX_PLATFORM; i++) {
            int j = i;
            Thread thread = new Thread(() -> Task.ioIntensive(j));
            thread.start();
        }
    }

    /*
    To create platform thread using Thread.Builder
    */
    private static void platformThreadDemo2() {

        Thread.Builder builder = Thread.ofPlatform().name("vins", 1);

        for (int i = 0; i < MAX_PLATFORM; i++) {
            int j = i;
            Thread thread = builder.unstarted(() -> Task.ioIntensive(j));
            thread.start();
        }
    }

    /*
    To create platform daemon thread using Thread.Builder
    */
    private static void platformThreadDemo3() throws InterruptedException {
        var latch = new CountDownLatch(MAX_PLATFORM);
        var builder = Thread.ofPlatform().daemon().name("daemon", 1);
        for (int i = 0; i < MAX_PLATFORM; i++) {
            int j = i;
            Thread thread = builder.unstarted(() -> {
                Task.ioIntensive(j);
                latch.countDown();
            });
            thread.start();
        }
        latch.await();
    }

    /*
    To create virtual thread using Thread.Builder
    - virtual threads are daemon by default
    - virtual threads do not have any default name
    */
    private static void virtualThreadDemo() throws InterruptedException {
        var latch = new CountDownLatch(MAX_VIRTUAL);

        var builder = Thread.ofVirtual().name("virtual-", 1);

        for (int i = 0; i < MAX_VIRTUAL; i++) {
            int j = i;
            Thread thread = builder.unstarted(() -> {
                Task.ioIntensive(j);
                latch.countDown();
            });
            thread.start();
        }
        latch.await();
    }

}

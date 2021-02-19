package com.linkus.lock;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

public class QueueDemo {
    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<String> blockingDeque = new ArrayBlockingQueue(3);
        blockingDeque.add("1");
        blockingDeque.add("2");
        blockingDeque.add("3");
        blockingDeque.offer("5",3, TimeUnit.SECONDS);
        //System.out.println(blockingDeque.peek());
        System.out.println("blockingDeque size : "+blockingDeque.size());
        System.out.println();

        TimeUnit.SECONDS.sleep(5);
        System.out.println(blockingDeque.toString());
        System.out.println("blockingDeque size : "+blockingDeque.size());
        //System.out.println("blockingDeque add : "+bool);


    }
}

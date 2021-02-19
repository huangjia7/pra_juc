package com.linkus.third;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * LockSupport的demo
 */
public class LockSupportDemo {

    public static void main(String[] args) {
        Thread A = new Thread(()->{
            LockSupport.park();
            System.out.println(Thread.currentThread().getName()+"-->没阻塞了，come in");
        },"A");
        A.start();

        new Thread(()->{
            System.out.println(Thread.currentThread().getName()+"-->come in");
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            LockSupport.unpark(Thread.currentThread());
            LockSupport.park();
            System.out.println(Thread.currentThread().getName()+"-->完成，out");
        },"B").start();
    }
}

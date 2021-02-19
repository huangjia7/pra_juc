package com.linkus.third;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class AQSDemo {
    public static void main(String[] args) {
        ReentrantLock lock = new ReentrantLock();

        //带入银行办理业务的案例来模拟我们的AQS进行线程的管理和唤醒机制

        //3个线程模拟3个来银行网点，受理窗口办理业务的顾客
        // A 顾客就是第一个顾客，此时办理窗口没有人，A可以直接办理
        new Thread(()->{
            lock.lock();
            try{
                System.out.println("-----A thred come in");
                try {
                    TimeUnit.MINUTES.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }finally {
                lock.unlock();
            }

        },"A").start();
        //B 顾客就是第二个顾客  ---> 由于受理业务窗口只有一个(只能一个线程持有锁，此时B只能等待)
        // 进入候客区
        new Thread(()->{
            lock.lock();
            try{
                System.out.println("-----B thred come in");
            }finally {
                lock.unlock();
            }

        },"B").start();
        //C 顾客就是第三个顾客  ---> 由于受理业务窗口只有一个(只能一个线程持有锁，此时C只能等待)
        // 进入候客区
        new Thread(()->{
            lock.lock();
            try{
                System.out.println("-----C thred come in");
            }finally {
                lock.unlock();
            }

        },"C").start();
    }
}

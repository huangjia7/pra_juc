package com.linkus.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 可重入锁(递归锁)
 */
public class ReOpenLockDemo {
    // 打电话
    public static synchronized void call() throws InterruptedException{
        System.out.println(Thread.currentThread().getId()+"-"+Thread.activeCount()+"-"+"打电话");
        sms();
    }

    public static synchronized void sms() throws InterruptedException {
        TimeUnit.SECONDS.sleep(2);
        System.out.println(Thread.currentThread().getId()+"-"+Thread.activeCount()+"-"+"发短信");
    }
    private static Lock lock = new ReentrantLock();
    // 打电话
    public static void call2() throws InterruptedException{
        lock.lock();
        try{
            System.out.println(Thread.currentThread().getName()+"-"+Thread.activeCount()+"-"+"打电话22");
            sms2();
        }finally {
            lock.unlock();
        }
    }

    public static void sms2() throws InterruptedException {
        TimeUnit.SECONDS.sleep(2);
        lock.lock();
        try{
            System.out.println(Thread.currentThread().getName()+"-"+Thread.activeCount()+"-"+"发短信22");
        }finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
 /*       new Thread(()->{
            try {
                call();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"A").start();
        new Thread(()->{
            try {
                sms();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"B").start();*/

        new Thread(()->{
            try {
                call2();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"C").start();
        new Thread(()->{
            try {
                call2();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"D").start();

        while(Thread.activeCount()>2){
            Thread.yield();
        }
        System.out.println("==================");
        System.out.println(Thread.currentThread().getId()+"-"+Thread.activeCount());

    }
}

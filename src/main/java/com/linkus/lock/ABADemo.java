package com.linkus.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * 原子引用
 * ABA问题
 */
public class ABADemo {
    static AtomicReference<Integer> atomicReference = new AtomicReference(100);
    private static AtomicStampedReference<Integer> reference = new AtomicStampedReference(100,1);

    public static void main(String[] args) throws InterruptedException {
        //AtomicReference<Integer> reference = new AtomicReference(100);
        //AtomicInteger reference = new AtomicInteger(100);
        //useAtomicReferce();
        int initStamp = reference.getStamp();
        new Thread(()->{
            System.out.println("执行"+Thread.currentThread().getName()+"线程，"+reference.getReference()+"----->"+initStamp);
            reference.compareAndSet(100,103,initStamp,reference.getStamp()+1);
            System.out.println("执行"+Thread.currentThread().getName()+"线程，"+reference.getReference()+"------>"+reference.getStamp());
            reference.compareAndSet(103,100,reference.getStamp(),reference.getStamp()+1);
            System.out.println("执行"+Thread.currentThread().getName()+"线程，"+reference.getReference()+"----->"+reference.getStamp());
            System.out.println("执行完"+Thread.currentThread().getName()+"线程。");
        },"A").start();

        new Thread(()->{
            try {
                TimeUnit.SECONDS.sleep(2);
                System.out.println("执行"+Thread.currentThread().getName()+"线程，"+reference.getReference()+"------>"+initStamp);
                boolean bool = reference.compareAndSet(100,110,initStamp,initStamp+1);
                System.out.println("执行"+Thread.currentThread().getName()+"线程，结果："+bool+"|||"+reference.getReference()+"------>"+reference.getStamp());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"B").start();


        TimeUnit.SECONDS.sleep(4);
        System.out.println("继续执行main线程，reference值为："+reference.getReference()+"------>"+reference.getStamp());

    }

    private static void useAtomicReferce() throws InterruptedException {
        new Thread(()->{
            System.out.println("执行"+Thread.currentThread().getName()+"线程，reference值为："+atomicReference.get());
            atomicReference.compareAndSet(100,127);
            System.out.println("------------>"+atomicReference.get());
            atomicReference.compareAndSet(127,100);
            System.out.println("------------>"+atomicReference.get());
            System.out.println("执行完"+Thread.currentThread().getName()+"线程，reference值为："+atomicReference.get());
        },"A").start();

        new Thread(()->{
            try {
                System.out.println("执行"+Thread.currentThread().getName()+"线程，reference值为："+atomicReference.get());
                TimeUnit.SECONDS.sleep(2);
                atomicReference.compareAndSet(100,119);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"B").start();

        TimeUnit.SECONDS.sleep(4);
        System.out.println("继续执行main线程，reference值为："+atomicReference.get());
    }
}

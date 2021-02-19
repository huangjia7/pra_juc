package com.linkus.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 多线程之间按顺序调用，实现A->B->C三个线程调用，要求如下：
 * A打印3次，B打印6次，C打印9次
 * 。。。。
 * 共十轮
 */
public class ProConsDemo2 {
    private volatile static boolean stop = Boolean.TRUE;
    private static void stop(){
        stop = Boolean.FALSE;
    }
    public static void main(String[] args) throws InterruptedException {
        PCSource2 pcSource2 = new PCSource2();
        new Thread(()->{
            try {
                for (int i =1;i<=10;++i){
                    pcSource2.print3();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"A").start();
        new Thread(()->{
            try {
                for (int i =1;i<=10;++i){
                    pcSource2.print6();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"B").start();
        new Thread(()->{
            try {
                for (int i =1;i<=10;++i){
                    pcSource2.print9();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"C").start();

        TimeUnit.SECONDS.sleep(5L);
        stop();
    }
}

/**
 * 资源类
 */
class PCSource2{
    private volatile int sign = 1; //1-A执行 2-B执行 3-C执行
    private Lock lock2 = new ReentrantLock();
    Condition conditionA = lock2.newCondition();//条件A
    Condition conditionB = lock2.newCondition();//条件B
    Condition conditionC = lock2.newCondition();//条件C
    void print3() throws InterruptedException {
        lock2.lock();
        try{
            while(sign!=1){
                conditionA.await();
            }
            for (int i =1;i<=3;++i){
                System.out.println(Thread.currentThread().getName()+"线程执行第"+i+"次");
            }
            sign = 2;
            conditionB.signal();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock2.unlock();
        }
    }
    void print6() throws InterruptedException {
        lock2.lock();
        try{
            while(sign!=2){
                conditionB.await();
            }
            for (int i =1;i<=6;++i){
                System.out.println(Thread.currentThread().getName()+"线程执行第"+i+"次");
            }
            sign = 3;
            conditionC.signal();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock2.unlock();
        }
    }
    void print9() throws InterruptedException {
        lock2.lock();
        try{
            if(sign!=3){
                conditionC.await();
            }
            for (int i =1;i<=9;++i){
                System.out.println(Thread.currentThread().getName()+"线程执行第"+i+"次");
            }
            sign = 1;
            System.out.println("=======================");
            conditionA.signal();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock2.unlock();
        }
    }
}

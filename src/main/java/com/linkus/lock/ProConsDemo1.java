package com.linkus.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 传统版生产者消费者模式 :lock
 */
public class ProConsDemo1 {
    private volatile static boolean stop = Boolean.TRUE;
    private static void stop(){
        stop = Boolean.FALSE;
    }
    public static void main(String[] args) throws InterruptedException {
        PCSource1 pcSource1 = new PCSource1();
        new Thread(()->{
            try {
                while (stop){
                    pcSource1.produce();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"A").start();

        new Thread(()->{
            try {
                while (stop){
                    pcSource1.consume();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"B").start();

        TimeUnit.SECONDS.sleep(10L);
        stop();
    }
}

class PCSource1{
    private int num = 0;// 面包数量
    private Lock lock1 = new ReentrantLock();
    Condition condition = lock1.newCondition();

    void produce() throws InterruptedException {
        lock1.lock();
        try{
            while(num > 1){//大于0，不需要生产，等待
                condition.await();
            }
            System.out.println("生产后面包总数为："+(++num));
            condition.signal();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock1.unlock();
        }
    }
    void consume() throws InterruptedException {
        lock1.lock();
        try{
            while(num <= 1){//小于等于0，不能消费，等待
                condition.await();
            }
            System.out.println("消费后面包总数为："+(--num));
            condition.signal();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock1.unlock();
        }
    }
}

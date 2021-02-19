package com.linkus.lock;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class SemaphoreDemo {
    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(3);

        int count = 6;
        for (int i = 1; i <= count; ++i){
            final int index = i;
            new Thread(()->{
                try {
                    semaphore.acquire();
                    System.out.println("线程"+Thread.currentThread().getName()+"开始工作");
                    TimeUnit.SECONDS.sleep((long)index);
                    System.out.println("线程"+Thread.currentThread().getName()+"完成："+index);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    semaphore.release();
                }
            },String.valueOf(i)).start();
        }
    }
}

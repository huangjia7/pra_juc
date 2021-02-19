package com.linkus.lock;

import java.util.concurrent.TimeUnit;

/**
 * 死锁的demo
 */
public class DeadLockDemo {
    public static void main(String[] args) {
        String lockA="lockA",lockB="lockB";
        new Thread(new DeadSource(lockA,lockB),lockA).start();
        new Thread(new DeadSource(lockB,lockA),lockB).start();
    }
}
class DeadSource implements Runnable{
    private String lockA;
    private String lockB;

    public DeadSource(String lockA, String lockB) {
        this.lockA = lockA;
        this.lockB = lockB;
    }

    @Override
    public void run() {
        synchronized (lockA){
            System.out.println(Thread.currentThread().getName()+"锁定资源"+this.lockA);

            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (lockB){
                System.out.println(Thread.currentThread().getName()+"锁定资源"+this.lockB);
            }
        }
        System.out.println(Thread.currentThread().getName()+"完成testDead方法。。。");
    }
}

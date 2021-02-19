package com.linkus.lock;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class CyclicBarrierDemo {

    private static void mainRun() {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("集齐七颗龙珠，召唤神龙");
    }

    public static void main(String[] args) {
        int count = 7;
        CyclicBarrier cyclicBarrier = new CyclicBarrier(count,()->{
            mainRun();
        });

        for (int i = 1; i <= count; ++i){
            final int index = i;
            new Thread(()->{
                try {
                    TimeUnit.SECONDS.sleep(1);
                    System.out.println("集到龙珠"+index);
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            },String.valueOf(i)).start();
        }

    }
}

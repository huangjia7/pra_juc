package com.linkus.lock;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

public class CountDownLatchDemo {
    public static void main(String[] args) throws InterruptedException {
        int count = 10;
        CountDownLatch countDownLatch = new CountDownLatch(count);
        for (int i = 0; i<count;++i){
            final int num = i;
            new Thread(()->{
                try {
                    TimeUnit.SECONDS.sleep(2);
                    System.out.println(Thread.currentThread().getName()+"--->"+num);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    countDownLatch.countDown();
                }

            }).start();
        }

        boolean bool = countDownLatch.await(7,TimeUnit.SECONDS);
        System.out.println("执行为:"+bool+";进入 main方法 ，CountDownLatch 完成。。。");


    }
}

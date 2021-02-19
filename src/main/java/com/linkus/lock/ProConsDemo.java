package com.linkus.lock;

import java.util.concurrent.TimeUnit;

/**
 * 传统版生产者消费者模式 :synchronized+Notify+wait
 */
public class ProConsDemo {
    private volatile static boolean stop = Boolean.TRUE;
    private static void stop(){
        stop = Boolean.FALSE;
    }
    public static void main(String[] args) throws InterruptedException {
        PCSource pcSource = new PCSource();
        new Thread(()->{
            try {
                while (stop){
                    pcSource.produce();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"A").start();

        new Thread(()->{
            try {
                while (stop){
                    pcSource.consume();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"B").start();

        new Thread(()->{
            try {
                while (stop){
                    pcSource.produce();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"C").start();

        new Thread(()->{
            try {
                while (stop){
                    pcSource.consume();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"D").start();

        TimeUnit.SECONDS.sleep(5L);
        stop();
    }
}

class PCSource{
    private int num = 0;// 面包数量
    synchronized void produce() throws InterruptedException {
        while(num > 1){//大于0，不需要生产，等待
            this.wait();
        }
        System.out.println("生产后面包总数为："+(++num));
        this.notifyAll();
    }
    synchronized void consume() throws InterruptedException {
        while(num <= 1){//小于等于0，不能消费，等待
            this.wait();
        }
        System.out.println("消费后面包总数为："+(--num));
        this.notifyAll();
    }
}

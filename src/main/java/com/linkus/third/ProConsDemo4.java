package com.linkus.third;

import java.util.Arrays;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.LockSupport;

/**
 * locksupport实现生产者消费者模式
 */
public class ProConsDemo4 {
    private volatile static boolean stop = Boolean.TRUE;
    private static void stop(){
        stop = Boolean.FALSE;
    }

    public static void main(String[] args) throws InterruptedException {
        Thread threadA = null,threadB = null;
        PCSource4 pcSource4 = new PCSource4();
        threadA = new Thread(()->{
            try {
                while (stop){
                    pcSource4.produce();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"A");
        threadB = new Thread(()->{
            try {
                while (stop){
                    pcSource4.consume();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"B");
        pcSource4.setThreadA(threadA);
        pcSource4.setThreadB(threadB);
        threadA.start();
        threadB.start();

        TimeUnit.SECONDS.sleep(10L);
        stop();
    }
}

/**
 * 资源类
 */
class PCSource4{
    private volatile int num = 0;// 面包数量

    private Thread[] threads=new Thread[2];

    // threadA代表生产，threadB代表消费
    public void setThreadA(Thread threadA){
        threads[0] = threadA;
    }
    public void setThreadB(Thread threadB){
        threads[1] = threadB;
    }

    //生产
    public void produce() throws InterruptedException {
        while (num > 0){
            LockSupport.park();
        }
        System.out.println("生产后面包总数为："+(++num));
        LockSupport.unpark(threads[1]);
    }
    //消费
    public void consume() throws InterruptedException {
        while(num <= 0){//小于等于0，不能消费，等待
            LockSupport.park();
        }
        System.out.println("消费后面包总数为："+(--num));
        LockSupport.unpark(threads[0]);
    }

}

package com.linkus.lock;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 阻塞队列实现生产者消费者模式
 */
public class ProConsDemo3 {
    public static void main(String[] args) throws InterruptedException {
        PCSource3 pcSource3 = new PCSource3(new ArrayBlockingQueue<String>(3));
        new Thread(()->{
            try {
                pcSource3.produce();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"A").start();
        new Thread(()->{
            try {
                pcSource3.consume();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"B").start();

        TimeUnit.SECONDS.sleep(5L);
        pcSource3.stop();
    }
}

/**
 * 资源类
 */
class PCSource3{
    private volatile boolean FLAG = true; // 默认开启，进行生产+消费
    private AtomicInteger atomicInteger = new AtomicInteger(0);
    BlockingQueue<String> blockingQueue = null;

    public PCSource3(BlockingQueue<String> blockingQueue){
        this.blockingQueue = blockingQueue;
        System.out.println(blockingQueue.getClass().getName());
    }

    public void produce() throws InterruptedException {
        String data = null;
        boolean retValue;
        while (FLAG){
            data = atomicInteger.incrementAndGet()+"";
            retValue = blockingQueue.offer(data,2,TimeUnit.SECONDS);
            if(retValue){
                System.out.println(Thread.currentThread().getName()+"插入队列成功:"+data);
            }else{
                System.out.println(Thread.currentThread().getName()+"插入队列失败:"+data);
            }
            TimeUnit.MILLISECONDS.sleep(200L);
        }
        System.out.println(Thread.currentThread().getName()+"大老板叫停，无需生产，FLAG=false");
    }

    public void consume() throws InterruptedException {
        String data = null;
        while (FLAG){
            data = blockingQueue.poll(2,TimeUnit.SECONDS);
            if(null==data||data.equals("")){
                System.out.println(Thread.currentThread().getName()+"超过2秒没有消费，自动退出");
                FLAG = false;
                return;
            }
            System.out.println(Thread.currentThread().getName()+"消费队列成功:"+data);
        }

        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println(Thread.currentThread().getName()+"大老板叫停，无需消费，FLAG=false");
    }

    public void stop(){
        FLAG = false;
    }
}

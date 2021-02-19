package com.linkus.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

class MySource {
    volatile int num = 0;
    AtomicInteger atomicInteger = new AtomicInteger(0);
    public void add60(){
        this.num = 60;
    }
    public void addOne(){
        ++this.num;
    }
    public void addPlus(){
        atomicInteger.getAndIncrement();
    }
}

public class VolitileDemo {
/*    private static volatile boolean bool = true;

    private static void stop(){
        System.out.println("停止");
        bool = false;
    }

    public static void main(String[] args) {
        new Thread(()->{
            while(bool){
                System.out.println("=");
            }
        },"AA").start();

        new Thread(()->{
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            stop();
        },"BB").start();
    }*/

    public static void main(String[] args) {
        //volitileSeeking();
        MySource mySource = new MySource();
        for(int i =0;i<20;++i){
            //mySource.addOne();
            new Thread(()->{
                for(int j =0;j<1000;++j){
                    mySource.addOne();
                    mySource.addPlus();
                }
            }).start();
        }

        while(Thread.activeCount()>2){
            Thread.yield();
        }
        System.out.println("mySource.num:"+mySource.num);
        System.out.println("mySource.num:"+mySource.atomicInteger);
    }

    /**
     * 验证volitile可见性，可及时通知其余线程，可保证可见性
     */
    private static void volitileSeeking() {
        MySource mySource = new MySource();
        new Thread(()->{
            System.out.println("线程"+Thread.currentThread().getName()+"进入");
            try {
                TimeUnit.SECONDS.sleep(2);
                mySource.add60();
                System.out.println("线程"+Thread.currentThread().getName()+"休息2秒后将num更新为"+mySource.num);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"AA").start();

        while(mySource.num==0){

        }
        System.out.println("线程"+Thread.currentThread().getName()+"能进入，说明mySource.num不为0了,为："+mySource.num);
    }

}

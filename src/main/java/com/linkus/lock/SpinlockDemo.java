package com.linkus.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 资源类
 */
class CommonSource{
    int num = 0;
    // 自加1
    public void addOne(){
        this.lock();
        try {
            ++this.num;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            this.unlock();
        }
    }

    private AtomicReference<Thread> atomicReference = new AtomicReference<>();

    private void lock(){
        Thread thread = Thread.currentThread();
        //System.out.println("come in " +thread.getName());
        while(!atomicReference.compareAndSet(null,thread)){

        }
        //System.out.println(thread.getName()+"获得锁");
    }
    private void unlock(){
        Thread thread = Thread.currentThread();
        atomicReference.compareAndSet(thread, null);
        //System.out.println(thread.getName()+"释放锁");
    }
}

/**
 * 自旋锁
 */
public class SpinlockDemo {

    public static void main(String[] args) throws InterruptedException {
        CommonSource commonSource = new CommonSource();
        for (int i = 0; i < 10; ++i){
            //commonSource.addOne();
            new Thread(()->{
                for(int j =0;j<20000;++j){
                    commonSource.addOne();
                }
            },"线程"+i).start();
        }

        TimeUnit.SECONDS.sleep(30);

        System.out.println("-->"+commonSource.num);
    }
}

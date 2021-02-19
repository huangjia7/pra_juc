package com.linkus.lock;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

public class CallableDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureTask<Integer> futureTask = new FutureTask(new CallAble());
        Thread thread = new Thread(futureTask,"AA");
        thread.start();

        FutureTask<Integer> futureTask2 = new FutureTask(new CallAble());
        Thread thread2 = new Thread(futureTask2,"BB");
        thread2.start();
        Thread thread3 = new Thread(futureTask2,"CC");
        thread3.start();

        System.out.println(futureTask.get()+futureTask2.get());
    }

}

class CallAble implements Callable<Integer> {

    @Override
    public Integer call() throws Exception {
        System.out.println(Thread.currentThread().getName()+"进入");
        TimeUnit.SECONDS.sleep(4);
        return 100;
    }
}

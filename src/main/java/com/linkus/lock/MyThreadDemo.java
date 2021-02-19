package com.linkus.lock;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.util.UUID;
import java.util.WeakHashMap;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

import static sun.swing.SwingUtilities2.submit;

/**
 *  Array Arrays
 *  Executor Executors线程池辅助工具类
 */
public class MyThreadDemo {
    static ExecutorService executorService = new ThreadPoolExecutor(
            2,
            5,
            1L,
            TimeUnit.SECONDS,new LinkedBlockingQueue<>(3),
            Executors.defaultThreadFactory(),
            new ThreadPoolExecutor.CallerRunsPolicy());
    public static void main(String[] args) {
        AtomicInteger atomicInteger = new AtomicInteger(0);
        try{
  /*          for (int i = 0;i<5000;++i){
                executorService.submit(()->{
                    try {
                        TimeUnit.MILLISECONDS.sleep(100L);

                        System.out.println(Thread.currentThread().getName()+":"+ atomicInteger.incrementAndGet());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
            }*/
            Future<ErrorCode> future= null;
            for (int i = 0;i<10;++i){
                final int index = i;
                future= executorService.submit(()->{
                    TimeUnit.MILLISECONDS.sleep(600);
                    return new ErrorCode(index,UUID.randomUUID().toString());
                });
                System.out.println(Thread.currentThread().getName() +":"+ future.get().toString());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } finally {
            executorService.shutdown();
        }

    }
}
@Data
@AllArgsConstructor
@ToString
class ErrorCode{
    private int code;
    private String message;

}

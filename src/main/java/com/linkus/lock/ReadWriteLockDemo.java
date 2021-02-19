package com.linkus.lock;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 自定义缓存
 */
class Mycache{
    private ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    private volatile Map<String,String> map = new HashMap<>();
     void put(String key,String value) throws InterruptedException {
         readWriteLock.writeLock().lock();
         try {
             System.out.println(Thread.currentThread().getName()+" key:"+key+"--value:"+value+"; write begin...");
             map.put(key,value);
             System.out.println(Thread.currentThread().getName()+" key:"+key+"--value:"+value+"; write end..");
         }catch (Exception e){
             e.printStackTrace();
         }finally {
             readWriteLock.writeLock().unlock();
         }
     }
    void get(String key) throws InterruptedException {
        readWriteLock.readLock().lock();
        try {
            System.out.println(Thread.currentThread().getName()+" key:"+key+"; read begin...");
            System.out.println(Thread.currentThread().getName()+" key:"+key+"; value:"+map.get(key)+"; read end...");
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            readWriteLock.readLock().unlock();
        }
    }
}

public class ReadWriteLockDemo {
    public static void main(String[] args) {
        Mycache mycache = new Mycache();
        int count =5;
        for (int j = 0 ;j <count; ++j){
            final String key2 = j+"";
            new Thread(()->{
                try {
                    mycache.put(key2,key2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            },String.valueOf(key2)).start();
        }
        for (int i = 0 ;i <count; ++i){
            final String key = i+"";
            new Thread(()->{
                try {
                    mycache.get(key);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            },String.valueOf(key)).start();
        }
    }
}

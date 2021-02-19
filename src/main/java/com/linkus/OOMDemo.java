package com.linkus;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * 6种gc
 */
public class OOMDemo {
    //栈错误
    // -Xms5m -Xmx5m -Xss512k
    static void stack(){
        stack();
    }
    //堆错误
    // -Xms5m -Xmx5m -Xss512k
    static void heap(){
        //方式1
        String str = "linkus";
        while(true){
            str += str +new Random().nextInt(900000000)+new Random().nextInt(70000000);
            str.intern();
        }
        //方式2
        //byte[] bytes = new byte[6* 1024 * 1024];
    }
    //无法创建线程错误，常见高并发
    static void nativeth(){
        for(int i =0;i<Integer.MAX_VALUE;++i){
            final int index = i;
            new Thread(()->{
                System.out.println("num:"+index);
                try {
                    TimeUnit.SECONDS.sleep(Integer.MAX_VALUE);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }

    }
    //垃圾回收的时间大于创建的时间,Debug运行，才可重现此错误：  java.lang.OutOfMemoryError: GC overhead limit exceeded
    // -Xms10m -Xmx10m -XX:+PrintGCDetails -XX:MaxDirectMemorySize=5m
    static void overHead(){
        List<String> list = new ArrayList<>();
        int i = 0;

        try{
            while(true){
                list.add(String.valueOf(++i).intern());
            }
        }catch (Exception e){
            System.out.println("----"+i);
            e.printStackTrace();
        }

    }
    // 本地内存GC   -XX:MaxDirectMemorySize=5m
    // java.lang.OutOfMemoryError: Direct buffer memory
    static void directBuffer(){
        ByteBuffer.allocateDirect(6 *1024 *1024);

    }
    public static void main(String[] args) {
        //stack();
        //heap();
        //nativeth();
        //overHead();
        directBuffer();
    }
}

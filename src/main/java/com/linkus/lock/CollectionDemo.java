package com.linkus.lock;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

public class CollectionDemo {

    public static void main(String[] args) {
        //List<String> list = new ArrayList();
        //List<String> list = new CopyOnWriteArrayList<>();
        List<String> list = Collections.synchronizedList(new ArrayList());
        for (int i = 0; i <20 ;++i){
            new Thread(()->{
                list.add(UUID.randomUUID().toString().substring(0,8));
                System.out.println(list.toString());
            }).start();
        }

        while(Thread.activeCount()>2){
            Thread.yield();
        }
        System.out.println(list.size());
    }
}

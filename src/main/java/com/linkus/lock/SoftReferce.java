package com.linkus.lock;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;

//软引用、弱引用、虚引用
public class SoftReferce {
    // 软引用
    static void softReferce(){
        Object o1 = new Object();
        SoftReference<Object> softReference = new SoftReference(o1);
        System.out.println(o1);
        System.out.println(softReference.get());

        o1 = null;
        System.gc();
        System.out.println(o1);
        System.out.println(softReference.get());

       System.out.println("======软引用完成========");
/*
        try{
            byte[] bytes = new byte[6 * 1024 * 1024];
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            System.out.println(o1);
            System.out.println(softReference.get());
        }*/
    }
    // 弱引用
    static void weakReferce(){
        String o1 = new String("AA");
        ReferenceQueue referenceQueue = new ReferenceQueue();
        WeakReference<Object> weakReference = new WeakReference(o1, referenceQueue);
        System.out.println(o1);
        System.out.println(weakReference.get());
        System.out.println(referenceQueue.poll());

        o1 = null;
        System.gc();
        System.out.println(o1);
        System.out.println(weakReference.get());
        System.out.println(referenceQueue.poll());

        System.out.println("=======弱引用完成=======");
    }
    static void myHashMap(){
        HashMap<Integer,String> map = new HashMap<>();
        Integer key = new Integer("1");
        String value = "HashMap";
        map.put(key , value);
        System.out.println(map);

        key = null;
        System.out.println(map);
        System.out.println("=====System.gc()完成=====");
        System.gc();
        System.out.println(map);

        System.out.println("=====HashMap完成=====");
    }
    static void myWeakMap(){
        Map<Integer,String> map = new WeakHashMap<>();
        Integer key = new Integer("2");
        String value = "WeakHashMap";
        map.put(key , value);
        System.out.println(map);

        key = null;
        System.out.println(map);
        System.out.println("=====System.gc()完成=====");
        System.gc();
        System.out.println(map);

        System.out.println("=====WeakHashMap完成=====");
    }
    // 虚引用
    static void PhantomReference(){
        String o1 = new String("AA");
        ReferenceQueue referenceQueue = new ReferenceQueue();
        PhantomReference<Object> weakReference = new PhantomReference(o1, referenceQueue);
        System.out.println(o1);
        System.out.println(weakReference.get());
        System.out.println(referenceQueue.poll());

        o1 = null;
        System.gc();
        System.out.println(o1);
        System.out.println(weakReference.get());
        System.out.println(referenceQueue.poll());

        System.out.println("=======弱引用完成=======");
    }
    public static void main(String[] args) {
        //softReferce();
        //weakReferce();

/*        myHashMap();
        myWeakMap();*/

        PhantomReference();
    }
}

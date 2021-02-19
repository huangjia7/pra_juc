package com.linkus.lock;

public class SingletomDemo {
    private volatile static SingletomDemo singletomDemo = null;

    private SingletomDemo() {
        System.out.println("初始化SingletomDemo");
    }

    public static SingletomDemo getInstance(){
        if(null == singletomDemo){
            synchronized (SingletomDemo.class){
                if(null == singletomDemo){
                    singletomDemo = new SingletomDemo();
                }
            }
        }
        return singletomDemo;
    }

    public static void main(String[] args) {
        for (int i = 0 ;i < 200; ++i){
            new Thread(()->{
                SingletomDemo.getInstance();
            },"AA").start();
        }
    }
}

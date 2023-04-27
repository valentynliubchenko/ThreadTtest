package org.example;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;


public class AppSimpleThread {

    private final String name;

    public AppSimpleThread (String name) {
        this.name = name;
    }

    public static ThreadPoolExecutor fixedThreadPool = (ThreadPoolExecutor) Executors.newFixedThreadPool(4);

    public final AtomicBoolean isFinished = new AtomicBoolean(false);
    public void F1 () {
        System.out.println("Start  F1 " + name + " fixedThreadPool " + fixedThreadPool);

        fixedThreadPool.submit(() -> {
            F2();
        });
        System.out.println("Finish F1  " + name + " fixedThreadPool " + fixedThreadPool);
    }

    public void F2 () {
        System.out.println("Start  F2 " + name + " fixedThreadPool " + fixedThreadPool);
        fixedThreadPool.submit(() -> {
            F3();
        });
        System.out.println("Finish F2 " + name + " fixedThreadPool " + fixedThreadPool);
    }

    public void F3 () {
     //   System.out.println("Start  F3 " + name + " fixedThreadPool " + fixedThreadPool);
        isFinished.set( true);
       // System.out.println("Finish F3 " + name + " fixedThreadPool " + fixedThreadPool + "isfinish " + isFinished.get());
    }


    public static void main (String[] args) {
        AppSimpleThread app = new AppSimpleThread("th#1");
        System.out.println("Start main AppSimpleThread " + "fixedThreadPool " + fixedThreadPool);
        fixedThreadPool.submit(() -> {
            app.F1();
        });
        while (!app.isFinished.get()){
        }
        System.out.println("after while isFinish " + app.isFinished.get());
//        fixedThreadPool.shutdown();
//        try {
//            fixedThreadPool.awaitTermination(60, TimeUnit.SECONDS);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
        System.out.println("Finish main AppSimpleThread " + "fixedThreadPool " + fixedThreadPool);
        //fixedThreadPool.shutdown();
    }

}
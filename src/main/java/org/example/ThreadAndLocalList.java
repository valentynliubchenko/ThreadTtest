package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicBoolean;

public class ThreadAndLocalList {

    private final String name;

    public ThreadAndLocalList(String name) {
        this.name = name;
    }

    public static ThreadPoolExecutor fixedThreadPool = (ThreadPoolExecutor) Executors.newFixedThreadPool(4);

    public final AtomicBoolean isFinished = new AtomicBoolean(false);
    public void F1 () {
        System.out.println("Start  F1 " + name + " fixedThreadPool " + fixedThreadPool);
        List<Integer> integerList=new ArrayList<>();
        integerList.add(10);
        integerList.add(20);
        integerList.add(30);
        ListIterator listIterator=integerList.listIterator();
        fixedThreadPool.submit(() -> {
            F2(listIterator);
        });
        System.out.println("Finish F1  " + name + " fixedThreadPool " + fixedThreadPool);
    }

    public void F2 (ListIterator listIterator) {
        System.out.println("Start  F2 " + name + " fixedThreadPool " + fixedThreadPool);
        System.out.println(listIterator);
        System.out.println(listIterator.hashCode());
        System.out.println(listIterator.next());

        fixedThreadPool.submit(() -> {
            F3(listIterator);
        });

        System.out.println("Finish F2 " + name + " fixedThreadPool " + fixedThreadPool);
    }

    public void F3 (ListIterator listIterator) {
        System.out.println("Start  F3 " + name + " fixedThreadPool " + fixedThreadPool);
        System.out.println(listIterator);
        System.out.println(listIterator.hashCode());
        System.out.println(listIterator.next());
        fixedThreadPool.submit(() -> {
            F4(listIterator);
        });

        System.out.println("Finish F3 " + name + " fixedThreadPool " + fixedThreadPool + "isfinish " + isFinished.get());
    }
    public void F4 (ListIterator listIterator) {
        System.out.println("Start  F4 " + name + " fixedThreadPool " + fixedThreadPool);
        System.out.println(listIterator);
        System.out.println(listIterator.hashCode());
        System.out.println(listIterator.next());

        isFinished.set(true);
        System.out.println("Finish F3 " + name + " fixedThreadPool " + fixedThreadPool + "isfinish " + isFinished.get());
    }




    public static void main (String[] args) {
        ThreadAndLocalList app = new ThreadAndLocalList("th#1");
        System.out.println("Start main AppSimpleThread " + "fixedThreadPool " + fixedThreadPool);
        fixedThreadPool.submit(() -> {
            app.F1();
        });
        while (!app.isFinished.get()){
        }
        System.out.println("after while isFinish " + app.isFinished.get());
        fixedThreadPool.shutdown();
        while (!fixedThreadPool.isTerminated()){
        }
        System.out.println("Finish main AppSimpleThread " + "fixedThreadPool " + fixedThreadPool);
    }

}
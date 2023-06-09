package org.example;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;


public class App {

    private final String name;

    public App (String name) {
        this.name = name;
    }

    public static ThreadPoolExecutor fixedThreadPool = (ThreadPoolExecutor) Executors.newFixedThreadPool(4);

    public void F1 () {
        System.out.println("Start  F1 " + name);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        fixedThreadPool.submit(() -> {
            F11();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            F2();
        });
        System.out.println("Finish F1 " + name);
    }

    public void F11 () {
        System.out.println("Start  F11 " + name);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Finish F11 " + name);
    }
    public void F2 () {
        System.out.println("Start  F2 " + name);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        fixedThreadPool.submit(() -> {
            F3();
        });
        System.out.println("Finish F2 " + name);
    }

    public void F3 () {
        System.out.println("Start  F3 " + name);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Finish F3 " + name);
    }


    public static void main (String[] args) {
        App app = new App("th#1");
        System.out.println("Start main simpleThread");
        fixedThreadPool.submit(() -> {
            app.F1();
        });

        App app2 = new App("th#2");
        fixedThreadPool.submit(() -> {
            app2.F1();
        });

        try {
            Thread.sleep(13000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        fixedThreadPool.shutdown();
        System.out.println("Finish main simpleThread");


    }

}
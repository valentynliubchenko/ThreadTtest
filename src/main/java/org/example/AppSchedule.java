package org.example;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class AppSchedule {

    private final String name;

    public static ScheduledExecutorService fixedThreadPoolScheduled = (ScheduledExecutorService ) Executors.newScheduledThreadPool(1);

    public void F1 () {
        System.out.println("Start  F1 " + name);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        fixedThreadPoolScheduled.submit(() -> {
            F2();
        });
        System.out.println("Finish F1 " + name);
    }

    public void F2 () {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Start  F2 " + name);
        fixedThreadPoolScheduled.schedule(() -> {
            F3();
        },5, TimeUnit.SECONDS);
        System.out.println("Finish F2 " + name);
    }

    public void F3 () {
        System.out.println("Start  F3 " + name);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Finish F3 " + name);
    }
    public AppSchedule (String name) {
        this.name = name;
    }

    public static void main(String[] args) {
        System.out.println("sdfgsdf");

        AppSchedule app=new AppSchedule("th#1" );
        System.out.println("Start main sheduleThread");
        fixedThreadPoolScheduled.submit(() -> {
            app.F1();
        });

        AppSchedule app2=new AppSchedule("th#2");
        fixedThreadPoolScheduled.submit(() -> {
            app2.F1();
        });

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
         fixedThreadPoolScheduled.shutdown();
        try {
            fixedThreadPoolScheduled.awaitTermination(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Finish main sheduleThread");

    }

}

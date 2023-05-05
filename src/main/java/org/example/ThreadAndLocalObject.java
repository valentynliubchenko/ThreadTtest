package org.example;

import org.example.objects.Floor;
import org.example.objects.House;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicBoolean;

public class ThreadAndLocalObject {

    private final String name;

    public ThreadAndLocalObject(String name) {
        this.name = name;
    }

    public static ThreadPoolExecutor fixedThreadPool = (ThreadPoolExecutor) Executors.newFixedThreadPool(4);

    public final AtomicBoolean isFinished = new AtomicBoolean(false);
    public void F1 () {
        House house = new House("Big house");
        house.floor = new Floor(house);
        house.floor.data = 321;
        System.out.println("in F1 House name" + house.name + "floor data " + house.floor.data + " fixedThreadPool " + fixedThreadPool);
        fixedThreadPool.submit(() -> {
            F2(house.floor);
        });
    }

    public void F2 (Floor floor) {
        System.out.println("in F2 House name" + floor.house.name + "floor data " + floor.data + " fixedThreadPool " + fixedThreadPool);
        fixedThreadPool.submit(() -> {
            F3(floor);
        });
    }

    public void F3 (Floor floor) {
        System.out.println("in F3 House name" + floor.house.name + "floor data " + floor.data + " fixedThreadPool " + fixedThreadPool);
        fixedThreadPool.submit(() -> {
            F4(floor);
        });
    }
    public void F4 (Floor floor) {
        System.out.println("In F4 House name" + floor.house.name + "floor data " + floor.data + " fixedThreadPool " + fixedThreadPool);
        isFinished.set(true);
    }


    public static void main (String[] args) {
        ThreadAndLocalObject app = new ThreadAndLocalObject("th#1");
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
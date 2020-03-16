package com.salvatorecorsaro;


import java.util.concurrent.Semaphore;


public class Main {


    static int parkingSize;
    static boolean[] parkingStatus;
    static Semaphore barrera;
    private static int numberOfCars;


    public static void main(String[] args) throws InterruptedException {

        parkingSize = 2;
        numberOfCars = 6;
        settingTheParking();
        parkingInAction();
    }

    private static void parkingInAction() {
        for (int i = 0; i < numberOfCars; i++)
            new Thread(new Car(i)).start();
    }

    private static void settingTheParking() {
        parkingStatus = new boolean[parkingSize];
        barrera = new Semaphore(parkingSize, true);
    }


}
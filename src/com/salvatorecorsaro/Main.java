package com.salvatorecorsaro;





import java.util.concurrent.Semaphore;


public class Main {


     static int parkingSize = 2;
    // Parking place occupied = true; free - false
    static final boolean[] PARKING_PLACES = new boolean[parkingSize];
    // Set fair to true that for method aсquire() garantie order
   static final Semaphore SEMAPHORE = new Semaphore(parkingSize, true);
    private static int numberOfCars;


    public static void main(String[] args) throws InterruptedException {
        numberOfCars = 6;
        for (int i = 0; i < numberOfCars; i++) {
            new Thread(new Car(i)).start();
//            Thread.sleep(500);
        }
    }


}
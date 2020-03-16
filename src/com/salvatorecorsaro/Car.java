package com.salvatorecorsaro;

import java.util.Random;

public class Car implements Runnable {


    private int carNumber;

    public Car(int carNumber) {
        this.carNumber = carNumber;
    }

    @Override
    public void run() {

        approachTheParking();
        enterTheParking();
    }

    private void approachTheParking() {
        System.out.printf("Car number %d at the entrance of the parking \n", carNumber + 1);

        try {
            waitBeforeTryToPark();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void enterTheParking() {
        try {
            int parkingNumber = moveToFreeParkingSpot();
            waitBeforeExitParking();
            leaveTheParking(parkingNumber);

        } catch (InterruptedException e) {
        }
    }

    private void leaveTheParking(int parkingNumber) {
        synchronized (Main.parkingStatus) {
            Main.parkingStatus[parkingNumber] = false;
        }

        Main.barrera.release();
        System.out.printf("Car number %d has left the parking.\n", carNumber + 1);
    }

    private int moveToFreeParkingSpot() throws InterruptedException {
        Main.barrera.acquire();

        int parkingNumber = -1;

        synchronized (Main.parkingStatus) {
            for (int i = 0; i < Main.parkingSize; i++) {
                if (!Main.parkingStatus[i]) {
                    parkingNumber = parkInThePosition(i);
                    break;
                }
            }
        }
        return parkingNumber;
    }



    private int parkInThePosition(int i) {
        int parkingNumber;
        Main.parkingStatus[i] = true;
        parkingNumber = i;
        System.out.printf("Car number %d has parked\n", carNumber + 1);
        return parkingNumber;

    }

    private void waitBeforeExitParking() throws InterruptedException {
        Thread.sleep(getRandomNumberInRange(1000, 2000));
    }

    private void waitBeforeTryToPark() throws InterruptedException {
        Thread.sleep(getRandomNumberInRange(1000, 2000));
    }

    private int getRandomNumberInRange(int min, int max) {

        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

}

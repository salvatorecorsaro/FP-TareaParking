package com.salvatorecorsaro;

import java.util.Random;

public class Car implements Runnable {


    private int carNumber;

    public Car(int carNumber) {
        this.carNumber = carNumber;
    }

    @Override
    public void run() {
        try {
            waitBeforeTryToPark();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.printf("Car #%d drive to parking \n", carNumber);

        try {
            Main.barrera.acquire();

            int parkingNumber = -1;

            synchronized (Main.parkingStatus) {
                for (int i = 0; i < Main.parkingSize; i++) {
                    if (!Main.parkingStatus[i]) {
                        Main.parkingStatus[i] = true;
                        parkingNumber = i;
                        System.out.printf("Car #%d Parked on %d place.\n", carNumber, i);
                        break;
                    }
                }
            }

            try {
                waitBeforeExitParking();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            synchronized (Main.parkingStatus) {
                Main.parkingStatus[parkingNumber] = false;
            }

            Main.barrera.release();
            System.out.printf("Car #%d leave the parking.\n", carNumber);

        } catch (InterruptedException e) {
        }
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

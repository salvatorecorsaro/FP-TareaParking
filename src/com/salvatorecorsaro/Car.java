package com.salvatorecorsaro;

import java.util.Random;

public class Car implements Runnable{


        private int carNumber;

        public Car(int carNumber) {
            this.carNumber = carNumber;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(getRandomNumberInRange(1000, 2000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.printf("Car #%d drive to parking \n", carNumber);

            try {
                Main.SEMAPHORE.acquire();

                int parkingNumber = -1;

                synchronized (Main.PARKING_PLACES) {
                    for (int i = 0; i < Main.parkingSize; i++) {
                        if (!Main.PARKING_PLACES[i]) {
                            Main.PARKING_PLACES[i] = true;
                            parkingNumber = i;
                            System.out.printf("Car #%d Parked on %d place.\n", carNumber, i);
                            break;
                        }
                    }
                }

                try {
                    Thread.sleep(getRandomNumberInRange(1000, 2000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                synchronized (Main.PARKING_PLACES) {
                    //Free space for car
                    Main.PARKING_PLACES[parkingNumber] = false;
                }

                Main.SEMAPHORE.release();
                System.out.printf("Car #%d leave the parking.\n", carNumber);

            } catch (InterruptedException e) {
            }
        }

        private static int getRandomNumberInRange(int min, int max) {

            if (min >= max) {
                throw new IllegalArgumentException("max must be greater than min");
            }

            Random r = new Random();
            return r.nextInt((max - min) + 1) + min;
        }



}

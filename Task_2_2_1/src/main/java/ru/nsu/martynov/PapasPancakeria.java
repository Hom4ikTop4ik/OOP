package ru.nsu.martynov;

import java.util.concurrent.atomic.AtomicBoolean;

import static java.lang.Thread.sleep;

public class PapasPancakeria {

    // start, from
    private int rand(int st, int fr) {
        return st + (int)(Math.random() * (fr - st + 1));
    }

    class Cooker {
        private int speed;
        private AtomicBoolean ready;

        Cooker(int speed) {
            this.speed = speed;
            ready = new AtomicBoolean(true);
        }

        int getTime() {
            return speed;
        }

        void setTime(int speed) {
            this.speed = speed;
        }

        boolean isReady() {
            return ready.get();
        }

        void setReady(boolean ready) {
            this.ready.set(ready);
        }
    }

    class Deliver {
        private int speed;
        private int capacity;
        private AtomicBoolean ready;

        Deliver(int speed, int capacity) {
            this.speed = speed;
            this.capacity = capacity;
            this.ready = new AtomicBoolean(true);
        }

        int getTime() {
            return speed;
        }

        int getCapacity() {
            return capacity;
        }

        boolean isReady() {
            return ready.get();
        }

        void setReady(boolean ready) {
            this.ready.set(ready);
        }
    }

    class Storage {
        private int current;
        private int capacity;

        Storage(int capacity) {
            this.current = 0;
            this.capacity = capacity;
        }

        int getCapacity() {
            return this.capacity;
        }

        int push(int count) {
            if (this.current + count <= this.capacity) {
                this.current += count;
                return count;
            } else {
                int tmp = this.capacity - this.current;
                this.current = this.capacity;
                return tmp;
            }
        }

        int pop(int count) {
            if (count <= this.current) {
                this.current -= count;
                return count;
            } else {
                int tmp = this.current;
                this.current = 0;
                return tmp;
            }
        }
    }

    Storage storage;
    Cooker[] cookers;
    Deliver[] delivers;

    private int getCookerTime() {
        final int maxTime = 5;
        return rand(0, maxTime);
    }
    private int getDeliverTime() {
        final int maxTime = 20;
        return rand(0, maxTime);
    }
    private int getDeliverCapacity() {
        final int maxCapacity = 10;
        return rand(1, maxCapacity);
    }

    private void sillyCookerRevSort(Cooker[] arr) {
        for (int i = 0; i < arr.length; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[i].getTime() > arr[j].getTime()) {
                    Cooker temp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = temp;
                }
            }
        }
    }

    private void sillyDeliverRevSort(Deliver[] arr) {
        for (int i = 0; i < arr.length; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[i].getTime() > arr[j].getTime()) {
                    Deliver temp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = temp;
                }
            }
        }
    }

    void wakeUpCooker(Cooker cooker) {
        sleep(cooker.getTime());
    }

    private void proccessOrder(int orderCount) {
        while (orderCount > 0) {
            for (int i = 0; (orderCount > 0) && (i < cookers.length); i++) {
                if (cookers[i].isReady()) {
                    cookers[i].setReady(false);

                    wakeUpCooker(cookers[i]);
                }
            }
        }
    }

    PapasPancakeria() {
        cookers = new Cooker[10];
        delivers = new Deliver[10];

        for (int i = 0; i < cookers.length; i++) {
            int sp = getCookerTime();
            cookers[i] = new Cooker(sp);
        }
        sillyCookerRevSort(cookers); // cookers[0] is the fastest cooker

        int sum = 0;
        for (int i = 0; i < delivers.length; i++) {
            int sp = getDeliverTime();
            int cap = getDeliverCapacity();
            delivers[i] = new Deliver(sp, cap);

            sum += cap;
        }
        sillyDeliverRevSort(delivers); // del[0] is the fastest deliver

        // чтоб хранилище физически МОГЛО остаться ненулёвым,
        // даже если все курьеры разом возьмут
        storage = new Storage((int)(sum * 1.2) + 1);
    }

    public void start() {
        final int typesCount = 3;
        int type = rand(0, typesCount - 1);

        // take new order
        if (type == 0) {
            int orderCount = rand(1, 10);
            proccessOrder(orderCount);
        }
        // smth else
        else if (type == 1) {

        }
        // another else
        else if (type == 2) {

        } else {
            System.out.println("SOSAT");
        }
    }
}

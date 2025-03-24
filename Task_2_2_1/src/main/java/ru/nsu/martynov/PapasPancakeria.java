package ru.nsu.martynov;

import java.lang.Thread;

import java.io.FileReader;
import java.io.IOException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 * Main class.
 */
public class PapasPancakeria {

    private void ttt() {
        System.out.printf("Time: %d%n", System.currentTimeMillis() / 1000L % 1000);
    }

    private int rand(int st, int fr) {
        return st + (int) (Math.random() * (fr - st + 1));
    }

    private long randL(long st, long fr) {
        return st + (long) (Math.random() * (fr - st + 1));
    }

    public Storage storage;
    public Cooker[] cookers;
    public Deliver[] delivers;

    boolean pancakeriaIsOpen = true;

    long timeDay;

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

    void workCooker(Cooker cooker, int i) {
        new Thread(() -> {
            try {
                final int t = cooker.getTime();
                final int l = cooker.getLevel();

                ttt();
                System.out.printf("Cooker %d will free after %d secs%n", i, t);
                Thread.sleep(t * 1000L);
                storage.push(l);
                System.out.printf("Cooker %d is free%n", i);

                cooker.setReady(true);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    void workDeliver(Deliver deliver, int i) {
        new Thread(() -> {
            try {
                final int t = deliver.getTime();
                final int c = deliver.getCount();

                ttt();
                System.out.printf("Deliver %d stole %d pizzas, return after %d secs%n", i, c, t);
                Thread.sleep(t * 1000L);
                System.out.printf("Deliver %d returned%n", i);

                deliver.setReady(true);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void processCookers(int orderCount) {
        while (orderCount > 0) {
            for (int i = 0; (orderCount > 0) && (i < cookers.length); i++) {
                if (cookers[i].isReady()) {
                    orderCount--;
                    cookers[i].setReady(false);
                    loggerHelper();
                    workCooker(cookers[i], i + 1);
                }
            }
        }
    }

    private void processDelivers() {
        while (pancakeriaIsOpen || storage.getCount() > 0) {
            if (storage.getCount() <= 0) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            for (int i = 0; (storage.getCount() > 0) && (i < delivers.length); i++) {
                if (delivers[i].isReady()) {
                    int cap = delivers[i].getCapacity();
                    int cnt = storage.pop(cap);
                    delivers[i].setCount(cnt);
                    delivers[i].setReady(false);
                    loggerHelper();
                    workDeliver(delivers[i], i + 1);
                }
            }
        }
    }

    /**
     * Config loader.
     *
     * @param jsonPath — path to json config
     */
    public void loadConfig(String jsonPath) {
        try (FileReader reader = new FileReader(jsonPath)) {
            JSONTokener tokener = new JSONTokener(reader);
            JSONObject root = new JSONObject(tokener);

            // Загружаем поваров
            JSONArray cookersArray = root.getJSONArray("cookers");
            cookers = new Cooker[cookersArray.length()];
            for (int i = 0; i < cookersArray.length(); i++) {
                int time = cookersArray.getInt(i);
                cookers[i] = new Cooker(time);
            }
            sillyCookerRevSort(cookers);

            // Загружаем доставщиков
            JSONArray deliversArray = root.getJSONArray("delivers");
            delivers = new Deliver[deliversArray.length()];
            for (int i = 0; i < deliversArray.length(); i++) {
                JSONObject d = deliversArray.getJSONObject(i);
                int time = d.getInt("time");
                int capacity = d.getInt("capacity");
                delivers[i] = new Deliver(time, capacity);
            }
            sillyDeliverRevSort(delivers);

            // Загружаем склад
            int storageCap = root.getInt("storageCapacity");
            storage = new Storage(storageCap);
            this.timeDay = root.getInt("timeDay");

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Ошибка чтения конфигурации: " + e.getMessage());
        }
    }

    static int por(int num) {
        if (num <= 0) {
            return 1;
        }

        int ret = 0;
        while (num > 0) {
            ret++;
            num /= 10;
        }
        return ret;
    }

    void printCookers(Cooker[] cs) {
        int length = cs.length;
        int ll = cs[cs.length - 1].getTime();
        if (ll < cs.length) {
            ll = cs.length;
        }
        int digits = por(length);

        System.out.print("| Cooker N |");
        for (int i = 1; i <= length; i++) {
            int p = por(i);

            for (int j = 0; j < digits - p + 1; j++) {
                System.out.print(" ");
            }
            System.out.printf("%d |", i);
        }
        System.out.println();
        System.out.print("|  isReady |");
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < digits; j++) {
                System.out.print(" ");
            }
            System.out.printf("%s |", cs[i].isReady() ? "+" : "-");
        }
        System.out.println();
    }

    void printDelivers(Deliver[] ds) {
        int length = ds.length;
        int ll = ds[ds.length - 1].getCapacity();
        if (ll < ds.length) {
            ll = ds.length;
        }
        int digits = por(ll);

        System.out.print("| Deliver N |");
        for (int i = 1; i <= length; i++) {
            int p = por(i);

            for (int j = 0; j < digits - p + 1; j++) {
                System.out.print(" ");
            }
            System.out.printf("%d |", i);
        }
        System.out.println();
        System.out.print("|  curCount |");
        for (int i = 0; i < length; i++) {
            int p = por(ds[i].getCount());
            for (int j = 0; j < digits - p + 1; j++) {
                System.out.print(" ");
            }
            System.out.printf("%d |", ds[i].getCount());
        }
        System.out.println();
    }

    void loggerHelper() {
        printCookers(cookers);
        printDelivers(delivers);
    }

    void logger(long updateTimeMillis) {
        while (pancakeriaIsOpen) {
            try {
                Thread.sleep(updateTimeMillis);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            loggerHelper();
        }
    }

    PapasPancakeria(String jsonPath) {
        loadConfig(jsonPath);
    }

    /**
     * Start logger + new day.
     */
    public void start() {
        new Thread(() -> {
            logger(2500);
        }).start();

        newDay(timeDay);
    }

    /**
     * Start new day.
     *
     * @param workTimeSeconds — secs.
     */
    private void newDay(long workTimeSeconds) {
        long startTime = System.currentTimeMillis() / 1000L;
        long curTime = System.currentTimeMillis() / 1000L;
        System.out.printf("Let's start a new working day! (%d seconds)%n", workTimeSeconds);

        Thread dels = new Thread(() -> {
            processDelivers();
        });
        dels.start();

        while (curTime - startTime < workTimeSeconds) {
            final long minWait = 100;
            final long maxWait = 10000;
            long wait = randL(minWait, maxWait);
            try {
                Thread.sleep(wait);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            int orderCount = rand(1, 10);
            System.out.printf("Got %d orders%n", orderCount);
            processCookers(orderCount);

            curTime = System.currentTimeMillis() / 1000L;
        }

        System.out.println("Cookers are free)");
        System.out.println("Wait delivers...");
        pancakeriaIsOpen = false;
        try {
            dels.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("The working day is over!");
    }
}

package ru.nsu.martynov;

import java.lang.Thread;

import java.io.IOException;

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

    private Storage storage;
    private Cooker[] cookers;
    private Deliver[] delivers;

    public Storage getStorage() {
        return storage;
    }

    public void setStorage(Storage storage) {
        this.storage = storage;
    }

    public Cooker[] getCookers() {
        return cookers;
    }

    public void setCookers(Cooker[] cookers) {
        this.cookers = cookers;
    }

    public Deliver[] getDelivers() {
        return delivers;
    }

    public void setDelivers(Deliver[] delivers) {
        this.delivers = delivers;
    }

    boolean pancakeriaIsOpen = true;

    long timeDay;


    /**
     * Config loader.
     *
     * @param jsonPath — path to json config
     */
    public void loadConfig(String jsonPath) {
        Loader loader = new Loader();
        try {
            AppConfig config = loader.loadConfig(jsonPath);
            this.cookers = config.getCookers();
            this.delivers = config.getDelivers();
            this.storage = config.getStorage();
            this.timeDay = config.getTimeDay();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Ошибка чтения конфигурации: " + e.getMessage());
        }
    }

    void logger(long updateTimeMillis) {
        while (pancakeriaIsOpen) {
            try {
                Thread.sleep(updateTimeMillis);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Printer.loggerHelper(cookers, delivers);
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

    void workCooker(Cooker cooker, int i) {
        new Thread(() -> {
            try {
                final int t = cooker.getTime();
                final int l = cooker.getLevel();

                ttt();
                System.out.printf("Cooker %d will free after %d secs%n", i, t);
                Thread.sleep(t * 1000L);

                // Try to push all pizzas to storage, waiting if necessary
                int remaining = l;
                while (remaining > 0) {
                    synchronized (storage) {
                        int pushed = storage.push(remaining);
                        remaining -= pushed;
                        if (remaining > 0) {
                            System.out.printf("Cooker %d waiting for storage space (remaining: %d)%n", i, remaining);
                            storage.wait();
                        }
                    }
                }

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

                // Notify waiting cooks when pizzas are taken
                synchronized (storage) {
                    storage.notifyAll();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void processCookers(int orderCount) {
        while (orderCount > 0 && pancakeriaIsOpen) {
            for (int i = 0; (orderCount > 0) && (i < cookers.length); i++) {
                if (cookers[i].isReady()) {
                    orderCount--;
                    cookers[i].setReady(false);
                    Printer.loggerHelper(cookers, delivers);
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
                continue;
            }
            for (int i = 0; (storage.getCount() > 0) && (i < delivers.length); i++) {
                if (delivers[i].isReady()) {
                    int cap = delivers[i].getCapacity();
                    synchronized (storage) {
                        int cnt = storage.pop(cap);
                        delivers[i].setCount(cnt);
                        delivers[i].setReady(false);
                        storage.notifyAll(); // Notify waiting cooks
                    }
                    Printer.loggerHelper(cookers, delivers);
                    workDeliver(delivers[i], i + 1);
                }
            }
        }
    }

    volatile Integer pendingOrders = 0;
    /**
     * Start new day.
     *
     * @param workTimeSeconds — secs.
     */
    private void newDay(long workTimeSeconds) {
        long startTime = System.currentTimeMillis() / 1000L;
        System.out.printf("Let's start a new working day! (%d seconds)%n", workTimeSeconds);

        // Объект для синхронизации заказов
        final Object ordersMonitor = new Object();

        Thread dels = new Thread(() -> {
            processDelivers();
        });
        dels.start();

        // Поток генерации заказов
        Thread orderGenerator = new Thread(() -> {
            while (pancakeriaIsOpen) {
                final long minWait = 100;
                final long maxWait = 10000;
                long wait = randL(minWait, maxWait);
                try {
                    Thread.sleep(wait);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }

                int orderCount = rand(1, 10);
                synchronized (ordersMonitor) {
                    pendingOrders += orderCount;
                    System.out.printf("Got %d orders (total pending: %d)%n",
                            orderCount, pendingOrders);
                    ordersMonitor.notifyAll();
                }
            }
        });
        orderGenerator.start();

        // Основной цикл обработки заказов
        while (System.currentTimeMillis() / 1000L - startTime < workTimeSeconds
                && pancakeriaIsOpen) {
            synchronized (ordersMonitor) {
                try {
                    // Ждем новых заказов или окончания рабочего дня
                    if (pendingOrders == 0) {
                        ordersMonitor.wait(1000); // Таймаут для проверки времени
                    }

                    if (pendingOrders > 0) {
                        int ordersToProcess = pendingOrders;
                        pendingOrders = 0;
                        processCookers(ordersToProcess);
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }

        // Завершение рабочего дня
        System.out.println("Cookers are free)");
        System.out.println("Wait delivers...");
        pancakeriaIsOpen = false;

        // Разбудить все ожидающие потоки
        synchronized (ordersMonitor) {
            ordersMonitor.notifyAll();
        }
        synchronized (storage) {
            storage.notifyAll();
        }

        try {
            orderGenerator.join();
            dels.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println("The working day is over!");
    }
}

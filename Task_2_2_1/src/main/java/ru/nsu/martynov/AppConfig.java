package ru.nsu.martynov;

public class AppConfig {
    private Cooker[] cookers;
    private Deliver[] delivers;
    private Storage storage;
    private int timeDay;

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

    public Storage getStorage() {
        return storage;
    }

    public void setStorage(Storage storage) {
        this.storage = storage;
    }

    public int getTimeDay() {
        return timeDay;
    }

    public void setTimeDay(int timeDay) {
        this.timeDay = timeDay;
    }
}

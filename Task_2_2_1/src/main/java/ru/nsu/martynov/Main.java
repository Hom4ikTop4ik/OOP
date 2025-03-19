package ru.nsu.martynov;

public class Main {
    public static void main(String[] args) {
        PapasPancakeria pp = new PapasPancakeria("config.txt");
        pp.loadConfig("config.txt");

        // Вывод таблички пекарей
        System.out.println("+------------------------+");
        System.out.println("|         Cookers        |");
        System.out.println("+-------+----------------+");
        System.out.println("|  #    | Cook time      |");
        System.out.println("+-------+----------------+");
        PapasPancakeria.Cooker[] cookers = pp.cookers;
        for (int i = 0; i < cookers.length; i++) {
            System.out.printf("|  %-4d | %-14d |\n", i + 1, cookers[i].getTime());
        }
        System.out.println("+-------+----------------+");

        // Вывод таблички доставщиков
        System.out.println("\n+---------------------------------+");
        System.out.println("|            Delivers             |");
        System.out.println("+-------+------------+------------+");
        System.out.println("|  #    | Time, secs |  Capacity  |");
        System.out.println("+-------+------------+------------+");
        PapasPancakeria.Deliver[] delivers = pp.delivers;
        for (int i = 0; i < delivers.length; i++) {
            PapasPancakeria.Deliver d = delivers[i];
            System.out.printf("|  %-4d | %-10d | %-10d |\n", i + 1, d.getTime(), d.getCapacity());
        }
        System.out.println("+-------+------------+------------+");

        // Вывод склада
        System.out.println("\nStorage capacity: " + pp.storage.getCount());


        pp.newDay();
    }
}

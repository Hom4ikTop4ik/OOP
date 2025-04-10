package ru.nsu.martynov;

public class Printer {

    public synchronized static void loggerHelper(Cooker[] cs, Deliver[] ds) {
//        printCookers(cs);
//        printDelivers(ds);
    }

    private static void printCookers(Cooker[] cs) {
        int length = cs.length;
        int ll = cs[cs.length - 1].getTime();
        if (ll < cs.length) {
            ll = cs.length;
        }
        int digits = por(ll);

        System.out.print("| Cooker N |");
        printNumber(length, digits);
        System.out.print("|  isReady |");
        for (Cooker c : cs) {
            for (int j = 0; j < digits; j++) {
                System.out.print(" ");
            }
            System.out.printf("%s |", c.getReady() ? "+" : "-");
        }
        System.out.println();
    }

    private static void printDelivers(Deliver[] ds) {
        int length = ds.length;
        int ll = ds[ds.length - 1].getCapacity();
        if (ll < ds.length) {
            ll = ds.length;
        }
        int digits = por(ll);

        System.out.print("| Deliver N |");
        printNumber(length, digits);

        System.out.print("|  curCount |");
        for (Deliver d : ds) {
            int p = por(d.getCount());
            for (int j = 0; j < digits - p + 1; j++) {
                System.out.print(" ");
            }
            System.out.printf("%d |", d.getCount());
        }
        System.out.println();
    }

    private static void printNumber(int length, int digits) {
        for (int i = 1; i <= length; i++) {
            int p = por(i);

            for (int j = 0; j < digits - p + 1; j++) {
                System.out.print(" ");
            }
            System.out.printf("%d |", i);
        }
        System.out.println();
    }

    private static int por(int num) {
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
}

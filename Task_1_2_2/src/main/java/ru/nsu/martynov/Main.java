package ru.nsu.martynov;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        HashTable<String, Integer> table1 = new HashTable<>();
        HashTable<Double, Double> table2 = new HashTable<>();
        table1.put("A", 1);
        table1.put("B", 2);
        table1.put("C", 3);

        table2.put(0.0, 3.0);
        table2.put(1.0, 4.0);
        table2.put(2.0, 5.0);


        boolean flag = table1.equals(table2);
        System.out.println(flag);
    }
}
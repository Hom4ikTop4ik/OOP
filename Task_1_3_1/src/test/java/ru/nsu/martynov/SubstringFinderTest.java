package ru.nsu.martynov;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;

class SubstringFinderTest {

    @Test
    void findTestYes() throws IOException {
        ArrayList<Pair> list1 = new ArrayList<>();
        list1.add(new Pair(1, 0)); list1.add(new Pair(8, 0));
        assertEquals(list1, SubstringFinder.find("test1.txt", "бра"));

        ArrayList<Pair> list2 = new ArrayList<>();
        list2.add(new Pair(0, 0)); list2.add(new Pair(7, 0));
        assertEquals(list2, SubstringFinder.find("test2.txt", "абракадабра"));

        ArrayList<Pair> list3 = new ArrayList<>();
        list3.add(new Pair(1, 0)); list3.add(new Pair(8, 0)); list3.add(new Pair(1, 1)); list3.add(new Pair(8, 1));
        assertEquals(list3, SubstringFinder.find("test3.txt", "бра"));
    }

    @Test
    void findTestNo() throws IOException {
        ArrayList<Pair> listEmpty = new ArrayList<>();
        assertEquals(listEmpty, SubstringFinder.find("test1.txt", "яблоко"));
    }

    @Test
    void findTestEmpty() throws IOException {
        ArrayList<Pair> listEmpty = new ArrayList<>();
        assertEquals(listEmpty, SubstringFinder.find("test1.txt", ""));
        assertEquals(listEmpty, SubstringFinder.find("testEmpty.txt", "apple"));
        assertEquals(listEmpty, SubstringFinder.find("testEmpty.txt", ""));
    }
}

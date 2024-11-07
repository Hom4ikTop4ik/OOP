package ru.nsu.martynov;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import org.junit.jupiter.api.Test;

class SubstringFinderTest {

    @Test
    void findTestYes() {
        ArrayList<Integer> list1 = new ArrayList<>();
        list1.add(1); list1.add(8);
        assertEquals(list1, SubstringFinder.find("test1.txt", "бра"));

        ArrayList<Integer> list2 = new ArrayList<>();
        list2.add(0); list2.add(7);
        assertEquals(list2, SubstringFinder.find("test2.txt", "абракадабра"));

        ArrayList<Integer> list3 = new ArrayList<>();
        list3.add(1); list3.add(8); list3.add(13); list3.add(20);
        assertEquals(list3, SubstringFinder.find("test3.txt", "бра"));
    }

    @Test
    void findTestNo() {
        ArrayList<Integer> listEmpty = new ArrayList<>();
        assertEquals(listEmpty, SubstringFinder.find("test1.txt", "яблоко"));
    }

    @Test
    void findTestEmpty() {
        ArrayList<Integer> listEmpty = new ArrayList<>();
        assertEquals(listEmpty, SubstringFinder.find("test1.txt", ""));
        assertEquals(listEmpty, SubstringFinder.find("testEmpty.txt", "apple"));
        assertEquals(listEmpty, SubstringFinder.find("testEmpty.txt", ""));
    }


}
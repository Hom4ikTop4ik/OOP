package ru.nsu.martynov;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.Test;

class MyReaderTest {

    @Test
    void getCharTestNoFile() {
        PrintStream outOld = System.out;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        String fileName = "thisFileDontExist";
        MyReader reader = new MyReader(fileName);

        String str = "File '" + fileName + "' not found" + System.lineSeparator();
        assertEquals(str, outputStream.toString());

        System.setOut(outOld);
    }

    @Test
    void getCharTestGood() {
        MyReader reader = new MyReader("test1.txt");
        String str = "абракадабра";

        for (int i = 0; i < str.length(); i++) {
            assertEquals(reader.getChar(), str.charAt(i));
        }
    }

}
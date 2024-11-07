package ru.nsu.martynov;

import static ru.nsu.martynov.Constants.BUFFER_SIZE;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * The class is for reading to the buffer, because the files can be huge.
 */
public class MyReader {
    String fileName;
    FileReader inputStream;

    private char[] buffer;
    private int ptr = 0;
    private int len = 0;

    /**
     * Constructor.
     *
     * @param fileName — file you want to open.
     */
    public MyReader(String fileName) {
        this.fileName = fileName;
        this.buffer = new char[BUFFER_SIZE];
        this.ptr = 0;
        this.len = 0;

        try {
            inputStream = new FileReader(fileName);
        } catch (FileNotFoundException e) {
            System.out.println("File '" + fileName + "' not found");
            e.printStackTrace();
        }
    }

    /**
     * If buffer is empty read from file next BUFFER_SIZE characters.
     *
     * @return count of characters read.
     */
    private int fileToBuffer() {
        try {
            len = inputStream.read(buffer, 0, BUFFER_SIZE);
        } catch (IOException e) {
            e.printStackTrace();
            len = 0; // считано 0 байт
        }
        ptr = 0;

        return len;
    }

    /**
     * Func for getting next character from file.
     *
     * @return next character from file.
     */
    public char getChar() {
        if (ptr >= len) {
            if (fileToBuffer() <= 0) {
                return (char) -1;
            }
        }

        return buffer[ptr++];
    }
}

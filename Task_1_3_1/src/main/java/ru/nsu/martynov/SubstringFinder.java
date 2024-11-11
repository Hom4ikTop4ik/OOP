package ru.nsu.martynov;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;

/**
 * Class with find function.
 */
public class SubstringFinder {
    public static final int BUFFER_SIZE = 1024;

    /**
     * Main function.
     *
     * @param fileName — the name of file.
     * @param substring — substring you want find.
     * @return ArrayList with indexes in the string where the substrings begin.
     */
    public static ArrayList<Pair> find(String fileName, String substring) throws IOException {
        int ptr = 0;
        int lenSub = substring.length();
        int lineNumber = 0;

        if (lenSub == 0) {
            return new ArrayList<>();
        }

        Hashtable<Pair, Integer> process = new Hashtable<>();
        ArrayList<Pair> done = new ArrayList<>();

        char c;
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName), BUFFER_SIZE)) {
            while ((c = (char) reader.read()) != (char) -1) {
                // Такое только в Windows можно встретить обычно.
                if (c == '\r') {
                    continue;
                }
                if (c == '\n') {
                    lineNumber++;
                    ptr = 0;
                    continue;
                }

                // Вставим запись в HashTable, что с текущего индекса совпало НОЛЬ символов.
                // В цикле ниже проверится, совпадёт ли нулевой символ.
                // Если нет, то пара удалится. Да — начала подстроки и текста совпадают.
                process.put(new Pair(ptr++, lineNumber), 0);

                ArrayList<Pair> indexesToRemove = new ArrayList<>();

                for (Pair i : process.keySet()) {
                    Integer index = process.get(i);

                    // если index'овый символ совпал, перепишем для дальнейшей проверки на +1;
                    // иначе удалим запись, ведь подстрока не совпала на index'овом символе.
                    if (substring.charAt(index) == c) {
                        // совпал последний символ — добавим индекс в ответ и удалим из hash таблицы
                        if (index + 1 == lenSub) {
                            done.add(i);
                            indexesToRemove.add(i);
                        } else {
                            process.put(i, index + 1);
                        }
                    } else {
                        indexesToRemove.add(i);
                    }
                }

                for (int i = 0; i < indexesToRemove.size(); i++) {
                    process.remove(indexesToRemove.get(i));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return done;
    }
}

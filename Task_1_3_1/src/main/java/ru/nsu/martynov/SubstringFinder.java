package ru.nsu.martynov;

import java.util.ArrayList;
import java.util.Hashtable;

/**
 * Class with find function.
 */
public class SubstringFinder {

    /**
     * Main function.
     *
     * @param fileName — the name of file.
     * @param substring — substring you want find.
     * @return ArrayList with indexes in the string where the substrings begin.
     */
    public static ArrayList<Integer> find(String fileName, String substring) {
        int ptr = 0;
        int lenSub = substring.length();

        if (lenSub == 0) {
            return new ArrayList<>();
        }

        Hashtable<Integer, Integer> proccess = new Hashtable<>();
        ArrayList<Integer> done = new ArrayList<>();

        MyReader reader = new MyReader(fileName);

        char c;
        while ((c = reader.getChar()) != (char) -1) {
            // Вставим запись в HashTable, что с текущего индекса совпало НОЛЬ символов.
            // В цикле ниже проверится, совпадёт ли нулевой символ.
            // Если нет, то пара удалится. Да — начала подстроки и текста совпадают.
            proccess.put(ptr++, 0);

            ArrayList<Integer> indexesToRemove = new ArrayList<>();

            for (Integer i : proccess.keySet()) {
                Integer index = proccess.get(i);

                // если index'овый символ совпал, перепишем для дальнейшей проверки на +1;
                // иначе удалим запись, ведь подстрока не совпала на index'овом символе.
                if (substring.charAt(index) == c) {
                    // совпал последний символ — добавим индекс в ответ и удалим из hash таблицы
                    if (index + 1 == lenSub) {
                        done.add(i);
                        indexesToRemove.add(i);
                    } else {
                        proccess.put(i, index + 1);
                    }
                } else {
                    indexesToRemove.add(i);
                }
            }

            for (int i = 0; i < indexesToRemove.size(); i++) {
                proccess.remove(indexesToRemove.get(i));
            }
        }

        return done;
    }
}

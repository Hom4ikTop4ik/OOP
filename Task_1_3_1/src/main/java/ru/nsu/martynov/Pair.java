package ru.nsu.martynov;

public class Pair {
    Integer fst;
    Integer snd;

    public Pair(Integer fst, Integer snd) {
        this.fst = fst;
        this.snd = snd;
    }

    /**
     * Creates a string of the form (fst, snd).
     *
     * @return string contains two Integers.
     */
    public String toString() {
        return "(" + fst + ", " + snd + ")";
    }

    public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        if (this == obj) {
            return true;
        }

        Pair pair = (Pair) obj;
        return fst.equals(pair.fst) && snd.equals(pair.snd);
    }
}
package ru.nsu.martynov.substringfinder;

/**
 * Pair class.
 */
public class Pair {
    Integer fst;
    Integer snd;

    /**
     * Pair constructor.
     *
     * @param fst — first Integer.
     * @param snd — second Integer.
     */
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

    /**
     * equals func to compare Pairs.
     *
     * @param obj — second pair for comparing.
     * @return boolean value. True if Pairs are equal, false otherwise.
     */
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
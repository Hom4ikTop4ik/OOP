package ru.nsu.martynov;

/**
 * The card class.
 */
public class Card {
    enum Suit {
        Spades, Clubs, Diamonds, Hearts
        // Пики, Трефы, Бубны, Червы
    }

    enum Rank {
        Ace, Two, Three, Four, Five, Six, Seven, Eight, Nine, Ten, Jack, Queen, King
        // Туз, Двойка, Тройка, Четвёрка, Пятёрка, Шестёрка, Семёрка, Восьмёрка, Девятка, Десятка,
        // Валет, Дама, Король

        // Туз, Два, Три, Четыре, Пять, Шесть, Семь, Восемь, Девять, Десять, Валет, Дама, Король
    }

    Suit suit;
    Rank rank;

    /**
     * Init card with suit (Spades — 0, Clubs — 1, Diamonds — 2, Hearts — 3)
     * and rank (From 0 to 12: Ace, Two, Three, Four, ..., Nine, Ten, Jack, Queen, King).
     *
     * @param suit — card's suit from enum Suit;
     * @param rank — card's rank from enum Rank.
     * @throws IllegalArgumentException if suit or rank is wrong
     */
    public Card(int suit, int rank) throws IllegalArgumentException {
        this.suit = Suit.values()[0];
        this.rank = Rank.values()[0];

        if (0 <= suit && suit < 4) {
            this.suit = Suit.values()[suit];
        } else {
            throw new IllegalArgumentException("Invalid suit value");
        }

        if (0 <= rank && rank < 13) {
            this.rank = Rank.values()[rank];
        } else {
            throw new IllegalArgumentException("Invalid rank value");
        }
    }

    /**
     * Returns the points of this card.
     *
     * @param over — true = Ace (1), false = Ace (11).
     * @return the card points.
     */
    public int points(boolean over) {
        int rank = this.rank.ordinal() + 1;
        int point = 0;

        // Ace = 11 and 1 if over
        if (rank == 1) {
            if (over) {
                return 1;
            } else {
                return 11;
            }
        } else if ((2 <= rank) && (rank <= 10)) { // 2..10
            return rank;
        } else { // Jack, Queen or King
            return 10;
        }
    }

    /**
     * Print the rank of this card, its suit and the number of points.
     *
     * @param over — true = Ace (1), false = Ace (11).
     */
    public void print(boolean over) {
        System.out.print(this.rank + " " + this.suit + " (" + this.points(over) + ")");
    }
}

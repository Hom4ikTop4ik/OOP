package ru.nsu.martynov;

public class Card {
    enum Suit {
        Spades, Clubs, Diamonds, Hearts
        // Пики, Трефы, Бубны, Червы
    }

    enum Rank {
        Ace, Two, Three, Four, Five, Six, Seven, Eight, Nine, Ten, Jack, Queen, King
        // Туз, Двойка, Тройка, Четвёрка, Пятёрка, Шестёрка, Семёрка, Восьмёрка, Девятка, Десятка, Валет, Дама, Король
    }

    Suit suit;
    Rank rank;

    public Card(int suit, int rank) {
        this.suit = Suit.values()[suit];
        this.rank = Rank.values()[rank];
    }

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
        }
        // 2..10
        else if ((2 <= rank) && (rank <= 10)) {
            return rank;
        }
        // Jack, Queen or King
        else {
            return 10;
        }
    }

    public void print(boolean over) {
        System.out.print(this.rank + " " + this.suit + " (" + this.points(over) + ")");
    }
}

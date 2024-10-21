package ru.nsu.martynov;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * The main class.
 */
public class Game {
    Player player;
    Player dealer;

    Deck deck;

    int playerCounter;
    int dealerCounter;

    int roundCounter;

    Scanner sc = new Scanner(System.in);

    /**
     * Creates this game.
     */
    public Game() {
        player = new Player();
        dealer = new Player();

        deck = new Deck();
        deck.shuffle();

        playerCounter = 0;
        dealerCounter = 0;

        roundCounter = 1;
    }

    /**
     * Deal two cards to player and one to dealer.
     *
     * @param player — Player, for example player or dealer.
     * @param deck — Deck.
     * @return Card, last from deck.
     */
    public Card dealCard(Player player, Deck deck) {
        Card tmp;
        try {
            tmp = deck.cards.remove(deck.cards.size() - 1);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Deck is empty (or some another error).");
            return null;
        }
        player.addCard(tmp);
        return tmp;
    }

    /**
     * Считывает число "до последнего", при ошибкаж выводя текст, например, с просьбой ввести опять.
     *
     * @param text — если не получилось считать целочисленное число, вывести текст text
     * @return Вернуть отсканированное число.
     */
    public int myScanInt(String text, Scanner sc) {
        int input;

        while (true) {
            try {
                input = sc.nextInt();
                break;
            } catch (InputMismatchException e) {
                System.out.print(text);
                sc.next();
            }
        }

        return input;
    }

    /**
     * Starts one of the rounds.
     *
     * @param test — boolean flag, false if real and true if testing.
     */
    public void round(boolean test, boolean simulateGame) {
        if (roundCounter <= 1) {
            System.out.println("Welcome to the Marty Nov Game!");
        } else {
            System.out.println("\n");
        }
        System.out.println("Round " + roundCounter);

        if (!simulateGame) {
            player = new Player();
            dealer = new Player();

            if (deck.cards.size() < 52 / 2) {
                deck = new Deck();
                deck.shuffle();
            }

            dealCard(player, deck);
            dealCard(player, deck);
            dealCard(dealer, deck);
        }

        System.out.println("Dealer dealt cards: ");

        player.printHand(false, true);
        dealer.printHand(true, false);

        System.out.println("\nYour turn:");
        System.out.println("----------");

        while (true) {
            if (player.pointHand() >= 21) {
                break;
            }

            System.out.print("Input '1' to take more cards, '0' to stop: ");

            int input;
            if (test) {
                input = 0;
            } else {
                input = myScanInt("Input '1' to take more cards, '0' to stop: ", sc);
            }

            if (input != 1) {
                break;
            } else {
                Card tmp = dealCard(player, deck);
                System.out.print("You opened card: ");
                int point = player.pointHand() + tmp.points(false);
                tmp.print(point > 21);
                System.out.println();
            }

            player.printHand(false, true);
            dealer.printHand(true, false);
        }

        if (player.pointHand() > 21) {
            dealerCounter++;
            System.out.print("You lost! ");
        } else if (player.pointHand() == 21) {
            playerCounter++;
            System.out.print("You won! ");
        } else if (player.pointHand() < 21) {
            System.out.println("\nDealer's turn:");
            System.out.println("----------");

            boolean first = true;
            while (first || dealer.pointHand() < 17) {
                Card tmp = dealCard(dealer, deck);
                boolean over = ((player.pointHand() + tmp.points(false)) > 21);

                if (first) {
                    System.out.print("Dealer open closed card ");
                } else {
                    System.out.print("Dealer open card: ");
                }

                tmp.print(over);
                System.out.println();

                player.printHand(false, true);
                dealer.printHand(false, false);

                first = false;
            }

            if (dealer.pointHand() > 21 || (player.pointHand() > dealer.pointHand())) {
                playerCounter++;
                System.out.print("You won! ");
            } else if (player.pointHand() == dealer.pointHand()) {
                playerCounter++;
                dealerCounter++;
                System.out.print("Draw! ");
            } else if (player.pointHand() < dealer.pointHand()) {
                dealerCounter++;
                System.out.print("You lost! ");
            }
        }

        System.out.println("Score you/dealer: " + playerCounter + "/" + dealerCounter);

        roundCounter++;
    }

    /**
     * Just start a game, at least one round.
     *
     * @param test — boolean flag, false if real and true if testing.
     */
    public void game(boolean test, boolean simulateGame) {
        while (true) {
            round(test, simulateGame);

            System.out.print("Input '1' to play again, '0' to stop: ");
            int input;

            if (test) {
                input = 0;
            } else {
                input = myScanInt("Input '1' to take more cards, '0' to stop: ", sc);
            }

            if (input != 1) {
                break;
            }
        }
    }

    /**
     * Start the game.
     *
     * @param args — unused.
     */
    public static void main(String[] args) {
        Game game = new Game();
        game.game(false, false);
    }
}

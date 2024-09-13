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
     * Starts one of the rounds.
     *
     * @param test — boolean flag, false if real and true if testing.
     */
    public void round(boolean test, boolean testGame) {
        if (roundCounter <= 1) {
            System.out.println("Welcome to the Marty Nov Game!");
        } else {
            System.out.println("\n");
        }
        System.out.println("Round " + roundCounter);

        Card tmp;
        if (!testGame) {
            player = new Player();
            dealer = new Player();

            if (deck.cards.size() < 52 / 2) {
                deck = new Deck();
                deck.shuffle();
            }

            tmp = deck.cards.remove(deck.cards.size() - 1);
            player.addCard(tmp);
            tmp = deck.cards.remove(deck.cards.size() - 1);
            player.addCard(tmp);
            tmp = deck.cards.remove(deck.cards.size() - 1);
            dealer.addCard(tmp);
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

            int input = 0;

            while (true) {
                try {
                    if (test) {
                        input = 0;
                    } else {
                        input = sc.nextInt();
                    }
                    break;
                } catch (InputMismatchException e) {
                    System.out.print("Input '1' to take more cards, '0' to stop: ");
                    if (!test) {
                        sc.next();
                    }
                }
            }

            if (input != 1) {
                break;
            } else {
                tmp = deck.cards.remove(deck.cards.size() - 1);
                player.addCard(tmp);
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
                tmp = deck.cards.remove(deck.cards.size() - 1);
                boolean over = ((player.pointHand() + tmp.points(false)) > 21);
                dealer.addCard(tmp);

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
    public void game(boolean test, boolean testGame) {
        while (true) {
            round(test, testGame);

            System.out.print("Input '1' to play again, '0' to stop: ");
            int input = 0;

            while (true) {
                try {
                    if (test) {
                        input = 0;
                    } else {
                        input = sc.nextInt();
                    }
                    break;
                } catch (InputMismatchException e) {
                    System.out.print("Input '1' to play again, '0' to stop: ");
                    if (!test) {
                        sc.next();
                    }
                }
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

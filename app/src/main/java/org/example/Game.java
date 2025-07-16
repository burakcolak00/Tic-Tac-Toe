package org.example;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Game {
    private Board board;
    private boolean turn; // false = X, true = O
    private Scanner scanner;
    private final String player1 = "X";
    private final String player2 = "O";
    private int player1Wins = 0;
    private int player2Wins = 0;
    private int ties = 0;

    public Game() {
        board = new Board();
        scanner = new Scanner(System.in);
        turn = false;
    }

    public void start() {
        while (true) {
            board.print();
            getInput();
            if (board.checkWin() || board.isFull()) {
                board.print();
                announceResult();
                if (!askReplay()) {
                    System.out.println("Thanks for playing!");
                    break;
                }
                board.reset();
            } else {
                switchTurn();
            }
        }
    }

    private void getInput() {
        while (true) {
            System.out.print("\nPlayer " + (turn ? player2 : player1) + ", enter your move (1-9): ");
            try {
                String input = scanner.nextLine().trim();
                int move = Integer.parseInt(input) - 1;
                if (move < 0 || move > 8) {
                    System.out.println("Invalid move. Please enter a number between 1 and 9.");
                    continue;
                }
                if (!board.placeMove(move, turn ? player2 : player1)) {
                    System.out.println("That space is already taken. Try again.");
                    continue;
                }
                break;
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    }

    private void announceResult() {
        if (board.checkWin()) {
            System.out.println("\nPlayer " + (turn ? player2 : player1) + " wins!\n");
            if (turn) {
                player2Wins++;
            } else {
                player1Wins++;
            }
        } else {
            System.out.println("\nIt's a draw!\n");
            ties++;
        }
        printStatistics();
    }

    private boolean askReplay() {
        System.out.println("Do you want to play again? (yes/no)");
        String response = scanner.nextLine().trim().toLowerCase();
        while (!response.equals("yes") && !response.equals("no")) {
            System.out.println("Invalid response. Please enter 'yes' or 'no'.");
            response = scanner.nextLine().trim().toLowerCase();
        }
        if (response.equals("yes")) {
            switchTurn();
            board.reset();
            return true;
        } else {
            saveStatisticsToFile();
            return false;
        }
    }

    public void printStatistics() {
        System.out.println("Current Log:");
        System.out.println("Player " + player1 + " Wins: " + player1Wins);
        System.out.println("Player " + player2 + " Wins: " + player2Wins);
        System.out.println("Ties: " + ties);
        System.out.println();
    }

    public void saveStatisticsToFile() {
        try (PrintWriter writer = new PrintWriter("game_log.txt")) {
            writer.println("Game Log:");
            writer.println("Player " + player1 + " Wins: " + player1Wins);
            writer.println("Player " + player2 + " Wins: " + player2Wins);
            writer.println("Ties: " + ties);
            System.out.println("Game log saved to game_log.txt");
        } catch (IOException e) {
            System.out.println("Error writing game log to file: " + e.getMessage());
        }
    }

    private void switchTurn() {
        turn = !turn;
    }

}

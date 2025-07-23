package org.example;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Game {
    private Board board;
    private boolean turn; // false = X, true = O
    private Scanner scanner;
    private final String PLAYER_1 = "X";
    private final String PLAYER_2 = "O";
    private int player1Wins = 0;
    private int player2Wins = 0;
    private int ties = 0;
    private Computer computer;
    private int gameMode;

    public Game() {
        board = new Board();
        scanner = new Scanner(System.in);
        turn = false;
    }

    public void start() {
        gameMode = askForGameMode();

        if (gameMode == 1) {
            System.out.println("Starting Human vs. Human game...");
        } else if (gameMode == 2) {
            System.out.println("Starting Human vs. Computer game...");
            computer = new Computer(this, PLAYER_2);
        } else {
            System.out.println("Starting Computer vs. Human game...");
            computer = new Computer(this, PLAYER_1);
        }

        while (true) {
            board.print();
            if ((gameMode == 1) ||
                (gameMode == 2 && !turn) || // Human's turn in HvC
                (gameMode == 3 && turn)) {  // Human's turn in CvH
                getInput();
            } else {
                System.out.println("\nComputer (" + (turn ? PLAYER_2 : PLAYER_1) + ") is making a move...");
                computer.move();
            }
            if (board.checkWin() || board.isFull()) {
                board.print();
                announceResult();
                if (!askReplay()) {
                    System.out.println("Thanks for playing!");
                    break;
                }
            } else {
                switchTurn();
            }
        }
    }

    private void getInput() {
        while (true) {
            System.out.print("\nPlayer " + (turn ? PLAYER_2 : PLAYER_1) + ", enter your move (1-9): ");
            try {
                String input = scanner.nextLine().trim();
                int move = Integer.parseInt(input) - 1;
                if (move < 0 || move > 8) {
                    System.out.println("Invalid move. Please enter a number between 1 and 9.");
                    continue;
                }
                if (!board.placeMove(move, turn ? PLAYER_2 : PLAYER_1)) {
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
            System.out.println("\nPlayer " + (turn ? PLAYER_2 : PLAYER_1) + " wins!\n");
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
        System.out.println("Player " + PLAYER_1 + " Wins: " + player1Wins);
        System.out.println("Player " + PLAYER_2 + " Wins: " + player2Wins);
        System.out.println("Ties: " + ties);
        System.out.println();
    }

    public void saveStatisticsToFile() {
        try (PrintWriter writer = new PrintWriter("game_log.txt")) {
            writer.println("Game Log:");
            writer.println("Player " + PLAYER_1 + " Wins: " + player1Wins);
            writer.println("Player " + PLAYER_2 + " Wins: " + player2Wins);
            writer.println("Ties: " + ties);
            System.out.println("Game log saved to game_log.txt");
        } catch (IOException e) {
            System.out.println("Error writing game log to file: " + e.getMessage());
        }
    }

    private void switchTurn() {
        turn = !turn;
    }

    private int askForGameMode() {
        System.out.println("What kind of game would you like to play?\n");

        System.out.println("1. Human vs. Human");
        System.out.println("2. Human vs. Computer");
        System.out.println("3. Computer vs. Human\n");

        System.out.println("What is your selection? (1/2/3)");
        String selection = scanner.nextLine().trim();

        while (!selection.equals("1") && !selection.equals("2") && !selection.equals("3")) {
            System.out.println("Invalid selection. Please enter 1, 2, or 3.");
            selection = scanner.nextLine().trim();
        }
        return Integer.parseInt(selection);
    }

    public Board getBoard() {
        return board;
    }

    public boolean isTurn() {
        return turn;
    }

    public int getGameMode() {
        return gameMode;
    }
}

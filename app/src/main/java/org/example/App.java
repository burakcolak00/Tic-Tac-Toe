package org.example;

import java.util.Scanner;

public class App {

  static String[] board = { " ", " ", " ", " ", " ", " ", " ", " ", " " };
  static boolean turn = false; // false for player 'X', true for player 'O'
  static Scanner scanner = new Scanner(System.in);

  public static void main(String[] args) {
    System.out.println(new App().getGreeting());

    while (true) {
      printBoard();
      while (true) {
        if (getInput()) {
          turn = !turn; // Switch turns
          break; // Exit the loop if input is valid
        }
      }
      checkGameStatus();
    }
  }

    public String getGreeting() {
    return "Welcome to Tic-Tac-Toe!";
  }

  public static void printBoard() {
    System.out.println("\nCurrent board:");
    System.out.println(" " + board[0] + " | " + board[1] + " | " + board[2]);
    System.out.println("---+---+---");
    System.out.println(" " + board[3] + " | " + board[4] + " | " + board[5]);
    System.out.println("---+---+---");
    System.out.println(" " + board[6] + " | " + board[7] + " | " + board[8]);
  }

  public static boolean getInput() {

    System.out.print("\nEnter your move (1-9): ");
    try {
      String input = scanner.nextLine().trim();

      if (input.isEmpty()) {
        System.out.println("Invalid input. Please enter a number between 1 and 9.");
        return false;
      }

      int move = Integer.parseInt(input) - 1; // Convert to 0-based index

      if (move < 0 || move > 8) {
        System.out.println("Invalid move. Please enter a number between 1 and 9.");
        return false;
      }
      if (!board[move].equals(" ")) {
        System.out.println("That space is already taken. Try again.");
        return false;
      }
      board[move] = turn ? "O" : "X";
    } catch (Exception e) {
      System.out.println("Invalid input. Please enter a number between 1 and 9.");
      return false; // Return false to indicate invalid input
    }
    return true;
  }

  public static boolean checkWin() {
    String[][] winPatterns = {
        { board[0], board[1], board[2] }, // Row 1
        { board[3], board[4], board[5] }, // Row 2
        { board[6], board[7], board[8] }, // Row 3
        { board[0], board[3], board[6] }, // Column 1
        { board[1], board[4], board[7] }, // Column 2
        { board[2], board[5], board[8] }, // Column 3
        { board[0], board[4], board[8] }, // Diagonal \
        { board[2], board[4], board[6] } // Diagonal /
    };

    for (String[] pattern : winPatterns) {
      if (!pattern[0].equals(" ") && pattern[0].equals(pattern[1]) && pattern[1].equals(pattern[2])) {
        return true;
      }
    }
    return false;
  }

  public static boolean isBoardFull() {
    for (String space : board) {
      if (space.equals(" ")) {
        return false; // Found an empty space
      }
    }
    return true; // No empty spaces found
  }

  public static void resetBoard() {
    for (int i = 0; i < board.length; i++) {
      board[i] = " "; // Reset each space to empty
    }
  }

  public static void resetGame() {
    resetBoard(); // Reset the board
    turn = false; // Reset turn to player 'X'
    System.out.println("The game has been reset. Player 'X' starts again.");
  }

  public static void announceWinner() {
    if (checkWin()) {
      System.out.println("\nPlayer " + (turn ? "X" : "O") + " wins!\n"); // Announce the winner based on the current turn
    } else if (isBoardFull()) {
      System.out.println("\nIt's a draw!\n");
    }

    System.out.println("Do you want to play again? (yes/no)");
    String response = scanner.nextLine().trim().toLowerCase();

    while (!response.equals("yes") && !response.equals("no")) {
      System.out.println("Invalid response. Please enter 'yes' or 'no'.");
      response = scanner.nextLine().trim().toLowerCase();
    }

    if (response.equals("yes")) {
      resetGame(); // Reset the board for a new game
    } else {
      System.out.println("Thanks for playing!");
      System.exit(0); // Exit the game
    }
  }

  public static void checkGameStatus() {
    if (checkWin() || isBoardFull()) {
      printBoard(); // Print the final board state
      announceWinner();
    }
  }

}

package org.example;

import java.util.Random;

public class Computer {
    private final Game game;
    private final String symbol;
    private final Random random;

    public Computer(Game game, String symbol) {
        this.game = game;
        this.symbol = symbol;
        random = new Random();
    }

    void move() {
        if (isFirstMove()) {
            moveToRandomCorner();
        } else if (isSecondMove()) {
            moveToCenter();
        } else {
            int winningMove = canWin();
            if (winningMove != -1) {
                game.getBoard().placeMove(winningMove, symbol);
                return;
            }

            int blockingMove = canBlock();
            if (blockingMove != -1) {
                game.getBoard().placeMove(blockingMove, symbol);
                return;
            }

            randomMove();
        }
    }

    private int canWin() {
        for (int i = 0; i < 9; i++) {
            if (game.getBoard().isCellEmpty(i)) {
                game.getBoard().placeMove(i, symbol);
                boolean win = game.getBoard().checkWin();
                game.getBoard().setCell(i, " "); // Reset the cell to empty
                if (win) {
                    return i; // Return the winning move index
                }
            }
        }
        return -1; // No winning move found
    }

    private int canBlock() {
        String opponentSymbol = symbol.equals("X") ? "O" : "X";
        for (int i = 0; i < 9; i++) {
            if (game.getBoard().isCellEmpty(i)) {
                game.getBoard().placeMove(i, opponentSymbol);
                boolean win = game.getBoard().checkWin();
                game.getBoard().setCell(i, " "); // Reset the cell to empty
                if (win) {
                    return i; // Return the blocking move index
                }
            }
        }
        return -1; // No blocking move found
    }

    private void randomMove() {
        int index;
        do {
            index = random.nextInt(9);
        } while (!game.getBoard().isCellEmpty(index));
        game.getBoard().placeMove(index, symbol);
    }

    private void moveToRandomCorner() {
        int[] corners = { 0, 2, 6, 8 }; // Top-left, Top-right, Bottom-left, Bottom-right
        int index = corners[random.nextInt(corners.length)];

        game.getBoard().placeMove(index, symbol);
    }

    private void moveToCenter() {
        if (game.getBoard().isCenterOfTheBoardEmpty())
            game.getBoard().placeMove(4, symbol); // Center index is 4
        else
            randomMove(); // If center is not empty, make a random move
    }

    private boolean isFirstMove() {
        return game.getBoard().getMoveCount() == 0;
    }

    private boolean isSecondMove() {
        return game.getBoard().getMoveCount() == 1;
    }
}

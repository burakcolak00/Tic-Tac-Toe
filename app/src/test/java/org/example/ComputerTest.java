package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ComputerTest {

    private Board board;
    private Game game;
    private Computer computer;

    @BeforeEach
    void setUp() {
        board = new Board();
        game = new Game();
        // Inject our test board into the game
        // This requires a setter or reflection if Game doesn't support it.
        // For simplicity, we'll use the board from game.getBoard()
        computer = new Computer(game, "O");
    }

    @Test
    void testComputerFirstMoveIsCorner() {
        // Computer should move to a corner on first move
        computer.move();
        int[] corners = { 0, 2, 6, 8 };
        boolean cornerTaken = false;
        for (int c : corners) {
            if (!game.getBoard().isCellEmpty(c)) {
                cornerTaken = true;
                break;
            }
        }
        assertTrue(cornerTaken, "Computer should take a corner on first move.");
    }

    @Test
    void testComputerBlocksWin() {
        // Set up board so X is about to win, O must block
        game.getBoard().placeMove(0, "X");
        game.getBoard().placeMove(1, "X");
        // Computer's turn
        computer.move();
        // O should block at position 2
        assertEquals("O", game.getBoard().isCellEmpty(2) ? "" : "O", "Computer should block at position 2.");
    }

    @Test
    void testComputerTakesWin() {
        // Set up board so O can win
        game.getBoard().placeMove(3, "O");
        game.getBoard().placeMove(4, "O");
        game.getBoard().placeMove(0, "X");
        game.getBoard().placeMove(1, "X");
        // Computer's turn
        computer.move();
        // O should win at position 5
        assertEquals("O", game.getBoard().isCellEmpty(5) ? "" : "O", "Computer should win at position 5.");
    }

    @Test
    void testComputerRandomMove() {
        computer.move();
        // Check if the move is valid
        boolean validMove = false;
        for (int i = 0; i < 9; i++) {
            if (!game.getBoard().isCellEmpty(i)) {
                validMove = true;
                break;
            }
        }
        assertTrue(validMove, "Computer should make a valid random move.");
    }

    @Test
    void testComputerMovesToCenter() {
        // Fill a corner so it's not the first move
        game.getBoard().placeMove(0, "X");
        // Now it's the computer's first move (second overall), should take center
        computer.move();
        assertEquals("O", game.getBoard().isCellEmpty(4) ? "" : "O",
                "Computer should take the center if available.");
    }

    @Test
    void testCanWin() {
        // Set up board so O can win
        game.getBoard().placeMove(0, "O");
        game.getBoard().placeMove(1, "O");
        // Computer's turn
        computer.move();
        // O should win at position 2
        assertEquals("O", game.getBoard().isCellEmpty(2) ? "" : "O", "Computer should win at position 2.");
        assertTrue(game.getBoard().checkWin(), "Board should indicate a win after computer's move.");
    }

    @Test
    void testCanBlock() {
        // Set up board so X can win next move
        game.getBoard().placeMove(0, "X");
        game.getBoard().placeMove(1, "X");
        // Computer's turn
        computer.move();
        // O should block at position 2
        assertEquals("O", game.getBoard().isCellEmpty(2) ? "" : "O", "Computer should block at position 2.");
        assertFalse(game.getBoard().checkWin(), "Board should not indicate a win after blocking.");
    }
}
package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    private Board board;

    @BeforeEach
    void setUp() {
        board = new Board();
    }

    @Test
    void testNewBoardIsNotFull() {
        assertFalse(board.isFull(), "A new board should not be full.");
    }

    @Test
    void testBoardIsFullAfterFilling() {
        for (int i = 0; i < 9; i++) {
            assertTrue(board.placeMove(i, "X"), "Should be able to place on empty spaces.");
        }
        assertTrue(board.isFull(), "Board should be full after filling all spaces.");
    }

    @Test
    void testWinConditionRow() {
        board.placeMove(0, "X");
        board.placeMove(1, "X");
        board.placeMove(2, "X");
        assertTrue(board.checkWin(), "Three in a row should result in a win.");
    }

    @Test
    void testWinConditionColumn() {
        board.placeMove(0, "O");
        board.placeMove(3, "O");
        board.placeMove(6, "O");
        assertTrue(board.checkWin(), "Three in a column should result in a win.");
    }

    @Test
    void testWinConditionDiagonal() {
        board.placeMove(0, "X");
        board.placeMove(4, "X");
        board.placeMove(8, "X");
        assertTrue(board.checkWin(), "Three in a diagonal should result in a win.");
    }

    @Test
    void testNoWinCondition() {
        board.placeMove(0, "X");
        board.placeMove(1, "O");
        board.placeMove(2, "X");
        assertFalse(board.checkWin(), "This configuration should not result in a win.");
    }

    @Test
    void testResetBoardClearsBoard() {
        for (int i = 0; i < 9; i++) {
            board.placeMove(i, "O");
        }
        board.reset();
        for (int i = 0; i < 9; i++) {
            assertTrue(board.placeMove(i, "X"), "Should be able to place after reset.");
        }
    }

    @Test
    void testCannotPlaceOnOccupiedCell() {
        assertTrue(board.placeMove(0, "X"), "Should place successfully on empty space.");
        assertFalse(board.placeMove(0, "O"), "Should not place on an occupied space.");
    }
}

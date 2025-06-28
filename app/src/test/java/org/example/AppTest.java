package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;

class AppTest {

  @BeforeEach
    void setUp() {
        App.resetBoard();
    }

  @Test
  void appHasAGreeting() {
    App classUnderTest = new App();
    assertNotNull(classUnderTest.getGreeting(), "app should have a greeting");
  }

  @Test
    void testEmptyBoardIsNotFull() {
        assertFalse(App.isBoardFull(), "A new board should not be full.");
    }

    @Test
    void testBoardIsFull() {
        for (int i = 0; i < App.board.length; i++) {
            App.board[i] = "X";
        }
        assertTrue(App.isBoardFull(), "A fully filled board should return true for isBoardFull().");
    }

    @Test
    void testCheckWinRow() {
        App.board[0] = "X";
        App.board[1] = "X";
        App.board[2] = "X";
        assertTrue(App.checkWin(), "Three in a row should be a win.");
    }

    @Test
    void testCheckWinColumn() {
        App.board[0] = "O";
        App.board[3] = "O";
        App.board[6] = "O";
        assertTrue(App.checkWin(), "Three in a column should be a win.");
    }

    @Test
    void testCheckWinDiagonal() {
        App.board[0] = "X";
        App.board[4] = "X";
        App.board[8] = "X";
        assertTrue(App.checkWin(), "Three in a diagonal should be a win.");
    }

    @Test
    void testNoWin() {
        App.board[0] = "X";
        App.board[1] = "O";
        App.board[2] = "X";
        assertFalse(App.checkWin(), "This should not be a win.");
    }

    @Test
    void testResetBoard() {
        for (int i = 0; i < App.board.length; i++) {
            App.board[i] = "X";
        }
        App.resetBoard();
        for (String space : App.board) {
            assertEquals(" ", space, "After reset, all board spaces should be empty.");
        }
    }
}

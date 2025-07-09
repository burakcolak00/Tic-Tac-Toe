package org.example;

public class Board {
    private String[] cells;

    public Board() {
        cells = new String[9];
        reset();
    }

    public void reset() {
        for (int i = 0; i < 9; i++) {
            cells[i] = " ";
        }
    }

    public void print() {
        System.out.println("\nCurrent board:");
        System.out.println(" " + cells[0] + " | " + cells[1] + " | " + cells[2]);
        System.out.println("---+---+---");
        System.out.println(" " + cells[3] + " | " + cells[4] + " | " + cells[5]);
        System.out.println("---+---+---");
        System.out.println(" " + cells[6] + " | " + cells[7] + " | " + cells[8]);
    }

    public boolean placeMove(int index, String symbol) {
        if (!cells[index].equals(" ")) {
            return false;
        }
        cells[index] = symbol;
        return true;
    }

    public boolean checkWin() {
        int[][] winIndices = {
            {0, 1, 2}, {3, 4, 5}, {6, 7, 8}, // rows
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, // columns
            {0, 4, 8}, {2, 4, 6}             // diagonals
        };
        for (int[] line : winIndices) {
            String a = cells[line[0]];
            String b = cells[line[1]];
            String c = cells[line[2]];
            if (!a.equals(" ") && a.equals(b) && b.equals(c)) {
                return true;
            }
        }
        return false;
    }

    public boolean isFull() {
        for (String cell : cells) {
            if (cell.equals(" ")) {
                return false;
            }
        }
        return true;
    }
}

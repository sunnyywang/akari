package com.comp301.a09akari.model;

public class PuzzleImpl implements Puzzle {
    private int[][] board;

    public PuzzleImpl(int[][] board) {
        this.board = board;
    }

    @Override
    public int getWidth() {
        return board[0].length;
    }

    @Override
    public int getHeight() {
        return board.length;
    }

    @Override
    public CellType getCellType(int row, int col) {
        if (row >= getHeight() || col >= getWidth() || row < 0 || col < 0) {
            throw new IndexOutOfBoundsException();
        }
        int cell = board[row][col];

        if (cell < 5) {
            return CellType.CLUE;
        }
        if (cell == 5) {
            return CellType.WALL;
        }
        if (cell == 6) {
            return CellType.CORRIDOR;
        }
        return null;
    }

    @Override
    public int getClue(int row, int col) {
        if (row >= getHeight() || col >= getWidth() || row < 0 || col < 0) {
            throw new IndexOutOfBoundsException();
        }
        int cell = board[row][col];

        if (cell > 4) {
            throw new IllegalArgumentException();
        }
        return cell;
    }
}
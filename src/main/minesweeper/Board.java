package minesweeper;

import java.util.Random;

public class Board {
    private final Tile[][] board;
    private final int numMines;

    public Board(int row, int col, int numMines) {
        this.board = new Tile[row][col];
        this.numMines = numMines;
        populateBoard(row, col, numMines);
    }

    public void populateBoard(int row, int col, int numMines) {
        boolean[][] mines = new boolean[row][col];
        Random rng = new Random();
        for (int i = 0; i < numMines; i++) {
            boolean isMine = true;
            while (isMine) {
                int r = rng.nextInt(row);
                int c = rng.nextInt(col);
                if (!mines[r][c]) {
                    mines[r][c] = true;
                    isMine = false;
                }
            }
        }
        calculateValues(mines);
    }

    private void calculateValues(boolean[][] mines) {
        for (int row = 0; row < mines.length; row++) {
            for (int col = 0; col < mines[0].length; col++) {
                if (mines[row][col]) {
                    this.board[row][col] = new Tile(true, -1);
                } else {
                    int value = 0;
                    for (int y = -1; y <= 1; y++) {
                        for (int x = -1; x <= 1; x++) {
                            if (row + y >= 0 && row + y < mines.length && col + x >= 0 && col + x < mines[0].length) {
                                if (!(y == 0 && x == 0) && mines[row + y][col + x]) {
                                    value++;
                                }
                            }
                        }
                    }
                    this.board[row][col] = new Tile(false, value);
                }
            }
        }
    }

    public int getNumMines() {
        return numMines;
    }

    public void play(int row, int col) {
        board[row][col].playTile();
    }

    public void flag(int row, int col) {
        board[row][col].flagTile();
    }

    public boolean isPlayed(int row, int col) {
        return board[row][col].isPlayed();
    }

    public boolean isFlagged(int row, int col) {
        return board[row][col].isFlagged();
    }

    public boolean isMine(int row, int col) {
        return board[row][col].isMine();
    }

    public int value(int row, int col) {
        return board[row][col].value();
    }

    public int rows() {
        return board.length;
    }

    public int cols() {
        return board[0].length;
    }
}

package minesweeper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MinesweeperUI extends JFrame {
    private static final int DEFAULT_GRID_COLS = 20;
    private static final int DEFAULT_GRID_ROWS = 10;
    private static final int DEFAULT_MINE_COUNT = 50;
    private static final Color DEFAULT_TILE_COLOR = Color.gray;
    private static final Color DEFAULT_PLAYED_COLOR = Color.white;
    private static final Color DEFAULT_MINE_COLOR = Color.red;
    private static final Color DEFAULT_FLAG_COLOR = Color.orange;
    private static final Color DEFAULT_MENU_COLOR = Color.lightGray;
    private static final Dimension DEFAULT_TILE_SIZE = new Dimension(50, 50);
    private final JButton[][] buttonGrid;

    public MinesweeperUI (Board board) {
        this.buttonGrid = new JButton[DEFAULT_GRID_ROWS][DEFAULT_GRID_COLS];

        JFrame frame = new JFrame("Minesweeper");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create menu
        JMenuBar menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);
        JMenu operationsMenu = new JMenu("menu");
        menuBar.add(operationsMenu);

        // Style menu
        menuBar.setOpaque(true);
        menuBar.setBackground(DEFAULT_MENU_COLOR);

        // Add menu items
        addMenuItem(operationsMenu, "reveal all", e -> reveal(board));
        addMenuItem(operationsMenu, "reset", e -> reset(board));
        addMenuItem(operationsMenu, "exit", e -> exit());

        frame.setLayout(new GridLayout(DEFAULT_GRID_ROWS, DEFAULT_GRID_COLS));
        for (int row = 0; row < DEFAULT_GRID_ROWS; row++) {
            for (int col = 0; col < DEFAULT_GRID_COLS; col++) {
                JButton button = new JButton();
                frame.add(button);
                buttonGrid[row][col] = button;
                button.setBackground(DEFAULT_TILE_COLOR);
                button.setPreferredSize(DEFAULT_TILE_SIZE);
                button.setOpaque(true);
                int finalRow = row;
                int finalCol = col;
                button.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent arg0) {
                        if (arg0.getButton() == MouseEvent.BUTTON1){
                            board.play(finalRow, finalCol);
                            updateTile(board, finalRow, finalCol);
                        } else if (arg0.getButton() == MouseEvent.BUTTON3) {
                            board.flag(finalRow, finalCol);
                            updateTile(board, finalRow, finalCol);
                        }
                    }
                });
            }
        }

        frame.pack();
        frame.setVisible(true);
    }

    private void reveal(Board board) {
        for (int i = 0; i < board.rows(); i++) {
            for (int j = 0; j < board.cols(); j++) {
                board.play(i, j);
                updateTile(board, i, j);
            }
        }
    }

    private void updateTile(Board board, int row, int col) {
        if (board.isPlayed(row, col)) {
            if(board.isMine(row, col)) {
                buttonGrid[row][col].setBackground(DEFAULT_MINE_COLOR);
            } else {
                buttonGrid[row][col].setText(String.valueOf(board.value(row, col)));
                buttonGrid[row][col].setBackground(DEFAULT_PLAYED_COLOR);
            }
        } else if (board.isFlagged(row, col)) {
            buttonGrid[row][col].setText("F");
            buttonGrid[row][col].setBackground(DEFAULT_FLAG_COLOR);
        } else {
            buttonGrid[row][col].setText("");
            buttonGrid[row][col].setBackground(DEFAULT_TILE_COLOR);
        }
    }

    private void addMenuItem(JMenu menu, String title, ActionListener actionListener) {
        JMenuItem menuItem = new JMenuItem(title);
        menuItem.addActionListener(actionListener);
        menu.add(menuItem);
    }

    private void reset(Board board) {
        board.populateBoard(board.rows(), board.cols(), board.getNumMines());
        for (int row = 0; row < board.rows(); row++) {
            for (int col = 0; col < board.cols(); col++) {
                updateTile(board, row, col);
            }
        }
    }

    private void exit() {
        System.out.println("Exit");
        System.exit(0);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MinesweeperUI(
                new Board(DEFAULT_GRID_ROWS, DEFAULT_GRID_COLS, DEFAULT_MINE_COUNT)));
    }
}

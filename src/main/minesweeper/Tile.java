package minesweeper;

public class Tile {
    private boolean played;
    private boolean flagged;
    private final boolean mine;
    private final int value;

    public Tile(boolean mine, int value) {
        this.played = false;
        this.flagged = false;
        this.mine = mine;
        this.value = value;
    }

    public void playTile() {
        played = true;
    }

    public void flagTile() {
        flagged = !flagged;
    }

    public boolean isPlayed() {
        return played;
    }

    public boolean isFlagged() {
        return flagged;
    }

    public boolean isMine() {
        return mine;
    }

    public int value() {
        return value;
    }
}

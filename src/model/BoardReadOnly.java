package model;

public class BoardReadOnly implements ReadOnlyReversi {

  private Board board;

    public BoardReadOnly(Board board) {
        this.board = board;
    }

    @Override
    public Disc getDiscAt(int q, int r) {
        return board.getDiscAt(q, r);
    }

    @Override
    public boolean isCellEmpty(int q, int r) {
        return board.isCellEmpty(q, r);
    }

    @Override
    public int getSize() {
        return board.getSize();
    }

    @Override
    public boolean isGameOver() {
        return board.isGameOver();
    }
}

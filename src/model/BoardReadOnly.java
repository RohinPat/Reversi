package model;

public class BoardReadOnly implements ReversiReadOnly {

  private Board board;

    public BoardReadOnly(Board board) {
        this.board = board;
        board.playGame();
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

    /*
    @Override
    public void update(Board board) {
        if (board == null) {
            throw new IllegalArgumentException("Board must not be null");
        }
        this.board = board;
    }

     */
}

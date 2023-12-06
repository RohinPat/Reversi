package model;

/**
 * Creates an instance of a Readonly board in which you can only call observer methods.
 * On the board to prevent mutation when interacting with GUI.
 */
public class BoardReadOnly implements ReversiReadOnly {

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

  @Override
  public int getScore(Disc player) {
    return board.getScore(player);
  }

  @Override
  public int checkMove(Reversi model, Coordinate move) {
    return 0;
  }
}

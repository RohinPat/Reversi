package model;

import java.util.ArrayList;
import java.util.HashMap;

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
  public ArrayList<Coordinate> getPossibleMoves() {
    return null;
  }

  @Override
  public int checkMove(ReversiReadOnly model, Coordinate move) {
    return 0;
  }

  @Override
  public Disc currentColor() {
    return null;
  }

  @Override
  public HashMap<Coordinate, Cell> createCopyOfBoard() {
    return null;
  }
}

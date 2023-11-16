package controller.aistrat;

import java.util.ArrayList;

import model.Board;
import model.Coordinate;
import model.Disc;
import model.Turn;

public class CaptureCorners implements ReversiStratagy {

  @Override
  public Coordinate chooseMove(Board model, Disc turn) {
    Turn t = null;
    if (turn == Disc.BLACK) {
      t = Turn.BLACK;
    }
    else {
      t = Turn.WHITE;
    }
    Board copy = new Board(model.getSize(), model.createCopyOfBoard(), t);
    ArrayList<Coordinate> moves = copy.getPossibleMoves();
    int size = model.getSize();
    for (Coordinate move : moves) {
      if (this.getCorners(size).contains(move)) {
        return move;
      }
    }
    return new Coordinate(size, size);
  }

  private ArrayList<Coordinate> getCorners(int size) {
    ArrayList<Coordinate> corners = new ArrayList<Coordinate>();
    corners.add(new Coordinate(0, 1 - size));
    corners.add(new Coordinate(1 - size, 0));
    corners.add(new Coordinate(1 - size, size - 1));
    corners.add(new Coordinate(size - 1, 1 - size));
    corners.add(new Coordinate(size - 1, 0));
    corners.add(new Coordinate(0, size - 1));
    return corners;
  }
}


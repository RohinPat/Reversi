package controller.AIStrat;

import java.util.ArrayList;

import model.Board;
import model.Coordinate;
import model.Disc;

public class CaptureCorners implements ReversiStratagy {

  @Override
  public Coordinate chooseMove(Board model, Disc turn) {
    Board copy = model.createCopyOfBoard();
    ArrayList<Coordinate> moves = copy.getPossibleMoves();
    int size = model.getSize();
    for (Coordinate move : moves) {
      if (this.getCorners(size).contains(move)) {
        return move;
      }
    }

    ReversiStratagy ac = new AvoidCorners();
    return ac.chooseMove(model, turn);

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

  @Override
  public Coordinate chooseMove(Board model, Disc turn, ArrayList<Coordinate> possibleMoves) {
    CaptureMost cm = new CaptureMost();
    return cm.chooseMove(model, turn, possibleMoves);
  }

}

package controller.aistrat;

import java.util.ArrayList;

import model.Board;
import model.Coordinate;
import model.Disc;

public class AvoidCorners implements ReversiStratagy {

  @Override
  public Coordinate chooseMove(Board model, Disc turn) {
    CaptureMost cm = new CaptureMost();
    int size = model.getSize();
    ArrayList<Coordinate> notCorners = getSpotsToAvoid(size);
    ArrayList<Coordinate> possibleMoves = model.getPossibleMoves();
    for (Coordinate c : notCorners) {
      if (possibleMoves.contains(c)) {
        possibleMoves.remove(c);
      }
    }
    return cm.chooseMoveHelper(model, turn, possibleMoves);
  }

  private ArrayList<Coordinate> getSpotsToAvoid(int size) {
    ArrayList<Coordinate> notCorners = new ArrayList<Coordinate>();
    notCorners.add(new Coordinate(1, 1 - size));
    notCorners.add(new Coordinate(0, 2 - size));
    notCorners.add(new Coordinate(-1, 2 - size));

    notCorners.add(new Coordinate(2 - size, -1));
    notCorners.add(new Coordinate(2 - size, 0));
    notCorners.add(new Coordinate(1 - size, 1));

    notCorners.add(new Coordinate(1 - size, size - 2));
    notCorners.add(new Coordinate(2 - size, size - 2));
    notCorners.add(new Coordinate(2 - size, size - 1));

    notCorners.add(new Coordinate(-1, size - 1));
    notCorners.add(new Coordinate(0, size - 2));
    notCorners.add(new Coordinate(1, size - 2));

    notCorners.add(new Coordinate(size - 2, 1));
    notCorners.add(new Coordinate(size - 2, 0));
    notCorners.add(new Coordinate(size - 1, -1));

    notCorners.add(new Coordinate(size - 1, 2 - size));
    notCorners.add(new Coordinate(size - 2, 2 - size));
    notCorners.add(new Coordinate(size - 2, 1 - size));

    return notCorners;
  }
}

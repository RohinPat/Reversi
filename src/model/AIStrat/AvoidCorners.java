package model.AIStrat;

import java.util.ArrayList;

import model.Board;
import model.Coordinate;
import model.Turn;

public class AvoidCorners implements ReversiStratagy{

  @Override
  public Coordinate chooseMove(Board model, Turn turn) {
    CaptureMost cm = new CaptureMost();
    int size = model.getSize();
    ArrayList<Coordinate> notCorners = getSpotsToAvoid(size);
    ArrayList<Coordinate> possibleMoves = model.getPossibleMoves();
    for (Coordinate c : notCorners) {
      if (possibleMoves.contains(c)) {
        possibleMoves.remove(c);
      }
    }
    return cm.chooseMove(model, turn, possibleMoves);
  }

  private ArrayList<Coordinate> getSpotsToAvoid(int size) {
    ArrayList<Coordinate> notCorners = new ArrayList<Coordinate>();
    notCorners.add(new Coordinate(1, 0 - size));
    notCorners.add(new Coordinate(0, 1 - size));
    notCorners.add(new Coordinate(-1, 1 - size));

    notCorners.add(new Coordinate(1 - size, -1));
    notCorners.add(new Coordinate(1 - size, 0));
    notCorners.add(new Coordinate(0 - size, 1));

    notCorners.add(new Coordinate(0 - size, size - 1));
    notCorners.add(new Coordinate(1 - size, size - 1));
    notCorners.add(new Coordinate(1 - size, size));

    notCorners.add(new Coordinate(-1, size));
    notCorners.add(new Coordinate(0, size - 1));
    notCorners.add(new Coordinate(1, size - 1));

    notCorners.add(new Coordinate(size - 1, 1));
    notCorners.add(new Coordinate(size - 1, 0));
    notCorners.add(new Coordinate(size, -1));

    notCorners.add(new Coordinate(size, 1 - size));
    notCorners.add(new Coordinate(size - 1, 1 - size));
    notCorners.add(new Coordinate(size - 1, 0 - size));
    return notCorners;
  }

    @Override
    public Coordinate chooseMove(Board model, Turn turn, ArrayList<Coordinate> possibleMoves) {
        CaptureMost cm = new CaptureMost();
        return cm.chooseMove(model, turn, possibleMoves);
    }
    
}

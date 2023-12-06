package controller.aistrat;

import java.util.ArrayList;

import model.Coordinate;
import model.Disc;
import model.Reversi;
import model.ReversiReadOnly;


/**
 * The avoid corner strategy for playing Reversi.
 * This strategy focuses on avoiding corner spots on the Reversi board.
 * It modifies the list of potential moves by removing those that are
 * considered to be near the corners, as these positions are generally
 * less advantageous.
 */
public class AvoidCorners implements ReversiStratagy {

  /**
   * Chooses a move for the given turn in a Reversi game using the Avoid Corners strategy.
   * It filters out the moves that are near the corners of the board.
   *
   * @param model The Reversi game model representing the current state of the game.
   * @param turn  The object representing the current player's turn.
   * @return The selected for the next move, avoiding corners.
   */
  @Override
  public Coordinate chooseMove(ReversiReadOnly model, Disc turn) {
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

  /**
   * Generates a list of coordinates that represent spots to be avoided near the corners.
   * These are the positions adjacent to the corners where placing a disc could be disadvantageous.
   *
   * @param size The size of the Reversi board.
   * @return An ArrayList of objects representing spots to avoid.
   */
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

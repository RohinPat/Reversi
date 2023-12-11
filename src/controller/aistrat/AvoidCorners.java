package controller.aistrat;

import java.util.ArrayList;

import model.Coordinate;
import model.Disc;
import model.Coordinate;
import model.Position;
import model.ReversiReadOnly;


/**
 * The avoid corner strategy for playing Reversi.
 * This strategy focuses on avoiding corner spots on the Reversi board.
 * It modifies the list of potential moves by removing those that are
 * considered to be near the corners, as these Coordinates are generally
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
    ArrayList<Coordinate> possibleMoves = model.getPossibleMoves();
    ArrayList<Coordinate> returnCopy = model.getPossibleMoves();
    for (Coordinate c : possibleMoves) {
      System.out.println("tism");
      if (this.isSpotToAvoid(c, size)) {
        System.out.println("Remooved");
        returnCopy.remove(c);
      }
    }
    return cm.chooseMoveHelper(model, turn, returnCopy);
  }

  /**
   * Generates a list of coordinates that represent spots to be avoided near the corners.
   * These are the Coordinates adjacent to the corners where placing a disc could be disadvantageous.
   *
   * @param size The size of the Reversi board.
   * @return An ArrayList of objects representing spots to avoid.
   */
  private boolean isSpotToAvoid(Position p, int size) {
    return p.isNextToCorner(size);
  }
}

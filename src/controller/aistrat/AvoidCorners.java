package controller.aistrat;

import java.util.ArrayList;

import model.Disc;
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
  public Position chooseMove(ReversiReadOnly model, Disc turn) {
    CaptureMost cm = new CaptureMost();
    int size = model.getSize();
    ArrayList<Position> possibleMoves = model.getPossibleMoves();
    ArrayList<Position> returnCopy = model.getPossibleMoves();
    for (Position c : possibleMoves) {
      if (this.isSpotToAvoid(c, size)) {
        returnCopy.remove(c);
      }
    }
    return cm.chooseMoveHelper(model, turn, returnCopy);
  }

  /**
   * Checks if a given position on the Reversi board is near a corner and should be avoided.
   *
   * @param p    The position to check.
   * @param size The size of the Reversi board.
   * @return True if the position is near a corner and should be avoided, false otherwise.
   */
  private boolean isSpotToAvoid(Position p, int size) {
    return p.isNextToCorner(size);
  }
}

package controller.aistrat;

import java.util.ArrayList;

import model.Board;
import model.Coordinate;
import model.Disc;
import model.Reversi;
import model.ReversiReadOnly;
import model.Turn;

/**
 * The CaptureCorners strategy for playing Reversi.
 * This strategy prioritizes capturing corner positions on the Reversi board,
 * as owning corners can be a significant advantage in the game. The strategy
 * identifies possible moves that capture a corner and selects one of them if available.
 */
public class CaptureCorners implements ReversiStratagy {

  /**
   * Chooses a move for the given turn in a Reversi game using the Capture Corners strategy.
   * It prioritizes moves that allow capturing a corner position on the board.
   *
   * @param model The Reversi game model representing the current state of the game.
   * @param turn  The object representing the current player's turn.
   * @return The selected for the next move, prioritizing corners.
   */
  @Override
  public Coordinate chooseMove(ReversiReadOnly model, Disc turn) {
    Turn t = null;
    if (turn == Disc.BLACK) {
      t = Turn.BLACK;
    } else {
      t = Turn.WHITE;
    }
    ArrayList<Coordinate> moves = model.getPossibleMoves();
    int size = model.getSize();
    for (Coordinate move : moves) {
      if (this.getCorners(size).contains(move)) {
        return move;
      }
    }
    return new Coordinate(model.getSize(), model.getSize());
  }


  /**
   * Generates a list of coordinates that represent the corners of the board.
   *
   * @param size The size of the Reversi board.
   * @return An ArrayList of objects representing the corners of the board.
   */
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


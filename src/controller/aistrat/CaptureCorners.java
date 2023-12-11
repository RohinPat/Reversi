package controller.aistrat;

import java.util.ArrayList;

import model.Coordinate;
import model.Disc;
import model.Coordinate;
import model.Position;
import model.ReversiReadOnly;
import model.Turn;

/**
 * The CaptureCorners strategy for playing Reversi.
 * This strategy prioritizes capturing corner Coordinates on the Reversi board,
 * as owning corners can be a significant advantage in the game. The strategy
 * identifies possible moves that capture a corner and selects one of them if available.
 */
public class CaptureCorners implements ReversiStratagy {

  /**
   * Chooses a move for the given turn in a Reversi game using the Capture Corners strategy.
   * It prioritizes moves that allow capturing a corner Coordinate on the board.
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
    for (Coordinate move: moves) {
      System.out.println(move.getFirstCoordinate() + " " + move.getSecondCoordinate());
    }
    int size = model.getSize();
    for (Coordinate move : moves) {
      System.out.println("checked a move");
      if (this.getCorners(move, size)) {
        return move;
      }
    }
    System.out.println("this shit autistic");
    return new Coordinate(model.getSize(), model.getSize());
  }


  /**
   * Generates a list of coordinates that represent the corners of the board.
   *
   * @param size The size of the Reversi board.
   * @return An ArrayList of objects representing the corners of the board.
   */
  private boolean getCorners(Position p, int size) {
    return p.isCorner(size);
  }
}


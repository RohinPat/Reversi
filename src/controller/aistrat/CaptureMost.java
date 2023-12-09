package controller.aistrat;

import model.Disc;
import model.Position;
import model.ReversiReadOnly;
import model.Turn;
import model.Coordinate;

import java.util.ArrayList;


/**
 * The CaptureMost strategy for playing Reversi.
 * This strategy aims to choose a move that maximizes the player's score by capturing the most.
 * discs.
 * It evaluates all possible moves and selects the one that results in the highest score for.
 * the current player.
 */
public class CaptureMost implements ReversiStratagy {

  /**
   * Chooses the move that captures the most discs for the given turn in a Reversi game.
   *
   * @param model The Reversi game model representing the current state of the game.
   * @param turn  The  object representing the current player's turn.
   * @return The for the move that captures the most discs.
   */
  @Override
  public Position chooseMove(ReversiReadOnly model, Disc turn) {
    ArrayList<Position> moves = model.getPossibleMoves();
    return chooseMoveHelper(model, turn, moves);
  }

  /**
   * Helper method to choose the move that results in the highest score.
   *
   * @param model         The current state of the Reversi game.
   * @param turn          The current player's disc color.
   * @param possibleMoves A list of possible moves to consider.
   * @return The of the move that results in the highest score.
   */
  protected Position chooseMoveHelper(ReversiReadOnly model, Disc turn,
                                        ArrayList<Position> possibleMoves) {
    int max = 0;
    Position maxMove = null;
    Turn t = null;
    if (turn == Disc.BLACK) {
      t = Turn.BLACK;
    } else {
      t = Turn.WHITE;
    }
    for (Position move : possibleMoves) {
      int score = model.checkMove(model, move);
      if (score > max) {
        max = score;
        maxMove = move;
      }
    }
    if (maxMove == null) {
      return new Coordinate(model.getSize(), model.getSize());
    }
    return maxMove;
  }
}

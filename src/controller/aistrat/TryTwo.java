package controller.aistrat;

import model.Coordinate;
import model.Disc;
import model.Coordinate;
import model.ReversiReadOnly;


/**
 * The  class implements the interface
 * and encapsulates two different strategies. It attempts to choose a move
 * using the first strategy and, if unsuccessful (returns null), it then
 * tries the second strategy.
 */
public class TryTwo implements ReversiStratagy {
  ReversiStratagy first;
  ReversiStratagy second;

  /**
   * Constructs a strategy with two specified strategies.
   *
   * @param first  The primary strategy to try.
   * @param second The secondary strategy to use if the first one fails to find a move.
   */
  public TryTwo(ReversiStratagy first, ReversiStratagy second) {
    this.first = first;
    this.second = second;
  }

  /**
   * Chooses a move in the Reversi game by first trying the primary strategy.
   * If the primary strategy returns null (indicating no move found),
   * it then tries the secondary strategy.
   *
   * @param model The Reversi game model representing the current state of the game.
   * @param turn  The object representing the current player's turn.
   * @return The chosen for the move, or null if no move is found.
   */
  public Coordinate chooseMove(ReversiReadOnly model, Disc turn) {
    Coordinate ans = this.first.chooseMove(model, turn);
    if (ans.equals(new Coordinate(model.getSize(), model.getSize()))) {
      ans = this.second.chooseMove(model, turn);
    }
    return ans;
  }
}

package controller.aistrat;

import model.Coordinate;
import model.Disc;
import model.Reversi;

/**
 * Interface for strategy classes used in playing the game Reversi.
 * This interface defines a common method that all strategy classes must implement.
 * Each strategy class implementing this interface will provide its own algorithm
 * or logic to determine the next move based on the current state of the game.
 */
public interface ReversiStratagy {

    /**
   * Determines the next move in a Reversi game based on the implemented strategy.
   * This method is meant to be implemented by different strategy classes, each providing
   * its own approach to selecting a move.
   *
   * @param model The current state of the Reversi game, encapsulating the game board and its rules.
   * @param turn  The disc color (either BLACK or WHITE) of the current player.
   * @return The of the chosen move according to the strategy's logic.
   */
  Coordinate chooseMove(Reversi model, Disc turn);

}

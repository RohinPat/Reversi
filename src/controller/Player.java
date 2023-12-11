package controller;

import model.Position;
import model.Disc;
import model.Reversi;

/**
 * This is an interface that implements all the basic functionality that each.
 * Player type/ profile could have. These can include a human player, or AI.
 * Players with varying strategies.
 */
public interface Player {

  /**
   * Makes a move in the Reversi game, given the game model and a move Position.
   *
   * @param model The {@link Reversi} game model representing the current game state.
   * @param move  The {@link Position} representing the move to be made. If the player is
   *              unable to make a move, this parameter can be set to null.
   */
  void makeAMove(Reversi model, Position move);

  /**
   * Checks if it is currently the player's turn to make a move in the Reversi game.
   *
   * @param model The {@link Reversi} game model representing the current game state.
   * @return true if it is the player's turn to make a move, false otherwise.
   */
  boolean isPlayerTurn(Reversi model);

  /**
   * Retrieves the disc color associated with the player (either black or white).
   *
   * @return The {@link Disc} representing the color of the player's pieces.
   */
  Disc getDisc();

  /**
   * Passes the player's turn in the Reversi game. This method is called when a player chooses
   * to pass their turn instead of making a move.
   */
  void passTurn();

}

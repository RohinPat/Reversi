package controller;

import model.Disc;

/**
 * The {@code ControllerFeatures} interface defines the basic functionality that a Reversi
 * controller should provide to control the game's logic and interactions between the model and
 * view.
 */
public interface ControllerFeatures {

  /**
   * Confirms a move in the Reversi game. This method takes in the q,r axial coordinates of the.
   * selected hexagon and then attempts to carry out a move to that coordinate. Passes through.
   * Checks to make the move is legal.
   */
  void confirmMove(int q, int r);

  /**
   * Passes the current player's turn in the Reversi game. This method is called when a player
   * chooses to pass their turn instead of making a move.
   */
  void passTurn();

  /**
   * Retrieves the disc color associated with the current player.
   *
   * @return The {@link Disc} representing the color of the current player's pieces.
   */
  Disc getPlayer();

  /**
   * Retrieves the current player whose turn it is.
   *
   * @return The {@link Player} representing the current player.
   */
  Player getTurn();

  /**
   * Helps in the creation of an output log to ensure code runs as expected, and we can see the.
   * finer details of the AI decisions.
   *
   * @return the text output of the log that was accumulating through the running of the code
   */
  String getLog();

  /**
   * Updates the view to accurately represent the current state of the game.
   * This includes refreshing the game board to show the latest disc placements,
   * updating scores, and indicating the current player's turn. This method is
   * typically called after a move is made or the game state changes.
   */
  void updateView();

  /**
   * Handles actions required in the view when the turn changes from one player to another.
   * This method updates the view to reflect the new active player, indicated by the 'disc'
   * parameter.
   * It can trigger updating turn-related information displayed on the view.
   * @param disc The disc color (BLACK or WHITE) of the player whose turn is starting.
   */
  void handleTurnChange(Disc disc);
}

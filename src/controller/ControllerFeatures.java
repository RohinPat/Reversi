package controller;

import model.Disc;

/**
 * The {@code ControllerFeatures} interface defines the basic functionality that a Reversi
 * controller should provide to control the game's logic and interactions between the model and
 * view.
 */
public interface ControllerFeatures {

  /**
   * Handles the selection of a hexagon on the game board based on its coordinates (q, r).
   *
   * @param q The column coordinate of the selected hexagon.
   * @param r The row coordinate of the selected hexagon.
   */
  void selectHexagon(int q, int r);

  /**
   * Confirms a move in the Reversi game. This method is called when a player wants to make
   * a move after selecting a hexagon.
   */
  void confirmMove();

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
   * finer details of the AI decisions
   *
   * @return the text output of the log that was accumulating through the running of the code
   */
  String getLog();

  /**
   * Used to refresh / update the views.
   */
  void updateView();

  void handleTurnChange(Disc disc);
}

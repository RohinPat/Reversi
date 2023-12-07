package view;

import controller.ControllerFeatures;
import model.Coordinate;
import model.ReversiReadOnly;

public interface IBoardPanel {

  /**
   * Sets the controller for the board panel.
   *
   * @param cont The controller to be set.
   */
  void setController(ControllerFeatures cont);

  /**
   * Initializes the hexagonal grid on the game board based on the current game state.
   *
   * @param board The ReversiReadOnly board representing the current state of the game.
   */
  void initializeHexagons(ReversiReadOnly board);


  /**
   * Handles a mouse click event on a hexagon within the game board.
   *
   * @param mouseX The x-coordinate of the mouse click.
   * @param mouseY The y-coordinate of the mouse click.
   */
  void handleHexagonClick(int mouseX, int mouseY);

  /**
   * Displays a dialog box to inform the user of an invalid move.
   *
   * @param message The message to be displayed in the dialog box.
   */
  void showInvalidMoveDialog(String message);

}


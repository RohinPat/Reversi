package provider.view;

import provider.controller.ControllerTurnListener;

/**
 * The ReversiView interface represents the view component in the MVC architecture for the Reversi
 * game. It defines methods that a concrete implementation of a Reversi view must implement to
 * interact with the user interface and display the game state.
 */
public interface ReversiView extends ControllerTurnListener {

  /**
   * Displays or hides the user interface of the Reversi game.
   *
   * @param show {@code true} to display the user interface, {@code false} to hide it.
   */
  void display(boolean show);

  /**
   * Adds a listener for view-specific user interface features. The provided {@code ViewFeatures}
   * object defines the set of features that the view can interact with, such as handling player
   * input and updating the display based on game state changes.
   *
   * @param features The {@code ViewFeatures} object representing the set of view-specific
   *                 features.
   */
  void addFeaturesListener(Features features);

  /**
   * Alerts this view of an error message to be displayed. This error message is a result of a
   * failure during an action taken on the model.
   *
   * @param message the message to be displayed.
   */
  void alertErrorMessage(String message);
}

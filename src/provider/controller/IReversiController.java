package provider.controller;


import provider.view.ReversiView;
import provider.model.ReversiMutableModel;

/**
 * A controller that can be used to play a game of reversi. This controller handles/facilitates
 * interactions between the model, view, and one of the players of the Reversi game.
 */
public interface IReversiController extends ModelTurnListener {

  /**
   * Sets up this controller with the necessary information to play a gui-based game of reversi.
   * This method should connect this model, view, and player together so that they can properly
   * interact. In the event that the player is a human player, this method should set up the view to
   * allow inputs.
   *
   * @param player          the player being represented by this controller.
   * @param model           the model being played on.
   * @param view            the view that will display the model and any player-specific info.
   * @param humanController whether or not this controller should treat this player as a human.
   */
  void startController(Player player, ReversiMutableModel model,
      ReversiView view, boolean humanController);
}

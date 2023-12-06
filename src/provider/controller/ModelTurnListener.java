package provider.controller;

import provider.model.PlayerOwnership;

/**
 * Represents something listening for information from a Reversi Model. This object can be added to
 * the Reversi Model's list of subscribers through the method addTurnListener.
 */
public interface ModelTurnListener {

  /**
   * Alerts this listener of who's turn it is. PlayerOwnership.Player1 means it is player 1's turn,
   * PlayerOwnership.Player2 means it is player 2's turn, and PlayerOwnership.Unoccupied means the
   * game is over.
   *
   * @param turn the current turn of the reversi model calling this method.
   */
  void alertTurn(PlayerOwnership turn);
}

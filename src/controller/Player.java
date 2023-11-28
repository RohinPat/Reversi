package controller;

import model.Coordinate;
import model.Reversi;

/**
 * This is an interface that implements all the basic functionality that each.
 * Player type/ profile could have. These can include a human player, or AI.
 * Players with varying strategies.
 */
public interface Player {
  
  void makeAMove(Reversi model, Coordinate move);

  boolean isPlayerTurn(Reversi model);

  void passTurn();

}

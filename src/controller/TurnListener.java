package controller;

import model.Disc;

public interface TurnListener {
  void handleTurnChange(Disc currentPlayer);
}

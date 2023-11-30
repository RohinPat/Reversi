package controller;

import model.Disc;

public interface ControllerFeatures {
  void selectHexagon(int q, int r);
  void confirmMove();
  void passTurn();
  Disc getPlayer();
}

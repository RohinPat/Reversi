package controller;

import model.Board;
import view.BoardPanel;

public class AIController implements ControllerFeatures {
  private Board model;
  private BoardPanel view;
  private Player player;

  public AIController(Board model, BoardPanel view, Player player) {
    this.model = model;
    this.view = view;
    this.player = player;

  }

  @Override
  public void selectHexagon(int q, int r) {
    //
  }

  @Override
  public void confirmMove() {

  }

  @Override
  public void passTurn() {

  }
}

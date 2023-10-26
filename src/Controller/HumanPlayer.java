package Controller;

import Controller.Player;
import Model.Board;
import Model.Disc;

public class HumanPlayer implements Player {
  private Disc discColor;

  @Override
  public Disc getDiscColor() {
    return discColor;
  }

  @Override
  public void makeMove(Board board) {
    
  }
}
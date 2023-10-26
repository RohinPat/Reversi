package Player;

import Board.Board;
import Player.Player;
import Board.Disc;

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
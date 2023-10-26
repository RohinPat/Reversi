package Player;

import Board.Disc;
import Board.Board;

public interface Player {
  Disc getDiscColor();
  void makeMove(Board board);
}
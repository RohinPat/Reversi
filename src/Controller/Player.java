package Controller;

import Model.Board;
import Model.Disc;

public interface Player {
  Disc getDiscColor();
  void makeMove(Board board);
}
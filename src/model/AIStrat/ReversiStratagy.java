package model.AIStrat;

import model.Board;
import model.Coordinate;
import model.Turn;

public interface ReversiStratagy {
  Coordinate chooseMove(Board model, Turn turn);
}

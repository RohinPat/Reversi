package controller.AIStrat;

import model.Board;
import model.Coordinate;
import model.Disc;

public interface ReversiStratagy {

  Coordinate chooseMove(Board model, Disc turn);
  
}

package controller.aistrat;

import model.Coordinate;
import model.Disc;
import model.Reversi;

public interface ReversiStratagy {

  Coordinate chooseMove(Reversi model, Disc turn);

}

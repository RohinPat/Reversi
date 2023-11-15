package controller.aistrat;

import java.util.ArrayList;

import model.Board;
import model.Coordinate;
import model.Disc;

public interface ReversiStratagy {

  Coordinate chooseMove(Board model, Disc turn);

  Coordinate chooseMove(Board model, Disc turn, ArrayList<Coordinate> possibleMoves);

}

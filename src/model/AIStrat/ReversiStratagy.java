package model.AIStrat;

import java.util.ArrayList;

import model.Board;
import model.Coordinate;
import model.Turn;

public interface ReversiStratagy {

  Coordinate chooseMove(Board model, Turn turn);
  
  Coordinate chooseMove(Board model, Turn turn, ArrayList<Coordinate> possibleMoves);

}

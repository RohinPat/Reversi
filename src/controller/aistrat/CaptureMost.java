package controller.aistrat;

import model.Board;
import model.Disc;
import model.Turn;
import model.Coordinate;
import java.util.ArrayList;

public class CaptureMost implements ReversiStratagy {

 @Override
 public Coordinate chooseMove(Board model, Disc turn) {
   Turn t = null;
    if (turn == Disc.BLACK) {
      t = Turn.BLACK;
    }
    else {
      t = Turn.WHITE;
    }
    Board copy = new Board(model.getSize(), model.createCopyOfBoard(), t);
   ArrayList<Coordinate> moves = copy.getPossibleMoves();
   return chooseMoveHelper(model, turn, moves);
 }

 protected Coordinate chooseMoveHelper(Board model, Disc turn, ArrayList<Coordinate> possibleMoves) {
   int max = 0;
   Coordinate maxMove = null;
   Turn t = null;
    if (turn == Disc.BLACK) {
      t = Turn.BLACK;
    }
    else {
      t = Turn.WHITE;
    }
   for (Coordinate move : possibleMoves) {
     Board copy = new Board(model.getSize(), model.createCopyOfBoard(), t);
     copy.makeMove(move);
     int score = copy.getScore(turn);
     if (score > max) {
       max = score;
       maxMove = move;
     }
   }
   return maxMove;
 } 
}

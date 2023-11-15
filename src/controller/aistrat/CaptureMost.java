package controller.aistrat;

import model.Board;
import model.Disc;
import model.Coordinate;
import java.util.ArrayList;

public class CaptureMost implements ReversiStratagy {

 @Override
 public Coordinate chooseMove(Board model, Disc turn) {
   Board copy = model.createCopyOfBoard();
   ArrayList<Coordinate> moves = copy.getPossibleMoves();
   return chooseMoveHelper(model, turn, moves);
 }

 protected Coordinate chooseMoveHelper(Board model, Disc turn, ArrayList<Coordinate> possibleMoves) {
   int max = 0;
   Coordinate maxMove = null;
   for (Coordinate move : possibleMoves) {
     Board copy = model.createCopyOfBoard();
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

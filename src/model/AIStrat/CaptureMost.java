package model.AIStrat;

import java.util.ArrayList;

import model.Board;
import model.Coordinate;
import model.Turn;
import model.Disc;

public class CaptureMost implements ReversiStratagy{

    @Override
    public Coordinate chooseMove(Board model, Turn turn) {
      Board copy = model.createCopyOfBoard();
      ArrayList<Coordinate> moves = copy.getPossibleMoves();
      int max = 0;
      Coordinate maxMove = null;
      Disc d = null;
      if(turn == Turn.BLACK) {
        d = Disc.BLACK;
      } else {
        d = Disc.WHITE;
      }
      for(Coordinate move : moves) {
        Board copy2 = model.createCopyOfBoard();
        copy2.makeMove(move);
        int score = copy2.getScore(d);
        if(score > max) {
          max = score;
          maxMove = move;
        }
      }
      return maxMove;
    }
    
}

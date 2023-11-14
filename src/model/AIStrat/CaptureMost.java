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

    @Override
      public Coordinate chooseMove(Board model, Turn turn, ArrayList<Coordinate> possibleMoves) {
        Board copy = model.createCopyOfBoard();
        int max = 0;
        Coordinate maxMove = null;
        Disc d = null;
        if(turn == Turn.BLACK) {
          d = Disc.BLACK;
        } else {
          d = Disc.WHITE;
        }
        for(Coordinate move : possibleMoves) {
          Board copy2 = model.createCopyOfBoard();
          copy2.makeMove(move);
          int score = copy2.getScore(d);
          if(score > max) {
            max = score;
            maxMove = move;
          }
        }
        if (max == 0) {
          this.chooseMove(model, turn);
        }
        return maxMove;
      }
    
}

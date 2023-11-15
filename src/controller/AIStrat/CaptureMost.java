package controller.AIStrat;

import java.util.ArrayList;

import controller.AIStrat.ReversiStratagy;
import model.Board;
import model.Coordinate;
import model.Turn;
import view.BoardRenderer;
import model.Disc;

public class CaptureMost implements ReversiStratagy {

    @Override
    public Coordinate chooseMove(Board model, Disc turn) {
      Board copy = model.createCopyOfBoard();
      ArrayList<Coordinate> moves = copy.getPossibleMoves();
      int max = 0;
      Coordinate maxMove = null;
      for(Coordinate move : moves) {
        Board copy2 = model.createCopyOfBoard();
        copy2.makeMove(move);
        int score = copy2.getScore(turn);
        if(score > max) {
          max = score;
          maxMove = move;
        }
      }
      return maxMove;
    }

    @Override
      public Coordinate chooseMove(Board model, Disc turn, ArrayList<Coordinate> possibleMoves) {
        int max = 0;
        Coordinate maxMove = null;
        for(Coordinate move : possibleMoves) {
          Board copy = model.createCopyOfBoard();
          copy.makeMove(move);
          int score = copy.getScore(turn);
          if(score > max) {
            max = score;
            maxMove = move;
          }
        }
        if (max == 0) {
          return this.chooseMove(model, turn);
        }
        return maxMove;
      }
    
}

package controller.aistrat;

import model.Board;
import model.Coordinate;
import model.Disc;

public class TryTwo implements ReversiStratagy{
  ReversiStratagy first, second;  
  public Coordinate chooseMove(Board model, Disc turn) {
    Coordinate ans = this.first.chooseMove(model, turn);
    if (ans == null) {
      ans = this.second.chooseMove(model, turn);
    }
    return ans;
  }
}

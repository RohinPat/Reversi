package model.AIStrat;

import java.util.ArrayList;

import model.Board;
import model.Coordinate;
import model.Turn;

public class AvoidCorners implements ReversiStratagy{

  @Override
  public Coordinate chooseMove(Board model, Turn turn) {
    CaptureMost cm = new CaptureMost();
    int size = model.getSize();
    cm.chooseMove(model, turn);

  }

  private ArrayList<Coordinate> getSpotsToAvoid(int size) {
    ArrayList<Coordinate> corners = new ArrayList<Coordinate>();
    
  }
    
}

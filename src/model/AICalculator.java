package model;

import java.util.ArrayList;

public class AICalculator {
  private BoardReadOnly model;
  private ArrayList<Cell> possibleMoves;

  public AICalculator(Board model) {
    this.model = model;
    this.possibleMoves = this.model.getPossibleMoves();
  }

  public Coordinate captureMost() {
    for (Cell cell : possibleMoves) {
      
  }
}

package controller;

import model.Reversi;
import model.Disc;
import model.Coordinate;
import controller.aistrat.ReversiStratagy;

public class AIPlayer implements Player {
  private Disc playerDisc;
  private ReversiStratagy strategy;

  public AIPlayer(Disc playerDisc, ReversiStratagy strategy) {
    this.playerDisc = playerDisc;
    this.strategy = strategy;
  }

  @Override
  public void makeAMove(Reversi model, Coordinate coordinate) {
    Coordinate c1 =  strategy.chooseMove(model, playerDisc);
    if (c1.equals(new Coordinate(model.getSize(), model.getSize()))){
      model.passTurn();
    }
    model.makeMove(c1);
  }

  @Override
  public boolean isPlayerTurn(Reversi model) {
    return false;
  }

  @Override
  public Disc getDisc() {
    return playerDisc;
  }

  @Override
  public void passTurn() {

  }
}

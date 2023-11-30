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
    System.out.println(model.getConsecPasses() + "tism1");
    Coordinate c1 =  strategy.chooseMove(model, playerDisc);
    if (c1.equals(new Coordinate(model.getSize(), model.getSize()))){
      System.out.println("AI passed turn");
      model.passTurn();
    }
    model.makeMove(c1);
    System.out.println(model.getConsecPasses() + "tism2");
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

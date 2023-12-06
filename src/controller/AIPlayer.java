package controller;

import model.Reversi;
import model.Disc;
import model.Coordinate;
import controller.aistrat.ReversiStratagy;
import provider.strategies.InFallableReversiStrategy;
import provider.strategies.TopLeftTieBreaker;
import controller.StratagyAdapter;

/**
 * The {@code AIPlayer} class represents an AI player in the Reversi game.
 * It implements the {@link Player} interface and provides methods for making moves
 * based on a specified strategy.
 */
public class AIPlayer implements Player {
  private Disc playerDisc;
  private ReversiStratagy strategy;

  /**
   * Constructs an {@code AIPlayer} with the specified player disc color and strategy.
   *
   * @param playerDisc The {@link Disc} representing the color of the AI player's pieces
   *                   (BLACK or WHITE).
   * @param strategy   The {@link ReversiStratagy} defining the strategy used by this AI player.
   */
  public AIPlayer(Disc playerDisc, ReversiStratagy strategy) {
    this.playerDisc = playerDisc;
    this.strategy = strategy;
  }

  public AIPlayer(Disc playerDisc, InFallableReversiStrategy infallibleStrategy, TopLeftTieBreaker tieBreaker) {
    this.playerDisc = playerDisc;
    this.strategy = new StratagyAdapter(infallibleStrategy, tieBreaker);
  }

  @Override
  public void makeAMove(Reversi model, Coordinate coordinate) {
    Coordinate c1 = strategy.chooseMove(model, playerDisc);
    if (c1.equals(new Coordinate(model.getSize(), model.getSize()))) {
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
    // we left it blank as our makeAMove method handles the passing as well by returning a
    // special output that is read and understood as a pass (it is because our AI's only return
    // coordinates)
  }
}

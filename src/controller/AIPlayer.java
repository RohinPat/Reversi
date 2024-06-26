package controller;

import model.Coordinate;
import model.Position;
import model.Reversi;
import model.Disc;
import controller.aistrat.ReversiStratagy;
import provider.strategies.InFallableReversiStrategy;

/**
 * The {@code AIPlayer} class represents an AI player in the Reversi game.
 * It implements the {@link Player} interface and provides methods for making moves
 * based on a specified strategy.
 */
public class AIPlayer implements Player {
  private final Disc playerDisc;
  private final ReversiStratagy strategy;

  /**
   * Constructs an {@code AIPlayer} with the specified player disc and strategy.
   *
   * @param playerDisc The {@link Disc} representing the color of the AI player's pieces
   *                   (BLACK or WHITE).
   * @param strategy   The {@link ReversiStratagy} defining the strategy used by this AI player.
   */
  public AIPlayer(Disc playerDisc, ReversiStratagy strategy) {
    this.playerDisc = playerDisc;
    this.strategy = strategy;
  }

  /**
   * Constructs an {@code AIPlayer} with the specified player disc color and an infallible strategy.
   * This constructor adapts an {@link InFallableReversiStrategy} into a regular.
   * {@link ReversiStratagy} so it can be used by the AI player. This allows the AI player to.
   * utilize advanced, error-proof strategies and helps us combine the provider's strategies.
   *
   * @param playerDisc The {@link Disc} representing the color of the AI player's pieces
   *                   (BLACK or WHITE).
   * @param infallibleStrategy The {@link InFallableReversiStrategy} defining the advanced.
   *                           error-proof strategy used by this AI player. This strategy is.
   *                           adapted to a standard {@link ReversiStratagy} format for.
   *                           compatibility with the AI player.
   */
  public AIPlayer(Disc playerDisc, InFallableReversiStrategy infallibleStrategy) {
    this.playerDisc = playerDisc;
    this.strategy = new StratagyAdapter(infallibleStrategy);
  }

  @Override
  public void makeAMove(Reversi model, Position coordinate) {
    Position c1 = strategy.chooseMove(model, playerDisc);
    if (!model.isGameOver()
            && (c1.equals(new Coordinate(model.getSize(), model.getSize())))) {
      model.passTurn();
    }
    if (model.validMove(c1, playerDisc)) {
      model.makeMove(c1);
    }
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
  public void passTurn(Reversi model) {
    // we left it blank as our makeAMove method handles the passing as well by returning a
    // special output that is read and understood as a pass (it is because our AI's only return
    // coordinates)
  }
}

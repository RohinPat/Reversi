package controller;

import java.util.List;

import model.Reversi;
import controller.aistrat.ReversiStratagy;
import model.Coordinate;
import model.Disc;
import model.ReversiReadOnly;
import provider.model.HexCoord;
import provider.model.PlayerOwnership;
import provider.strategies.InFallableReversiStrategy;
import provider.strategies.StrategyWrapper;
import provider.strategies.TopLeftTieBreaker;

/**
 * Adapter class that bridges the provider's infallible strategy interface with the Reversi game's
 * strategy interface. It adapts the provider's strategy to be compatible with the Reversi game,
 * allowing for seamless strategy execution within the game's context.
 */
public class StratagyAdapter implements ReversiStratagy {

  private final InFallableReversiStrategy stratagy;
  private TopLeftTieBreaker tieBreaker;

  /**
   * Constructs a StrategyAdapter with a given infallible strategy from the provider.
   * This adapter allows the integration of the provider's strategic logic into the Reversi game,
   * enhancing the decision-making process with external strategy implementation.
   *
   * @param stratagy The infallible strategy implementation provided by the external provider.
   */
  public StratagyAdapter(InFallableReversiStrategy stratagy) {
    this.stratagy = stratagy;
    this.tieBreaker = new TopLeftTieBreaker();
  }

  @Override
  public Coordinate chooseMove(ReversiReadOnly model, Disc turn) {
    PlayerOwnership ownership = discToOwnership(turn);
    ReversiModelAdapter modelAdapter = new ReversiModelAdapter((Reversi) model);
    StrategyWrapper wrapper = new StrategyWrapper(stratagy);

    // Debugging: Check if the strategy is executed correctly
    List<HexCoord> possibleMoves = wrapper.executeStrategy(modelAdapter, ownership);

    if (possibleMoves == null || possibleMoves.isEmpty()) {
      return new Coordinate(model.getSize(), model.getSize()); // or handle this case as appropriate
    }

    // Debugging: Check if tiebreaker is called
    HexCoord output = tieBreaker.breakTie(possibleMoves);

    return new Coordinate(output.q, output.r);
  }


  /**
   * Converts the disc representation of a player's turn in the Reversi game model
   * to the PlayerOwnership representation used by the provider.
   * This conversion is crucial for aligning the game's internal logic with the
   * external provider's strategy interface.
   *
   * @param disc The Disc enum value representing a player in the Reversi game.
   * @return The corresponding PlayerOwnership enum value used by the provider.
   * @throws IllegalArgumentException if the disc parameter does not correspond
   *                                  to a valid player or state in the game.
   */
  private PlayerOwnership discToOwnership(Disc disc) {
    if (disc == Disc.BLACK) {
      return PlayerOwnership.PLAYER_1;
    } else if (disc == Disc.WHITE) {
      return PlayerOwnership.PLAYER_2;
    } else if (disc == Disc.EMPTY) {
      return PlayerOwnership.UNOCCUPIED;
    } else {
      throw new IllegalArgumentException("Invalid disc");
    }
  }
}

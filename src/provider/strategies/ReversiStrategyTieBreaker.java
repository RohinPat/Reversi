package provider.strategies;

import java.util.List;

import provider.model.HexCoord;

/**
 * Represents a tie breaker that returns the hexcoord most suited for a player to move too based on
 * the logic of the tie breaker. Typically, the results of the reversi strategy wrapper would be
 * passed into this.
 */
public interface ReversiStrategyTieBreaker {

  /**
   * Returns the hexcoord that fits the conditions of the tie breaker, if there are more than one.
   *
   * @param results the input for the tie breaker. Likely an output of a strategy wrapper.
   * @return the hexcoord fitting the logic of the tiebreaker.
   */
  HexCoord breakTie(List<HexCoord> results);
}

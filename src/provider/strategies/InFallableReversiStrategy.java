package provider.strategies;

import java.util.List;


import provider.Pair;
import provider.model.HexCoord;
import provider.model.PlayerOwnership;
import provider.model.ReversiReadOnlyModel;

/**
 * Represents a reversi strategy that should not fail, always managing to find at least on move that
 * suits its requirements.
 */
public interface InFallableReversiStrategy {


  /**
   * Returns a list of Pairs containing a coordinate matching the strategy's requirements and its
   * corresponding score.
   *
   * @param model  the model that the moves are being made on, so the strategy relies on the current
   *               boear state of the model.
   * @param player the player that is attempting to make a move on the given model using this
   *               strategy.
   * @return a list of the coordinates and their corresponding scores that match this strategy.
   *     should not be empty.
   * @throws IllegalStateException    if it is not that player's turn, or the model hasn't been
   *                                  started yet
   * @throws IllegalArgumentException if either input is null
   */
  List<Pair<HexCoord, Integer>> executeStrategyGivenMoves(ReversiReadOnlyModel model,
                                                          PlayerOwnership player);


}

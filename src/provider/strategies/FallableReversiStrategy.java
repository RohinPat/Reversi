package provider.strategies;

import java.util.List;
import java.util.Optional;

import provider.Pair;
import provider.model.HexCoord;
import provider.model.PlayerOwnership;
import provider.model.ReversiReadOnlyModel;

/**
 * Represents a reversi strategy that has a chance of failing. A failing strategy means that no
 * moves on the given model with the given player meet the requirements of that strategy.
 */
public interface FallableReversiStrategy {

  /**
   * Optionally returns a list of pairs representing coordinates that meet the requirements of this
   * strategy.
   *
   * @param model  the reversi model this strategy is being performed on.
   * @param player the player that is performing this strategy on the given board.
   * @return a list of coordinates and their scores. If there are none matching the requirements of
   * the strategy, return an empty optional.
   * @throws IllegalStateException    if it is not that player's turn, or the model hasn't been
   *                                  started yet
   * @throws IllegalArgumentException if either input is null
   */
  Optional<List<Pair<HexCoord, Integer>>> executeStrategyGivenMoves(ReversiReadOnlyModel model,
                                                                    PlayerOwnership player);
}

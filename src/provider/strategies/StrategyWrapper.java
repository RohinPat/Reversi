package provider.strategies;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import provider.Pair;
import provider.model.HexCoord;
import provider.model.PlayerOwnership;
import provider.model.ReversiReadOnlyModel;

/**
 * A helper class for the strategy pattern. Used to unwrap the return contents of an
 * InfallableStrategy, returning only the coordinates that conform with that strategy.
 */
public class StrategyWrapper {

  private final InFallableReversiStrategy strategy;

  /**
   * Default constructor that takes in an infallable strategy that should never fail.
   *
   * @param strategy the strategy that should be used by this strategy wrapper.
   */
  public StrategyWrapper(InFallableReversiStrategy strategy) {
    this.strategy = Objects.requireNonNull(strategy);
  }

  /**
   * Returns the results of executing the strategy stored within given a model and a player.
   *
   * @param model  the model that the strategy is being performed upon.
   * @param player the player that is performing the strategy.
   * @return the list of coordinates that conform to the player and the strategy and the board.
   * @throws NullPointerException  if model or player are null.
   * @throws IllegalStateException if the results of the strategy are empty. An infallable strategy
   *                               shouldn't yield no results. Also throws if the model isn't
   *                               started, or if it isn't the player's turn.
   */
  public List<HexCoord> executeStrategy(ReversiReadOnlyModel model, PlayerOwnership player) {
    Objects.requireNonNull(model);
    Objects.requireNonNull(player);
    List<Pair<HexCoord, Integer>> results = this.strategy.executeStrategyGivenMoves(model, player);
    return results.stream().map((pair) -> pair.value1).collect(Collectors.toList());
  }


}
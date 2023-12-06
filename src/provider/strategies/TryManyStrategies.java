package provider.strategies;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import provider.Pair;
import provider.model.HexCoord;
import provider.model.PlayerOwnership;
import provider.model.ReversiReadOnlyModel;

/**
 * Represents a fallable reversi strategy that has the ability to try many strategies. If the first
 * strategy doesn't work, it moves onto the next one, etc.
 */
public class TryManyStrategies implements FallableReversiStrategy {

  private final List<FallableReversiStrategy> strategies;

  /**
   * Defualt constructor that takes in the fallable strategies to use.
   *
   * @param strategies to be tried.
   */
  public TryManyStrategies(List<FallableReversiStrategy> strategies) {
    this.strategies = Objects.requireNonNull(strategies);
  }

  /**
   * Has the potential to return a list of pairs of hexcoords and their scores matching one of the
   * strategies stored in this list. If none of the strategies yield a non-empty option, returns an
   * empty optional. Goes in order of the beginning of the list. If the first strategy fails, moves
   * onto the next one, etc.
   *
   * @param model  the reversi model this strategy is being performed on.
   * @param player the player that is performing this strategy on the given board.
   * @return optionally the result of the first working strategy in the list, or an empty if none of
   * the strategies work.
   */
  @Override
  public Optional<List<Pair<HexCoord, Integer>>> executeStrategyGivenMoves(
      ReversiReadOnlyModel model,
      PlayerOwnership player) {
    Objects.requireNonNull(model);
    Objects.requireNonNull(player);
    Optional<List<Pair<HexCoord, Integer>>> strategicMoves;

    for (FallableReversiStrategy strat : this.strategies) {
      strategicMoves = strat.executeStrategyGivenMoves(model, player);
      if (strategicMoves.isPresent()) {
        return strategicMoves;
      }
    }
    return Optional.empty();
  }
}
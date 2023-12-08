package provider.strategies;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import provider.Pair;
import provider.model.HexCoord;
import provider.model.PlayerOwnership;
import provider.model.ReversiReadOnlyModel;

/**
 * Represents an Infallable Strategy where the first option is a fallable strategy, and if that
 * doesn't work, it falls back to an infallable strategy.
 */
public class FallableInfallablePairStrategy implements InFallableReversiStrategy {

  private FallableReversiStrategy fallable;
  private InFallableReversiStrategy inFallable;

  /**
   * Default constructor.
   *
   * @param fallable   fallable strategy to be tried first.
   * @param inFallable infallable strategy to fall back on.
   */
  public FallableInfallablePairStrategy(FallableReversiStrategy fallable,
      InFallableReversiStrategy inFallable) {
    this.fallable = Objects.requireNonNull(fallable);
    this.inFallable = Objects.requireNonNull(inFallable);
  }

  /**
   * Attempts to get a result from the first, fallable strategy. If this doesnt work, returns the
   * result of the second, infallable strategy.
   *
   * @param model  the model that the moves are being made on, so the strategy relies on the current
   *               boear state of the model.
   * @param player the player that is attempting to make a move on the given model using this
   *               strategy.
   * @return a list of pairs of hexcoords and their scores that fit this strategy
   */
  @Override
  public List<Pair<HexCoord, Integer>> executeStrategyGivenMoves(ReversiReadOnlyModel model,
                                                                 PlayerOwnership player) {
    Objects.requireNonNull(model);
    Objects.requireNonNull(player);

    Optional<List<Pair<HexCoord, Integer>>> strategicMoves =
            fallable.executeStrategyGivenMoves(model, player);

    if (strategicMoves.isPresent()) {
      return strategicMoves.get();
    }
    return inFallable.executeStrategyGivenMoves(model, player);
  }
}
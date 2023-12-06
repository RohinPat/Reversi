package provider.strategies;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import provider.Pair;
import provider.model.HexCoord;
import provider.model.PlayerOwnership;
import provider.model.ReversiReadOnlyModel;

/**
 * Represents a strategy that only tries to find available moves on corners of the given model.
 * Corners are a good place to play because they can't be captured.
 */
public class PlayCornersStrategy implements FallableReversiStrategy {

  /**
   * Optionally returns a list of pairs representing coordinates that are in corners.
   *
   * @param model  the reversi model this strategy is being performed on.
   * @param player the player that is performing this strategy on the given board.
   * @return a list of coordinates and their scores. If there are none in this corner, return an
   * empty optional.
   */
  @Override
  public Optional<List<Pair<HexCoord, Integer>>> executeStrategyGivenMoves(
      ReversiReadOnlyModel model,
      PlayerOwnership player) {
    Objects.requireNonNull(model);
    Objects.requireNonNull(player);
    List<HexCoord> cornerTiles = new GetCornersForBoard().getCorners(model.getBoard());
    List<Pair<HexCoord, Integer>> movesInCorners = new ArrayList<>();
    List<HexCoord> possibleMoves = new PossibleMoveGenerator().getPossibleMoves(model, player);
    for (HexCoord coord : possibleMoves) {
      if (cornerTiles.contains(coord)) {
        movesInCorners.add(new Pair<>(coord, 1));
      }
    }

    if (movesInCorners.isEmpty()) {
      return Optional.empty();
    }
    return Optional.of(movesInCorners);
  }
}

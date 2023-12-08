package provider.strategies;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;


import provider.Pair;
import provider.model.HexCoord;
import provider.model.HexDirection;
import provider.model.PlayerOwnership;
import provider.model.ReversiReadOnlyModel;


/**
 * Represents a fallable strategy that returns all coordinates that the given player can play on
 * that aren't next to corners on the given model.
 */
public class AvoidTilesNextToCornersStrategy implements FallableReversiStrategy {

  /**
   * Optionally returns a list of pairs that are not next to corners. If there are none, returns an
   * empty optional.
   *
   * @param model  the reversi model this strategy is being performed on.
   * @param player the player that is performing this strategy on the given board.
   * @return a list of coordinates and their scores. If there are none matching the requirements of
   *     the strategy, return an empty optional.
   */
  @Override
  public Optional<List<Pair<HexCoord, Integer>>> executeStrategyGivenMoves(
      ReversiReadOnlyModel model,
      PlayerOwnership player) {
    Objects.requireNonNull(model);
    Objects.requireNonNull(player);
    List<Pair<HexCoord, Integer>> notInCorners = new ArrayList<>();
    Map<HexCoord, Boolean> tilesNextToCorners = this.tilesNextToCorners(model);
    List<HexCoord> possibleMoves = new PossibleMoveGenerator().getPossibleMoves(model, player);
    for (HexCoord coord : possibleMoves) {
      if (!tilesNextToCorners.containsKey(coord)) {
        notInCorners.add(new Pair<>(coord, 100));
      }
    }

    if (notInCorners.isEmpty()) {
      return Optional.empty();
    } else {
      return Optional.of(notInCorners);
    }
  }

  /**
   * Gets the tiles that are next to corners on the given model.
   *
   * @param model that the tiles next to corners are being grabbed from.
   * @return a hashmap that contains only tiles next to corners.
   */
  private Map<HexCoord, Boolean> tilesNextToCorners(ReversiReadOnlyModel model) {
    List<HexCoord> corners = new GetCornersForBoard().getCorners(model.getBoard());
    List<HexDirection> directions = model.getBoard().getDirections();
    Map<HexCoord, Boolean> tilesNextToCorners = new HashMap<>();

    for (HexDirection direction : directions) {

      for (HexCoord corner : corners) {
        tilesNextToCorners.put(direction.addDirectionToCoord(corner), true);
      }
    }

    return tilesNextToCorners;
  }
}

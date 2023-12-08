package provider.strategies;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import provider.model.HexCoord;
import provider.model.PlayerOwnership;
import provider.model.ReversiReadOnlyModel;

/**
 * Used to get the possible coordinate moves for a model state and a player.
 */
public class PossibleMoveGenerator {

  /**
   * Gets a list of coordinates where the given player can play on the given model. These
   * coordinates must follow the logic of the model.
   *
   * @param model  that is being played on.
   * @param player that is trying to play on the model.
   * @return a list of all possible VALID moves for the given player on the given model.
   */
  public List<HexCoord> getPossibleMoves(ReversiReadOnlyModel model, PlayerOwnership player) {

    Objects.requireNonNull(model);
    Objects.requireNonNull(player);
    List<HexCoord> results = new ArrayList<>();
    for (HexCoord coord : model.getBoard().getMap().keySet()) {
      if (model.isMoveAllowed(coord, player)) {
        results.add(coord);
      }
    }

    return results;
  }
}
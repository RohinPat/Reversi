package provider.strategies;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;


import provider.Pair;
import provider.model.HexCoord;
import provider.model.PlayerOwnership;
import provider.model.ReversiMutableModel;
import provider.model.ReversiReadOnlyModel;

/**
 * Represents an infallable strategy that selects coordinates that yield the player the most
 * possible points. This object is infallable because as long as the player has possible moves,
 * there is a move with a max score.
 */
public class MostPointsGainedStrategy implements InFallableReversiStrategy {

  /**
   * Returns a list of pairs that meet the requirement of providing the maximum score game for the
   * player. The maximum score is the greatest positive difference between the player's score before
   * the move and after the move.
   *
   * @param model  the reversi model this strategy is being performed on.
   * @param player the player that is performing this strategy on the given board.
   * @return a list of coordinates and their scores that match the requirement of the highest score.
   */
  @Override
  public List<Pair<HexCoord, Integer>> executeStrategyGivenMoves(ReversiReadOnlyModel model,
                                                                 PlayerOwnership player) {

    Objects.requireNonNull(model);
    Objects.requireNonNull(player);
    List<Pair<HexCoord, Integer>> coordsThatGainMostPoints = new ArrayList<>();
    int maxPointsGained = 0;
    List<HexCoord> possibleMoves = new PossibleMoveGenerator().getPossibleMoves(model, player);
    for (HexCoord coord : possibleMoves) {
      ReversiMutableModel modelClone = model.cloneModel();
      Map m = modelClone.getBoard().getMap();
      int pointsBeforeMove = modelClone.countClaimedTiles(player);
      modelClone.placeDisk(coord, player);
      int pointsGained = modelClone.countClaimedTiles(player) - pointsBeforeMove;
      if (pointsGained == maxPointsGained) {
        coordsThatGainMostPoints.add(new Pair<>(coord, pointsGained));
      } else if (pointsGained > maxPointsGained) {
        maxPointsGained = pointsGained;
        coordsThatGainMostPoints = new ArrayList<>();
        coordsThatGainMostPoints.add(new Pair<>(coord, pointsGained));
      }
    }

    return coordsThatGainMostPoints;
  }
}
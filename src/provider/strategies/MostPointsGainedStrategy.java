package provider.strategies;

import java.util.ArrayList;
import java.util.List;
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
                                                                 PlayerOwnership player){
    System.out.println("tism0");
    Objects.requireNonNull(model);
    System.out.println("tism1");
    Objects.requireNonNull(player);
    System.out.println("tism2");
    List<Pair<HexCoord, Integer>> coordsThatGainMostPoints = new ArrayList<>();
    System.out.println("tism3");
    int maxPointsGained = 0;
    System.out.println("tism4");
    List<HexCoord> possibleMoves = new PossibleMoveGenerator().getPossibleMoves(model, player);
    System.out.println("tism5");
    for (HexCoord coord : possibleMoves) {
      System.out.println("tism6");
      ReversiMutableModel modelClone = model.cloneModel();
      System.out.println("tism7");
      int pointsBeforeMove = modelClone.countClaimedTiles(player);
      System.out.println("tism8");
      modelClone.placeDisk(coord, player);
      System.out.println("tism9");
      int pointsGained = modelClone.countClaimedTiles(player) - pointsBeforeMove;
      System.out.println("tism10");
      if (pointsGained == maxPointsGained) {
        System.out.println("in If tism0");
        coordsThatGainMostPoints.add(new Pair<>(coord, pointsGained));
      } else if (pointsGained > maxPointsGained) {
        System.out.println("in else If tism1");
        maxPointsGained = pointsGained;
        coordsThatGainMostPoints = new ArrayList<>();
        coordsThatGainMostPoints.add(new Pair<>(coord, pointsGained));
      }
    }
    
    return coordsThatGainMostPoints;
  }
}
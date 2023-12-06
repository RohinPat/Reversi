package provider.strategies;

import java.util.ArrayList;
import java.util.List;

import provider.Pair;
import provider.model.HexCoord;
import provider.model.HexDirection;
import provider.model.IBoard;

/**
 * A utility class for obtaining corner coordinates on a given game board. This class provides a
 * method to identify and retrieve coordinates that are considered corners based on the number of
 * neighboring positions.
 */
public class GetCornersForBoard {

  /**
   * Gets a list of corner coordinates on the provided game board. Corners are identified based on
   * the number of neighboring positions in different directions.
   *
   * @param board The game board for which corners are to be identified.
   * @return A list of corner coordinates on the game board.
   */
  public List<HexCoord> getCorners(IBoard board) {

    List<Pair<HexCoord, Integer>> numberOfNeighborsForCoord = new ArrayList<>();
    List<HexDirection> directions = board.getDirections();

    for (HexCoord coord : board.getMap().keySet()) {
      int count = 0;
      for (HexDirection direction : directions) {
        HexCoord temp = direction.addDirectionToCoord(coord);
        if (board.getMap().containsKey(temp)) {
          count += 1;
        }
      }
      numberOfNeighborsForCoord.add(new Pair<>(coord, count));
    }

    int min = Integer.MAX_VALUE;
    List<HexCoord> results = new ArrayList<>();
    for (Pair<HexCoord, Integer> pair : numberOfNeighborsForCoord) {
      if (pair.value2 < min) {
        results = new ArrayList<>();
        min = pair.value2;
        results.add(pair.value1);
      } else if (pair.value2 == min) {
        results.add(pair.value1);
      }
    }
    return results;

  }
}
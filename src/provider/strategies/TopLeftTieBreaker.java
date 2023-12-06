package provider.strategies;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import provider.model.HexCoord;

/**
 * Represents a tie breaker where the returned HexCoord is the one that is closest to the top-left
 * of the model.
 */
public class TopLeftTieBreaker implements ReversiStrategyTieBreaker {

  /**
   * Returns the hexcoord closest to the top left of the model. In this case, that would be the
   * hexcoord with the smallest r and then q value.
   *
   * @param results the input for the tie breaker. Likely an output of a strategy wrapper.
   * @return the hexcoord with the smalles r and q values (r comes first).
   * @throws IllegalArgumentException if the input list is empty.
   */
  @Override
  public HexCoord breakTie(List<HexCoord> results) {
    Objects.requireNonNull(results);
    if (results.isEmpty()) {
      throw new IllegalArgumentException("There is no coord fitting this tiebreaker");
    }
    if (results.size() == 1) {
      return results.get(0);
    } else {
      results.sort(new SortHexCoordsByTopLeft());
      return results.get(0);

    }
  }

  /**
   * Sorts the hexcoord by top left first. If the two coords have different r values, the one with
   * the smaller r value comes first. If they have the same r value, the one with the smaller q
   * value comes first.
   */
  private static class SortHexCoordsByTopLeft implements Comparator<HexCoord> {

    /**
     * Compares two hexcoords and returns a negative if the first is closer to the top-left of the
     * map than the second.
     *
     * @param coord1 the first object to be compared.
     * @param coord2 the second object to be compared.
     * @return negative value if obj 1 is closer to top left, pos value if obj 2 is closer to top
     * left, or 0 if same.
     */
    @Override
    public int compare(HexCoord coord1, HexCoord coord2) {

      if (coord1.r < coord2.r) {
        return -1;
      } else if (coord2.r < coord1.r) {
        return 1;
      } else {
        return coord1.q - coord2.q;
      }
    }
  }
}

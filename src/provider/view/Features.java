package provider.view;

import provider.model.HexCoord;
import java.util.Optional;

/**
 * The {@code ViewFeatures} interface represents the set of features that a Reversi view can
 * interact with, defining methods for handling player moves and passes.
 */
public interface Features {

  /**
   * Attempts to make a move on the Reversi game board with the specified hexagonal coordinate.
   *
   * @param hc An optional hexagonal coordinate representing the intended move. If empty, the move
   *           doesn't exist. This covers the case where a view attempts to submit an empty
   *           coordinate when one isn't selected.
   */
  void attemptMove(Optional<HexCoord> hc);

  /**
   * Attempts to pass the current player's turn in the Reversi game.
   */
  void attemptPass();
}

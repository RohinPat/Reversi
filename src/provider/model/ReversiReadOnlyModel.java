package provider.model;

/**
 * Represents a read-only view of a Reversi game model. This interface provides methods to observe
 * the model such as scoring, determining if the game is over, if moves are legal, etc.
 */
public interface ReversiReadOnlyModel {

  /**
   * Gets the game board.
   *
   * @return The game board, which can make further observations.
   * @throws IllegalStateException if the game has not been started
   */
  IBoard getBoard() throws IllegalStateException;

  /**
   * Checks if the game is over.
   *
   * @return True if the game is over, false otherwise.
   * @throws IllegalStateException if the game has not been started
   */
  boolean isGameOver() throws IllegalStateException;

  /**
   * Counts the number of tiles claimed by the specified player.
   *
   * @param player The player for whom you want to count claimed tiles.
   * @return The number of tiles claimed by the player.
   * @throws IllegalStateException    if the game hasn't started yet
   * @throws IllegalArgumentException if the player is neither of the game's players
   */
  int countClaimedTiles(PlayerOwnership player)
      throws IllegalStateException, IllegalArgumentException;

  /**
   * Checks if it is the turn of the given player.
   *
   * @param player to be checked if it is their turn.
   * @return true if it is the given player's turn.
   * @throws IllegalStateException    if the game hasn't started yet
   * @throws IllegalArgumentException if the player is neither of the game's players
   */
  boolean isPlayerTurn(PlayerOwnership player)
      throws IllegalArgumentException, IllegalStateException;

  /**
   * Checks if the attempted move by the given player is allowed.
   *
   * @param player The player for whom you want to check if their move is allowed.
   * @param hc     The coordinates where the disk could be placed.
   * @return true if the move is allowed
   * @throws IllegalStateException    if the game hasn't started yet or if it is not the player's
   *                                  turn
   * @throws IllegalArgumentException if the player is neither of the game's players
   */
  boolean isMoveAllowed(HexCoord hc, PlayerOwnership player)
      throws IllegalArgumentException, IllegalStateException;

  /**
   * Creates and returns a deep copy of the current Reversi model. The cloned model is independent
   * of the original, allowing for separate game instances.
   *
   * @return A deep copy of the current Reversi model.
   * @throws IllegalStateException if the game has not been started
   */
  ReversiMutableModel cloneModel();

  /**
   * Checks if the attempted move by the given player is allowed, without considering the turn
   * order. This method is useful for checking move validity in scenarios where the turn order is
   * not relevant, such as minimax
   *
   * @param hc              The coordinates where the disk could be placed.
   * @param playerOwnership The player for whom you want to check if their move is allowed.
   * @return true if the move is allowed, irrespective of the turn order.
   * @throws IllegalArgumentException if the player is neither of the game's players.
   * @throws IllegalStateException    if the game hasn't started yet.
   * @see cs3500.reversi.player.MinimaxStrategy
   */
  boolean isMoveAllowedTurnIndependent(HexCoord hc, PlayerOwnership playerOwnership)
      throws IllegalArgumentException, IllegalStateException;

}
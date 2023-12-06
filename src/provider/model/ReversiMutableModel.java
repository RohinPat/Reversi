package provider.model;


import provider.controller.ModelTurnListener;

/**
 * Represents a mutable view of a Reversi game model. This interface extends the read-only view and
 * provides methods for making mutations to the model, along with any read-only methods from the
 * parent interface.
 */
public interface ReversiMutableModel extends ReversiReadOnlyModel {

  /**
   * Starts a new Reversi game with the provided board and players.
   *
   * @param board The initial game board.
   * @throws IllegalStateException    if the game is already started
   * @throws IllegalArgumentException if the provided board or players are null
   */
  void startGame(IBoard board)
      throws IllegalStateException, IllegalArgumentException;

  /**
   * Places a disk on the game board at the specified coordinates for the given player.
   * Automatically passes the turn to the other player.
   *
   * <p>
   * Note: If the turn is passed to the other player and they cannot play, the turn is passed back.
   * If that player also cannot play, the turn is passed back again (implying the game is over)
   * </p>
   *
   * @param hc     The coordinates where the disk should be placed.
   * @param player The player who is making the move.
   * @throws IllegalStateException    if the game has not started, if it is not the player's turn,
   *                                  or if the player cannot place a disk here
   * @throws IllegalArgumentException if the tile doesn't exist on the board, if the player is
   *                                  neither of the game's players
   */
  void placeDisk(HexCoord hc, PlayerOwnership player)
      throws IllegalStateException, IllegalArgumentException;

  /**
   * Passes the turn to the next player.
   *
   * @param player The player who is passing their turn.
   * @throws IllegalStateException    if the game has not started, if it is not the player's turn
   * @throws IllegalArgumentException if the player is neither of the game's players
   */
  void pass(PlayerOwnership player) throws IllegalStateException, IllegalArgumentException;

  /**
   * Adds a model turn listener (a subscriber) to this model. Once added, the turn listener will be
   * notified of the current player's turn every time the model changes states.
   *
   * @param listener the listener to be added to the model.
   */
  void addTurnListener(ModelTurnListener listener);
}
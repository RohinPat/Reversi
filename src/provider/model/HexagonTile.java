package provider.model;

import java.util.Objects;

/**
 * The `HexagonTile` class represents a hexagonal tile in a Reversi game. Each tile can be owned by
 * a player or unowned. This class is used to manage the ownership of the tile and provides methods
 * for updating and retrieving the owner of the tile.
 */
public class HexagonTile {

  private PlayerOwnership playerOwnership;

  /**
   * Constructs a new `HexagonTile` with the specified player ownership.
   *
   * @param playerOwnership The ownership status of the tile.
   * @throws NullPointerException if `playerOwnership` is `null`.
   */
  public HexagonTile(PlayerOwnership playerOwnership) {
    this.playerOwnership = Objects.requireNonNull(playerOwnership);
  }

  /**
   * Get the player ownership status of this tile.
   *
   * @return The current player ownership of the tile.
   */
  public PlayerOwnership getPlayerOwnership() {
    return playerOwnership;
  }

  /**
   * Update the player ownership of this tile.
   *
   * @param player The new owner of the tile.
   */
  void updatePlayerOwnership(PlayerOwnership player) {
    this.playerOwnership = player;
  }

  /**
   * Returns a string representation of this tile, which is the string representation of its player
   * ownership.
   *
   * @return A string representation of the tile's ownership status.
   */
  @Override
  public String toString() {
    return this.playerOwnership.toString();
  }
}

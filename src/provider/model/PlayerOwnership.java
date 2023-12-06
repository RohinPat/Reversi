package provider.model;

/**
 * Enumerates the ownership status of players and unoccupied tiles in a Reversi game.
 */
public enum PlayerOwnership {
  PLAYER_1("X"), PLAYER_2("O"), UNOCCUPIED("_");
  private final String stringRepresentation;

  /**
   * Constructs a new player ownership status with the specified string representation.
   *
   * @param stringRepresentation The string representation for this ownership status.
   */
  PlayerOwnership(String stringRepresentation) {
    this.stringRepresentation = stringRepresentation;
  }

  /**
   * Returns the string representation of this ownership status.
   *
   * @return The string representation of this ownership status.
   */
  @Override
  public String toString() {
    return this.stringRepresentation;
  }
}

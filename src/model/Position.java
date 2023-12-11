package model;

/**
 * An interface representing a position on a Reversi game board.
 */
public interface Position {

  /**
   * Compares this position to another object for equality.
   *
   * @param that The object to compare with.
   * @return True if the objects are equal, false otherwise.
   */
  boolean equals(Object that);

  /**
   * Computes a hash code value for this position.
   *
   * @return The hash code value.
   */
  int hashCode();

  /**
   * Gets the first coordinate of this position.
   *
   * @return The value of the first coordinate.
   */
  int getFirstCoordinate();

  /**
   * Gets the second coordinate of this position.
   *
   * @return The value of the second coordinate.
   */
  int getSecondCoordinate();

  /**
   * Checks if this position is a corner position on a game board of the given size.
   *
   * @param size The size of the game board.
   * @return True if this position is a corner position, false otherwise.
   */
  boolean isCorner(int size);

  /**
   * Checks if this position is next to a corner position on a game board of the given size.
   *
   * @param size The size of the game board.
   * @return True if this position is next to a corner position, false otherwise.
   */
  boolean isNextToCorner(int size);
}

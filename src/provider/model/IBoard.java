package provider.model;

import java.util.List;
import java.util.Map;

/**
 * Represents the interface for a Reversi game board. Implementations of this interface provide
 * methods to interact with the game board, such as getting the map of hexagonal tiles, the width,
 * and the height of the board.
 */
public interface IBoard {

  /**
   * Gets the unmodifiable map representing the hexagonal tiles on the game board, with each tile
   * associated with its corresponding coordinates.
   *
   * @return A map of HexCoord to HexagonTile representing the state of the game board.
   */
  Map<HexCoord, HexagonTile> getMap();

  /**
   * Gets the width of the game board, which is defined as the widest row.
   *
   * @return The width of the game board.
   */
  int getWidth();

  /**
   * Gets the height of the game board, which is defined as the number of rows.
   *
   * @return The height of the game board.
   */
  int getHeight();

  /**
   * Gets a list of valid directions for hexagonal movement on the board.
   *
   * @return A list of HexDirection representing the possible movement directions.
   */
  List<HexDirection> getDirections();

  /**
   * Creates and returns a deep copy of the current game board. The cloned board is independent of
   * the original, allowing for separate game instances.
   *
   * @return A deep copy of the current game board.
   */
  IBoard clone();
}
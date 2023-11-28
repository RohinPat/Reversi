package model;

/**
 * The CellInterface represents a cell in a board or grid-based game.
 * Each cell can hold a Disc, and the Disc can be retrieved or set.
 */
public interface CellInterface {
  /**
   * Retrieves the disc content of the cell.
   *
   * @return The disc currently in the cell.
   */
  Disc getContent();

  /**
   * Sets the disc content of the cell.
   *
   * @param content The disc to be placed in the cell.
   */
  void setContent(Disc content);
}

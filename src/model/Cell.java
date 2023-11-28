package model;

/**
 * Represents a cell on the game board which can hold a disc.
 */

public class Cell implements CellInterface {
  private Disc content;

  /**
   * Constructs a new cell with the given disc content.
   *
   * @param content The disc to be placed in the cell.
   */
  public Cell(Disc content) {
    this.content = content;
  }

  /**
   * Retrieves the disc content of the cell.
   *
   * @return The disc currently in the cell.
   */
  public Disc getContent() {
    return this.content;
  }

  /**
   * Sets the disc content of the cell.
   *
   * @param content The disc to be placed in the cell.
   */
  public void setContent(Disc content) {
    this.content = content;
  }

}


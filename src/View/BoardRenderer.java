package view;

import model.Board;
import model.Disc;

/**
 * Represents a visual renderer for the game board.
 * This class is responsible for converting the game's board state into a visual representation
 * using characters to depict empty spaces, black discs, and white discs.
 */
public class BoardRenderer {
  private final Board model;

  /**
   * Constructs a new board renderer with the specified game board model.
   *
   * @param model The game board model to be rendered.
   */
  public BoardRenderer(Board model) {
    if (model == null) {
      throw new IllegalArgumentException("Model must not be null");
    }
    this.model = model;
  }

  /**
   * Converts the game board into a string representation.
   * Black discs are represented by 'X', white discs by 'O', and empty cells by '_'.
   *
   * @return The string representation of the game board.
   */
  @Override
  public String toString() {
    // StringBuilder to accumulate the string representation
    StringBuilder ap = new StringBuilder();

    // Calculate the number of rows and the diameter of the hexagon
    int numRows = model.getSize() - 1;
    int diameter = (model.getSize() * 2) - 1;

    // Initialize start and end column indices
    int startCol = 1;
    int endCol = model.getSize() - 1;

    // Loop through each row of the hexagon
    for (int row = 0 - numRows; row <= numRows; row++) {
      // Calculate the number of elements in the current row
      int numElem = ((model.getSize() - Math.abs(row) - 1) + model.getSize());

      // Calculate and append leading spaces for proper alignment
      for (int space = diameter - numElem; space > 0; space--) {
        ap.append(" ");
      }

      // Adjust start and end column indices as we move through the rows
      if (row <= 0) {
        startCol = startCol - 1;
      }
      if (row > 0) {
        endCol = endCol - 1;
      }

      // Loop through each column in the current row
      for (int col = startCol; col <= endCol; col++) {
        // Get the disc at the current position
        Disc print = model.getDiscAt(col, row);

        // Append the corresponding character to the StringBuilder
        if (print == Disc.BLACK) {
          ap.append("X ");
        } else if (print == Disc.WHITE) {
          ap.append("O ");
        } else {
          ap.append("_ ");
        }
      }

      // Move to the next line after each row
      ap.append("\n");
    }

    // Convert the StringBuilder to a string and return
    return ap.toString();
  }
}
package view;

import model.Disc;
import model.Reversi;

/**
 * Represents a visual renderer for the game board.
 * This class is responsible for converting the game's board state into a visual representation
 * using characters to depict empty spaces, black discs, and white discs.
 */
public class BoardRenderer {
  private final Reversi model;

  /**
   * Constructs a new board renderer with the specified game board model.
   *
   * @param model The game board model to be rendered.
   */
  public BoardRenderer(Reversi model) {
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
    StringBuilder ap = new StringBuilder();
    int numRows = model.getSize() - 1;
    int diameter = (model.getSize() * 2) - 1;
    int startCol = 1;
    int endCol = model.getSize() - 1;
    for (int row = 0 - numRows; row <= numRows; row++) {
      int numElem = ((model.getSize() - Math.abs(row) - 1) + model.getSize());
      for (int space = diameter - numElem; space > 0; space--) {
        ap.append(" ");
      }
      if (row <= 0) {
        startCol = startCol - 1;
      }
      if (row > 0) {
        endCol = endCol - 1;
      }
      for (int col = startCol; col <= endCol; col++) {
        Disc print = model.getDiscAt(col, row);
        if (print == Disc.BLACK) {
          ap.append("X ");
        } else if (print == Disc.WHITE) {
          ap.append("O ");
        } else {
          ap.append("_ ");
        }
      }
      ap.append("\n");
    }
    return ap.toString();
  }
}
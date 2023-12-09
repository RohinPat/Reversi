package view;

import model.Disc;
import model.Reversi;
import model.SquareBoard;

public class SquareBoardRenderer {

  private final Reversi model;

  public SquareBoardRenderer(Reversi model) {
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
    int size = model.getSize();

    for (int row = 0; row < size; row++) {
      for (int col = 0; col < size; col++) {
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

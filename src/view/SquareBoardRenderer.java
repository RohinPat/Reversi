package view;

import model.Disc;
import model.Reversi;

/**
 * The {@code SquareBoardRenderer} class is responsible for converting a Reversi game board
 * into a string representation. It uses 'X' for black discs, 'O' for white discs, and '_'
 * for empty cells in the generated string.
 */
public class SquareBoardRenderer {

  private final Reversi model;

  /**
   * Constructs a new {@code SquareBoardRenderer} with the specified Reversi model.
   *
   * @param model The Reversi model representing the current state of the game board.
   * @throws IllegalArgumentException If the provided model is null.
   */
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

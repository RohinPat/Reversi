package view;

import java.io.IOException;

/**
 * Represents a visual renderer for the game board.
 * This class is responsible for converting the game's board state into a visual representation
 * using characters to depict empty spaces, black discs, and white discs.
 */
public interface RendererInterface {

   /**
   * Converts the game board into a string representation.
   * Black discs are represented by 'X', white discs by 'O', and empty cells by '_'.
   *
   * @return The string representation of the game board.
   */
  String toString();

  /**
   * Renders the game board to the specified appendable output target.
   *
   * @throws IOException If an error occurs while appending to the output target.
   */
  void render();
}

package model;

public interface ReversiReadOnly {

  /**
   * Retrieves the disc at the specified cell coordinates.
   *
   * @param q The q-coordinate of the cell.
   * @param r The r-coordinate of the cell.
   * @return The disc present at the specified coordinates.
   * @throws IllegalArgumentException If the cell doesn't exist in the grid.
   */  
  Disc getDiscAt(int q, int r);

  /**
   * Checks if the cell at the specified coordinates is empty.
   *
   * @param q The q-coordinate of the cell.
   * @param r The r-coordinate of the cell.
   * @return True if the cell is empty, otherwise false.
   * @throws IllegalArgumentException If the cell doesn't exist in the grid.
   */
  boolean isCellEmpty(int q, int r);

  /**
   * Retrieves the size of the game board.
   *
   * @return The size of the board.
   */
  int getSize();

  /**
   * Checks if the game is over.
   *
   * @return True if the game is over, otherwise false.
   */
  boolean isGameOver();

  /*
   * Updates the board.
   * 
   * @param board The board to be updated.
   * @throws IllegalArgumentException If the board is null.
   */
  void update(Board board);
} 

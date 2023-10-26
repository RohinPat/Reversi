package Board;

public class Board {
  private Cell[][] grid;
  private final int size;

  public Board(int size) {
    this.size = size;
    this.grid = new Cell[size][size];

    // Initialize the board with all empty cells
    for (int q = 0; q < size; q++) {
      for (int r = 0; r < size; r++) {
        grid[q][r] = new Cell(Disc.EMPTY);
      }
    }
  }

  /**
   * Place a disc on the board.
   * @param q Axial coordinate q
   * @param r Axial coordinate r
   * @param disc Disc color to place
   */
  public void placeDisc(int q, int r, Disc disc) {
    grid[q][r].setDisc(disc);
  }

  /**
   * Get the disc at a specific position.
   * @param q Axial coordinate q
   * @param r Axial coordinate r
   * @return Disc at the position (q, r)
   */
  public Disc getDiscAt(int q, int r) {
    return grid[q][r].getDisc();
  }

  /**
   * Check if a cell is empty.
   * @param q Axial coordinate q
   * @param r Axial coordinate r
   * @return true if cell is empty, false otherwise
   */
  public boolean isCellEmpty(int q, int r) {
    return grid[q][r].getDisc() == Disc.EMPTY;
  }

  /**
   * Return the size of the board.
   * @return size of the board
   */
  public int getSize() {
    return size;
  }

  // Additional methods as necessary, such as checking valid moves, etc.
}

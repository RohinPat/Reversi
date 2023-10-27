package Model;

import java.util.HashMap;
import java.util.Map;

public class Board {
  private Map<Coordinate, Cell> grid;
  private final int size;

  public Board(int size) {
    this.size = size;
    int rowSize = (size * 2) - 1;
    int colSize = size - 1;
    for (int row = 0; row < rowSize; row++) {
      if (row + 1 < rowSize / 2) {
        colSize++;
      }
      else {
        colSize--;
      }
      for (int col = 0; col < colSize; col++) {
        grid.put(new Coordinate(row, col), new Cell(Disc.EMPTY));
      }
    }
  }

  


  public void placeDisc(int row, int col, Disc disc) {
    Cell c = grid.get(new Coordinate(row, col));
    
  }

  
  public Disc getDiscAt(int q, int r) {
    
  }

  
  public boolean isCellEmpty(int q, int r) {
    return grid[q][r].getDisc() == Disc.EMPTY;
  }

  
  public int getSize() {
    return size;
  }

}

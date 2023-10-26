package Model;

import java.util.HashMap;
import java.util.Map;

public class Board {
  private Map<Coordinate, Cell> grid;
  private final int size;

  public Board(int size) {
    this.size = size;
    this.grid = new Cell[size][size];

    

    for (int q = 0; q < size; q++) {
      for (int r = 0; r < size; r++) {
        grid[q][r] = new Cell(Disc.EMPTY);
      }
    }
  }

  


  public void placeDisc(int q, int r, Disc disc) {
    grid[q][r].setDisc(disc);
  }

  
  public Disc getDiscAt(int q, int r) {
    return grid[q][r].getDisc();
  }

  
  public boolean isCellEmpty(int q, int r) {
    return grid[q][r].getDisc() == Disc.EMPTY;
  }

  
  public int getSize() {
    return size;
  }

}

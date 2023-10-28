package Model;

import java.util.Map;

public class Board {
  private Map<Coordinate, Cell> grid;
  private final int size;


  public Board(int size) {
    this.size = size;

    // this first loop sets up the first half of rows not including the middle row
    for (int i = 0; i < size - 1; i++) {
        for (int j = -i; j < size; j++) {
            grid.put(new Coordinate(j, -(size - 1 - i), (-j + (size - 1 - i))), new Cell(Disc.EMPTY));
        }
    }

    // this second loop sets up the rest of the rows INCLUDING the middle row - intializes every cell to be empty at first
    for (int i = 0; i < size; i ++){
      for (int j = -(size - 1); j < size - 1; j++){
        grid.put(new Coordinate(j, i, (-j-(i))), new Cell(Disc.EMPTY));
      }
    }

    //i'm thinking of adding the starting pieces in the constructor as well but this should be easily moveable

    
  }

  public void placeDisc(int q, int r, int s, Disc disc) {
    grid.get(new Coordinate(q, r, s)).setContent(disc);
  }

  public Disc getDiscAt(int q, int r, int s) {
    return grid.get(new Coordinate(q, r, s)).getContent();
  }


  public boolean isCellEmpty(int q, int r, int s) {
    return grid.get(new Coordinate(q, r, s)).getContent() == Disc.EMPTY;
  }

  
  public int getSize() {
    return size;
  }

}

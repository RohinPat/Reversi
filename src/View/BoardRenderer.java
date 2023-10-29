package View;

import java.io.IOException;
import java.util.Objects;

import Model.Coordinate;
import Model.Disc;
import Model.Reversi;


public class BoardRenderer {
  private final Reversi model;
  private Appendable ap;

  public BoardRenderer(Reversi model) {
    this.model = model;
  }

  public BoardRenderer(Reversi model, Appendable ap) {
    this.model = model;
    this.ap = ap;
  }

  @Override
  public String toString() {
    // this first loop sets up the first half of rows not including the middle row
    for (int i = 0; i < board.size - 1; i++) {
        for (int j = -i; j < board.size; j++) {
            grid.put(new Coordinate(j, -(board.size - 1 - i)), new Cell(Disc.EMPTY));
        }
    }

    // this second loop sets up the rest of the rows INCLUDING the middle row - intializes every cell to be empty at first
    for (int i = 0; i < board.size; i ++){
      for (int j = -(board.size - 1); j < board.size - 1; j++){
        grid.put(new Coordinate(j, i), new Cell(Disc.EMPTY));
      }
    }
  }
}

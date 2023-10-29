package View;

import java.io.IOException;
import java.util.Objects;

import Model.Board;
import Model.Coordinate;
import Model.Disc;
import Model.Cell;

public class BoardRenderer {
  private final Board model;
  private Appendable ap;

  public BoardRenderer(Board model) {
    this.model = model;
  }

  public BoardRenderer(Board model, Appendable ap) {
    this.model = model;
    this.ap = ap;
  }

  @Override
public String toString() {
    StringBuilder ap = new StringBuilder();
    int boardSize = model.getSize();

    // Loop through each row
    for (int i = 0; i < (boardSize * 2) - 1; i++) {
        // Determine the indentation for the current row
        int indent = boardSize - 1 - Math.abs(boardSize / 2 - i);
        for (int s = 0; s < indent; s++) {
            ap.append(" ");
        }

        // Loop through each column
        int minCol = -(boardSize / 2) + Math.abs(boardSize / 2 - i);
        int maxCol = (boardSize / 2) - Math.abs(boardSize / 2 - i);
        for (int j = minCol; j <= maxCol; j++) {
            Coordinate coord = new Coordinate(j, i - (boardSize / 2));
            Cell cell = model.getGridCell(coord);
            if (cell != null) {  
                Disc cellSymbol = cell.getContent();
                String content = (cellSymbol == Disc.BLACK) ? "X" :
                                 (cellSymbol == Disc.WHITE) ? "O" : "_";
                ap.append(content).append(" ");
            }
        }

        ap.append("\n");
    }

    return ap.toString();
}
}
/* 
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

    */
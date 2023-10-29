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
      for (int i = 0; i < boardSize; i++) {
          // Determine the indentation for the current row
          int indent = Math.abs(boardSize / 2 - i);
          for (int s = 0; s < indent; s++) {
              ap.append(" ");
          }

          /*
          // Loop through each column
          for (int j = -(boardSize - 1); j < boardSize - 1; j++) {
              Coordinate coord = new Coordinate(j, i);
              Cell cell = board.getGrid.getCell(coord);  
              String cellSymbol = cell.getDisc().toString();  
              ap.append(" ").append(cellSymbol);
          }

           */
  
          ap.append("\n");
      }
  
      return ap.toString();
  }
  
}

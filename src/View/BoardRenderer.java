package view;

import model.Board;
import model.Disc;


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
    int numRows = model.getSize() - 1;
    int diameter = (model.getSize() * 2) - 1;
    int startCol = 1;
    int endCol = model.getSize() - 1;
    for (int row = 0 - numRows; row <= numRows; row++) {
      int numElem = ((model.getSize() - Math.abs(row) - 1) + model.getSize());
      for (int space = diameter - numElem; space > 0; space--) {
        ap.append(" ");
      }

      if (row <= 0) {
        startCol = startCol - 1;
      }
      if (row > 0) {
        endCol = endCol - 1;
      }

      for (int col = startCol; col <= endCol; col++) {
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
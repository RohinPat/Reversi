import java.util.HashMap;

import model.Board;
import model.Cell;
import model.Coordinate;
import model.Disc;
import model.ReversiReadOnly;
import model.Turn;
import view.BoardRenderer;
import view.ReversiFrame;

public final class ReversiMain {
  public static void main(String[] args) {
    ReversiReadOnly b1 = new Board(8);
    ReversiFrame view = new ReversiFrame(b1);
    view.setVisible(true);
  }
}

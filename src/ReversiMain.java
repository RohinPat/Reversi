import model.Board;
import model.ReversiReadOnly;
import view.ReversiFrame;

public final class ReversiMain {
  public static void main(String[] args) {
    ReversiReadOnly b1 = new Board(6);
    ReversiFrame view = new ReversiFrame(b1);
    view.setVisible(true);
  }
}

import model.Board;
import view.ReversiFrame;

public final class ReversiMain {
  public static void main(String[] args) {
    Board b1 = new Board(8);
    b1.playGame();
    ReversiFrame view = new ReversiFrame(b1);
    view.setVisible(true);
  }
}

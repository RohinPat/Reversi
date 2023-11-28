import controller.ReversiController;
import model.Board;
import model.ReversiReadOnly;
import view.BoardPanel;
import view.ReversiFrame;

/**
 * Main class used to run our GUI - passes through a ReadOnly version of Reversi to display.
 */
public final class ReversiMain {
  /**
   * Used to initialize the frame based on the given board.
   */
  public static void main(String[] args) {
    Board b1 = new Board(6);
    ReversiFrame view = new ReversiFrame(b1);
    BoardPanel viewPanel = view.getBoardPanel();
    ReversiController controller = new ReversiController(b1, viewPanel);
    view.setVisible(true);
  }
}

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
    ReversiFrame viewPlayer1 = new ReversiFrame(b1);
    BoardPanel viewPanel1 = viewPlayer1.getBoardPanel();
    ReversiController controller = new ReversiController(b1, viewPanel1);
    b1.addObserver(controller);
    ReversiFrame viewPlayer2 = new ReversiFrame(b1);
    BoardPanel viewPanel2 = viewPlayer2.getBoardPanel();
    ReversiController controller2 = new ReversiController(b1, viewPanel2);
    b1.addObserver(controller2);
    viewPlayer1.setVisible(true);
    viewPlayer2.setVisible(true);
  }
}

import controller.AIPlayer;
import controller.Player;
import controller.ReversiController;
import controller.aistrat.AvoidCorners;
import controller.aistrat.CaptureCorners;
import controller.aistrat.CaptureMost;
import controller.aistrat.TryTwo;
import model.Board;
import model.Disc;
import model.ReversiReadOnly;
import view.BoardPanel;
import view.ReversiFrame;
import controller.HumanPlayer;

/**
 * Main class used to run our GUI - passes through a ReadOnly version of Reversi to display.
 */
public final class ReversiMain {
  /**
   * Used to initialize the frame based on the given board.
   */
  public static void main(String[] args) {
    Board b1 = new Board(4);

    Player p1 = new HumanPlayer(Disc.BLACK);
    Player p2 = new HumanPlayer(Disc.WHITE);

    ReversiFrame viewPlayer1 = new ReversiFrame(b1);
    BoardPanel viewPanel1 = viewPlayer1.getBoardPanel();
    ReversiController controller = new ReversiController(b1, viewPanel1, p1);
    b1.addObserver(controller);
    ReversiFrame viewPlayer2 = new ReversiFrame(b1);
    BoardPanel viewPanel2 = viewPlayer2.getBoardPanel();
    ReversiController controller2 = new ReversiController(b1, viewPanel2, p2);
    b1.addObserver(controller2);
    viewPlayer1.setVisible(true);
    viewPlayer2.setVisible(true);

    controller2.updateView();

  }
}

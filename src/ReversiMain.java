import java.util.ArrayList;

import controller.AIPlayer;
import controller.Player;
import controller.ReversiController;
import controller.aistrat.AvoidCorners;
import controller.aistrat.CaptureCorners;
import controller.aistrat.CaptureMost;
import controller.aistrat.ReversiStratagy;
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
    Board b1 = null;
    Player p1 = null;
    Player p2 = null;
    ReversiController controller2 = null;
    int argsUsed = 0;
    int maxArgs = args.length;
    int size = 0;
    if (args.length == 0) {
      b1 = new Board(4);
      p1 = new HumanPlayer(Disc.BLACK);
      p2 = new HumanPlayer(Disc.WHITE);
    }
    else {
      try {
        size = Integer.parseInt(args[argsUsed]);
        argsUsed++;
      } catch (NumberFormatException e) {
        size = 4;
      }
      try {
        b1 = new Board(size);
      } catch (IllegalArgumentException e) {
        System.out.println("Invalid board size");
      }
      String player1 = args[argsUsed];
      argsUsed++;
      ArrayList<String> moves = new ArrayList<String>();
      moves.add("capturemost");
      moves.add("capturecorners");
      moves.add("avoidcorners");
      if (player1.equals("human")) {
        p1 = new HumanPlayer(Disc.BLACK);
      }
      else if (player1.equals("ai")) {
        String strat1 = args[argsUsed];
        String strat2 = null;
        String strat3 = null;
        argsUsed++;
        if (moves.contains(args[argsUsed])) {
          strat2 = args[argsUsed];
          argsUsed++;
        }
        if (moves.contains(args[argsUsed])) {
          strat3 = args[argsUsed];
          argsUsed++;
        }
        ReversiStratagy strat = getStrat(strat1, strat2, strat3);
        p1 = new AIPlayer(Disc.BLACK, strat);
      }
      String player2 = args[argsUsed];
      argsUsed++;
      if (player2.equals("human")) {
        p2 = new HumanPlayer(Disc.WHITE);
      }
      else if (player2.equals("ai")) {
        String strat1 = args[argsUsed];
        String strat2 = null;
        String strat3 = null;
        argsUsed++;
        try {
          if (moves.contains(args[argsUsed])) {
            strat2 = args[argsUsed];
            argsUsed++;
          }
          if (moves.contains(args[argsUsed])) {
            strat3 = args[argsUsed];
            argsUsed++;
          }
        } catch (ArrayIndexOutOfBoundsException e) {
          //stops from checking args that arent there
        }
        ReversiStratagy strat = getStrat(strat1, strat2, strat3);
        p2 = new AIPlayer(Disc.WHITE, strat);
      }
    }
    try {
      ReversiFrame viewPlayer1 = new ReversiFrame(b1);
      BoardPanel viewPanel1 = viewPlayer1.getBoardPanel();
      ReversiController controller = new ReversiController(b1, viewPanel1, p1);
      b1.addObserver(controller);
      ReversiFrame viewPlayer2 = new ReversiFrame(b1);
      BoardPanel viewPanel2 = viewPlayer2.getBoardPanel();
      controller2 = new ReversiController(b1, viewPanel2, p2);
      b1.addObserver(controller2);
      viewPlayer1.setVisible(true);
      viewPlayer2.setVisible(true);
    } catch (IllegalArgumentException e) {
      System.out.println("error in inputs");
    }
    controller2.updateView();
  }

  private static ReversiStratagy getStrat(String strat1, String strat2, String strat3) {
    ReversiStratagy stratOne = null;
    ReversiStratagy stratTwo = null;
    ReversiStratagy stratThree = null;
    if (strat1.equals("capturemost")) {
      stratOne = new CaptureMost();
    }
    else if (strat1.equals("capturecorners")) {
      stratOne = new CaptureCorners();
    }
    else if (strat1.equals("avoidcorners")) {
      stratOne = new AvoidCorners();
    }
    if (strat2 != null) {
      if (strat2.equals("capturemost")) {
        stratTwo = new CaptureMost();
      } else if (strat2.equals("capturecorners")) {
        stratTwo = new CaptureCorners();
      } else if (strat2.equals("avoidcorners")) {
        stratTwo = new AvoidCorners();
      }
    }
    if (strat3 != null) {
      if (strat3.equals("capturemost")) {
        stratThree = new CaptureMost();
      } else if (strat3.equals("capturecorners")) {
        stratThree = new CaptureCorners();
      } else if (strat3.equals("avoidcorners")) {
        stratThree = new AvoidCorners();
      }
    }
    if (stratOne != null && stratTwo == null && stratThree == null) {
      return stratOne;
    }
    else if (stratOne != null && stratTwo != null && stratThree == null) {
      return new TryTwo(stratOne, stratTwo);
    }
    else if (stratOne != null && stratTwo != null && stratThree != null) {
      return new TryTwo(stratOne, new TryTwo(stratTwo, stratThree));
    }
    else {
      return null;
    }
  }
}
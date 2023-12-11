import java.util.ArrayList;

import controller.AIPlayer;
import controller.Player;
import controller.ReversiController;
import controller.StratagyAdapter;
import controller.aistrat.AvoidCorners;
import controller.aistrat.CaptureCorners;
import controller.aistrat.CaptureMost;
import controller.aistrat.ReversiStratagy;
import controller.aistrat.TryTwo;
import model.Board;
import model.Disc;
import model.Reversi;
import model.SquareBoard;
import provider.strategies.AvoidTilesNextToCornersStrategy;
import provider.strategies.FallableInfallablePairStrategy;
import provider.strategies.MinimaxStrategy;
import provider.strategies.MostPointsGainedStrategy;
import provider.strategies.PlayCornersStrategy;
import view.IBoardPanel;
import view.IFrame;
import view.ReversiFrame;
import controller.HumanPlayer;
import view.SquareReversiFrame;

/**
 * Main class used to run our GUI - passes through a ReadOnly version of Reversi to display.
 */
public final class ReversiMain {
  /**
   * Used to initialize the frame based on the given arguments.
   */
  public static void main(String[] args) {

    Reversi b1 = null;
    Player p1 = null;
    Player p2 = null;
    ReversiController controller12 = null;
    boolean isSquare = false;
    int argsUsed = 0;
    int maxArgs = args.length;
    int size = 6;
    boolean hints = false;
    if (args.length == 0) {
      b1 = new Board(size);
      p1 = new HumanPlayer(Disc.BLACK);
      p2 = new HumanPlayer(Disc.WHITE);
    } else {
      try {
        size = Integer.parseInt(args[argsUsed]);
        argsUsed++;
      } catch (NumberFormatException e) {
        size = 6;
      }
      if (args[argsUsed].equals("s")) {
        try {
          b1 = new SquareBoard(size);
          isSquare = true;
          argsUsed++;
        }
        catch (IllegalArgumentException e) {
          throw new IllegalArgumentException("Invalid board size");
        }
      }
      else {
        try {
          b1 = new Board(size);
        } catch (IllegalArgumentException e) {
          throw new IllegalArgumentException("Invalid board size");
        }
      }
      String player1 = args[argsUsed];
      argsUsed++;
      boolean nextplayer = true;
      if (player1.equals("human")) {
        nextplayer = false;
        p1 = new HumanPlayer(Disc.BLACK);
      } else if (player1.equals("ai")) {
        ArrayList<String> strats = new ArrayList<String>();
        while (nextplayer) {
          strats.add(args[argsUsed]);
          argsUsed++;
          if (args[argsUsed].equals("human") || args[argsUsed].equals("ai")) {
            nextplayer = false;
          }
        }
        ReversiStratagy strat1 = getStrat(strats);
        p1 = new AIPlayer(Disc.BLACK, strat1);
      } else {
        System.out.println("First Player Input Error");
      }

      String player2 = args[argsUsed];
      argsUsed++;
      boolean nextplayer2 = true;
      if (player2.equals("human")) {
        nextplayer2 = false;
        p2 = new HumanPlayer(Disc.WHITE);
      } else if (player2.equals("ai")) {
        ArrayList<String> strats2 = new ArrayList<String>();
        while (nextplayer2) {
          strats2.add(args[argsUsed]);
          argsUsed++;
          try {
            if (args[argsUsed].equals("human") || args[argsUsed].equals("ai") || args[argsUsed].equals("hintson")) {
              nextplayer2 = false;
            }
          } catch (ArrayIndexOutOfBoundsException e) {
            nextplayer2 = false;
          }
        }
        ReversiStratagy strat2 = getStrat(strats2);
        p2 = new AIPlayer(Disc.WHITE, strat2);
      } else {
        System.out.println("Second Player Input Error");
      }
      try {
        if (args[argsUsed].equals("hintson")) {
          hints = true;
        } else {
          hints = false;
        }
      } catch (ArrayIndexOutOfBoundsException e) {
        hints = false;
      }
    }
    try {
      IFrame viewPlayer1 = null;
      IFrame viewPlayer2 = null;
      if (isSquare) {
        viewPlayer1 = new SquareReversiFrame(b1);
        viewPlayer2 = new SquareReversiFrame(b1);
      } else {
        viewPlayer1 = new ReversiFrame(b1, hints);
        viewPlayer2 = new ReversiFrame(b1, hints);
      }
      IBoardPanel viewPanel1 = viewPlayer1.getBoardPanel();
      ReversiController controller = new ReversiController(b1, viewPanel1, p1);
      b1.addObserver(controller);
      viewPanel1.setController(controller);

      IBoardPanel viewPanel2 = viewPlayer2.getBoardPanel();
      controller12 = new ReversiController(b1, viewPanel2, p2);
      b1.addObserver(controller12);
      viewPanel2.setController(controller12);
      viewPlayer2.makeVisible(true);
      viewPlayer1.makeVisible(true);

    } catch (IllegalArgumentException e) {
      System.out.println("error in inputs");
    }
    try {
      controller12.updateView();
    } catch (NullPointerException e) {
      //
    }
  }



/**
   * Parses command-line arguments to set up the game strategy for a Reversi game.
   * This method maps strings representing strategy names to their corresponding strategy objects.
   * It supports both single and multiple strategies. In the case of multiple strategies,
   * it combines them using the 'TryTwo' class, allowing an AI player to sequentially attempt
   * different strategies during the game. If an invalid strategy name is provided, the method
   * returns null, indicating an error in strategy selection.
   *
   * @param strats An ArrayList of strings representing the names of strategies.
   * @return ReversiStrategy The final composed strategy based on the input list,
   * or null if an invalid strategy name is encountered.
   **/
  private static ReversiStratagy getStrat(ArrayList<String> strats) {
    ArrayList<String> listOfStrats = strats;
    int numOfStrats = strats.size();
    ReversiStratagy finalStrat = null;
    ReversiStratagy captureCorners = new CaptureCorners();
    ReversiStratagy captureMost = new CaptureMost();
    ReversiStratagy avoidCorners = new AvoidCorners();
    ReversiStratagy avoidTilesNextToCorners =
            new StratagyAdapter(
                    new FallableInfallablePairStrategy(
                            new AvoidTilesNextToCornersStrategy(), new MostPointsGainedStrategy()));
    ReversiStratagy minimax = new StratagyAdapter(
            new MinimaxStrategy(5, new MostPointsGainedStrategy(),
                    new MostPointsGainedStrategy()));
    ReversiStratagy mostPointsGained = new StratagyAdapter(new MostPointsGainedStrategy());
    ReversiStratagy playCorners = new StratagyAdapter(
            new FallableInfallablePairStrategy(new PlayCornersStrategy(),
                    new MostPointsGainedStrategy()));
    if (numOfStrats == 1) {
      if (strats.get(0).equals("capturecorners")) {
        finalStrat = captureCorners;
      } else if (strats.get(0).equals("capturemost")) {
        finalStrat = captureMost;
      } else if (strats.get(0).equals("avoidcorners")) {
        finalStrat = avoidCorners;
      } else if (strats.get(0).equals("avoidtilesnexttocorners")) {
        finalStrat = avoidTilesNextToCorners;
      } else if (strats.get(0).equals("minimax")) {
        finalStrat = minimax;
      } else if (strats.get(0).equals("mostpointsgained")) {
        finalStrat = mostPointsGained;
      } else if (strats.get(0).equals("playcorners")) {
        finalStrat = playCorners;
      } else {
        System.out.println("Invalid Strategy");
        return null;
      }
    }
    while (listOfStrats.size() > 0) {
      if (finalStrat == null) {
        ArrayList<String> first = new ArrayList<>();
        first.add(strats.get(0));
        finalStrat = getStrat(first);
        strats.remove(0);
      }
      if (strats.get(0).equals("capturecorners")) {
        finalStrat = new TryTwo(finalStrat, captureCorners);
      } else if (strats.get(0).equals("capturemost")) {
        finalStrat = new TryTwo(finalStrat, captureMost);
      } else if (strats.get(0).equals("avoidcorners")) {
        finalStrat = new TryTwo(finalStrat, avoidCorners);
      } else if (strats.get(0).equals("avoidtilesnexttocorners")) {
        finalStrat = new TryTwo(finalStrat, avoidTilesNextToCorners);
      } else if (strats.get(0).equals("minimax")) {
        finalStrat = new TryTwo(finalStrat, minimax);
      } else if (strats.get(0).equals("mostpointsgained")) {
        finalStrat = new TryTwo(finalStrat, mostPointsGained);
      } else if (strats.get(0).equals("playcorners")) {
        finalStrat = new TryTwo(finalStrat, playCorners);
      } else {
        System.out.println("Invalid Strategy Two");
        return null;
      }
      listOfStrats.remove(0);
    }
    return finalStrat;
  }
}

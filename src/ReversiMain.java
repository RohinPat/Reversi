import java.util.ArrayList;

import controller.AIPlayer;
import controller.BoardAdapter2;
import controller.ControllerFeatures;
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
import provider.view.ReversiGUIView;
import view.BoardPanel;
import view.HintDecorator;
import view.ReversiFrame;
import controller.HumanPlayer;
import view.SquareBoardPanel;
import view.SquareReversiFrame;
import view.ViewAdapter;

/**
 * Main class used to run our GUI - passes through a ReadOnly version of Reversi to display.
 */
public final class ReversiMain {
  /**
   * Used to initialize the frame based on the given board.
   */
  public static void main(String[] args) {
    /*
    Reversi newBoard = new SquareBoard(10);

    Player player1 = new AIPlayer(Disc.BLACK, new CaptureMost());
    SquareReversiFrame frame = new SquareReversiFrame(newBoard);
    SquareBoardPanel viewPanel = frame.getBoardPanel();
    ControllerFeatures controller1 = new ReversiController(newBoard, viewPanel, player1);
    newBoard.addObserver(controller1);
    viewPanel.setController(controller1);

    Player player2 = new AIPlayer(Disc.WHITE, new MostPointsGainedStrategy());
    SquareReversiFrame frame2 = new SquareReversiFrame(newBoard);
    SquareBoardPanel viewPanel2 = frame2.getBoardPanel();
    ControllerFeatures controller2 = new ReversiController(newBoard, viewPanel2, player2);
    newBoard.addObserver(controller2);
    viewPanel2.setController(controller2);

    frame.setVisible(true);
    frame2.setVisible(true);

    controller2.updateView();
  }

     */

    Board b1 = null;
    Player p1 = null;
    Player p2 = null;
    ReversiController controller12 = null;
    int argsUsed = 0;
    int maxArgs = args.length;
    int size = 0;
    if (args.length == 0) {
      b1 = new Board(4);
      p1 = new HumanPlayer(Disc.BLACK);
      p2 = new HumanPlayer(Disc.WHITE);
    } else {
      try {
        size = Integer.parseInt(args[argsUsed]);
        argsUsed++;
      } catch (NumberFormatException e) {
        size = 4;
      }
      try {
        b1 = new Board(size);
      } catch (IllegalArgumentException e) {
        throw new IllegalArgumentException("Invalid board size");
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
            if (args[argsUsed].equals("human") || args[argsUsed].equals("ai")) {
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
    }
    try {

      ReversiFrame viewPlayer1 = new ReversiFrame(b1);
      BoardPanel viewPanel1 = viewPlayer1.getBoardPanel();
      HintDecorator hd1 = new HintDecorator(viewPanel1);
      ReversiController controller = new ReversiController(b1, hd1, p1);
      viewPlayer1.setBoardPanel(hd1);
      b1.addObserver(controller);
      hd1.setController(controller);

      ReversiFrame viewPlayer2 = new ReversiFrame(b1);
      BoardPanel viewPanel2 = viewPlayer2.getBoardPanel();
      controller12 = new ReversiController(b1, viewPanel2, p2);
      b1.addObserver(controller12);
      viewPanel2.setController(controller12);
      viewPlayer2.setVisible(true);


      /*
      ReversiGUIView rev2 = new ReversiGUIView(new BoardAdapter2(b1));
      ViewAdapter v2 = new ViewAdapter(rev2);
      controller2 = new ReversiController(b1, v2, p2);
      b1.addObserver(controller2);
      v2.setController(controller2);
      v2.setVisible(true);
      */


      viewPlayer1.setVisible(true);
      
    } catch (IllegalArgumentException e) {
      System.out.println("error in inputs");
    }
    try {
      controller12.updateView();
    }
    catch (NullPointerException e){
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
   *                         or null if an invalid strategy name is encountered.

  **/

  // 5 capturemost fallibleinfalliblepair(new Capture Corners, new Most)
  private static ReversiStratagy getStrat(ArrayList<String> strats) {
    ArrayList<String> listOfStrats = strats;
    int numOfStrats = strats.size();
    ReversiStratagy finalStrat = new CaptureMost();
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
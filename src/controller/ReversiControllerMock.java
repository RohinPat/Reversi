package controller;

import controller.aistrat.CaptureMost;
import view.BoardPanel;
import model.Board;
import model.Coordinate;
import model.Disc;

/**
 * The {@code ReversiController} class is responsible for controlling the game logic and
 * interaction between the model and the view in a Reversi game.
 */
public class ReversiControllerMock implements ControllerFeatures {
  private StringBuilder log = new StringBuilder();
  private Board model;
  private BoardPanel view;
  private Player player;

  /**
   * Constructs a {@code ReversiController} with the specified model, view, and player.
   * Initializes the controller with the given components and sets up the view's
   * reference to this controller. If the player is an AI and it's their turn, the AI
   * player may make a move immediately.
   *
   * @param model  The {@link Board} model representing the game board and state.
   * @param view   The {@link BoardPanel} view displaying the game board.
   * @param player The {@link Player} representing the current player.
   */
  public ReversiControllerMock(Board model, BoardPanel view, Player player) {
    this.model = model;
    this.view = view;
    this.player = player;
    this.view.setController(this);

    // Check if the AI player should make a move immediately
    if (model.currentColor() == player.getDisc() && player instanceof AIPlayer) {
      player.makeAMove(model, null);
    }
  }

  @Override
  public void selectHexagon(int q, int r) {
    // Logic when a hexagon is selected
    // You might want to highlight the selected hexagon in the view, for example
  }

  @Override
  public void confirmMove() {
    log.append("Confirms the Move for " + player.getDisc() + "and trys to make the move" + "\n");
    if (player.isPlayerTurn(model)) {
      Coordinate selectedHex = view.getSelectedHexagon();
      if (selectedHex != null && model.isCellEmpty(selectedHex.getQ(), selectedHex.getR())) {
        try {
          player.makeAMove(model, selectedHex);
          log.append("move made" + "\n");
          view.initializeHexagons(model); // Update the view to reflect the new board state
        } catch (IllegalArgumentException e) {
          view.showInvalidMoveDialog(e.getMessage());
        }
      }
    } else {
      view.showInvalidMoveDialog("Not your turn right now");
    }
  }

  @Override
  public void passTurn() {
    // Logic for passing a turn
    log.append("Passes the Turn" + "\n");
    model.passTurn();
    view.initializeHexagons(model); // Update the view if necessary
  }

  /**
   * Updates the view to reflect the current game state. If the game is not over and it's
   * the AI player's turn, the AI may make a move. Otherwise, it may highlight possible
   * moves or indicate that it's the player's turn if it's a human player.
   */
  public void updateView() {
    log.append("Updates the View" + "\n");
    view.initializeHexagons(model);
    if (!model.isGameOver()) {
      if (model.currentColor() == player.getDisc()) {
        if (player instanceof AIPlayer) {
          CaptureMost cm = new CaptureMost();
          log.append("AI Player makes a move: " + player.getDisc() + cm.chooseMove(model, player.getDisc()).getQ() + cm.chooseMove(model, player.getDisc()).getR() + "\n");
          player.makeAMove(model, null); // AI strategy chooses the move
        } else {
          log.append("Human Player makes a move" + player.getDisc() + "\n");
          // Human player - wait for user input
          // Maybe highlight possible moves or indicate it's the player's turn
        }
      }
    } else {
      String end = model.getState().toString();
      view.showInvalidMoveDialog("GAME IS OVER" + end);
    }
  }

  /**
   * Retrieves the current player's disc color (either black or white).
   *
   * @return The {@link Disc} representing the color of the current player.
   */
  public Disc getPlayer() {
    log.append("Gets the Players Disc: " + player.getDisc() + "\n");
    return player.getDisc();
  }

  /**
   * Retrieves the current player whose turn it is.
   *
   * @return The {@link Player} representing the current player.
   */
  public Player getTurn() {
    log.append("Gets the Players Turn: " + player + "\n");
    return player;
  }

  public String getLog() {
    return log.toString();
  }
}

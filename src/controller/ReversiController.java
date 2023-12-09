package controller;

import view.BoardPanel;
import model.Board;
import model.Coordinate;
import model.Disc;
import view.IBoardPanel;

/**
 * The {@code ReversiController} class is responsible for controlling the game logic and
 * interaction between the model and the view in a Reversi game.
 */
public class ReversiController implements ControllerFeatures {
  private Board model;
  private IBoardPanel view;
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
  public ReversiController(Board model, IBoardPanel view, Player player) {
    this.model = model;
    this.view = view;
    this.player = player;

    // Check if the AI player should make a move immediately
    if (model.currentColor() == player.getDisc() && player instanceof AIPlayer) {
      player.makeAMove(model, null);
    }
  }

  @Override
  public void confirmMove(int q, int r) {
    if (player.getDisc().equals(model.currentColor())) {
      Coordinate selectedHex = new Coordinate(q, r);
      if (selectedHex != null && model.isCellEmpty(selectedHex.getFirstCoordinate(), selectedHex.getSecondCoordinate())) {
        try {
          player.makeAMove(model, selectedHex);
          model.notifyTurnChange();
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
    model.passTurn();
  }

  /**
   * Updates the view to reflect the current game state. If the game is not over and it's
   * the AI player's turn, the AI may make a move. Otherwise, it may highlight possible
   * moves or indicate that it's the player's turn if it's a human player.
   */
  public void updateView() {
    view.initializeBoard(model);
    handleTurnChange(model.currentColor());
    if (model.isGameOver()) {
      view.initializeBoard(model);
    }
  }

  @Override
  public void handleTurnChange(Disc disc) {
    if (player.getDisc().equals(disc) && player instanceof AIPlayer && !model.isGameOver()) {
      player.makeAMove(model, null); // AI strategy chooses the move
    }
  }

  /**
   * Retrieves the current player's disc color (either black or white).
   *
   * @return The {@link Disc} representing the color of the current player.
   */
  public Disc getPlayer() {
    return player.getDisc();
  }

  /**
   * Retrieves the current player whose turn it is.
   *
   * @return The {@link Player} representing the current player.
   */
  public Player getTurn() {
    return player;
  }

  /**
   * Helps in the creation of an output log to ensure code runs as expected, and we can see the.
   * finer details of the AI decisions - there is no log as this is not the mock, so it doesn't.
   * log anything here.
   *
   * @return null as this is not the mock, so it doesn't log anything here
   */
  public String getLog() {
    return null;
  }
}

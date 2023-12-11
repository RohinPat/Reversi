package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import controller.ControllerFeatures;

/**
 * An abstract class representing the model for a Reversi game.
 * This class provides a foundation for Reversi game models and includes common functionality
 * such as maintaining the game state, managing the grid, and handling game logic.
 */

public abstract class AbstractModel implements Reversi {
  protected Map<Position, Cell> grid;
  protected int consecPasses;
  protected GameState gameState;
  protected Turn whoseTurn;
  protected int size;
  protected List<ControllerFeatures> observers;

  /**
   * Constructs an AbstractModel for a Reversi game with the specified board size. Sets up the.
   * foundational compoenents and values of a Reversi game.
   *
   * @param size The size of the Reversi board.
   */
  public AbstractModel(int size) {
    this.size = size;
    this.grid = new HashMap<>();
    this.consecPasses = 0;
    this.observers = new ArrayList<>();
    this.gameState = GameState.PRE;
    this.whoseTurn = Turn.BLACK;
  }


  /**
   * Used to return a map representation of the current game board, which holds what pieces.
   * are placed at what positions.
   *
   * @return returns a map representation of the current game board.
   */
  public Map<Position, Cell> getMap() {
    return grid;
  }

  /**
   * Returns the color of the disc of the current player.
   *
   * @return The disc color of the current player.
   */
  public Disc currentColor() {
    if (gameState != GameState.PRE) {
      if (whoseTurn == Turn.BLACK) {
        return Disc.BLACK;
      } else {
        return Disc.WHITE;
      }
    } else {
      throw new IllegalStateException("This cannot be checked yet");
    }
  }

  /**
   * Adds a {@link ControllerFeatures} observer to the list of observers.
   * This method is used to register a controller as an observer that should be notified
   * of changes to the state of this object.
   *
   * @param controller The {@link ControllerFeatures} instance to be added as an observer.
   */
  public void addObserver(ControllerFeatures controller) {
    observers.add(controller);
  }

  /**
   * Notifies all registered observers of a change in the object's state.
   * This method is typically called to inform the observers about an update or change in the state
   * that requires their attention, usually resulting in a change or refresh of their view or data.
   * Each observer's `updateView` method is called to perform these updates.
   */
  public void notifyObservers() {
    for (ControllerFeatures controller : observers) {
      controller.updateView();
    }
  }

  /**
   * Notifies all registered observers of a change in the current player's turn.
   * This method is invoked to communicate to observers that the turn has switched
   * from one player to another in the game. It triggers the `handleTurnChange`
   * method in each observer, allowing them to respond appropriately to the new
   * game state, such as updating the UI or initiating AI moves.
   */
  public void notifyTurnChange() {
    for (ControllerFeatures controller : observers) {
      controller.handleTurnChange(currentColor());
    }
  }

  /**
   * Passes the turn to the next player.
   * used to swap turns either when a player passes their turn or at the end of their move and then.
   * updates all subcribed listeners about the change in game state.
   */
  public void passTurn() {
    if (gameState != GameState.PRE) {
      if (whoseTurn == Turn.BLACK) {
        whoseTurn = Turn.WHITE;
      } else {
        whoseTurn = Turn.BLACK;
      }
      consecPasses += 1;
    } else {
      throw new IllegalStateException("The game has not been started yet no move can be made");
    }

    notifyObservers();
    notifyTurnChange();

  }

  /**
   * Retrieves the size of the game board, which for hexagonal reversi is the number of hexagons.
   * in the top most row, and for a square reversi game is the number of squares in any row.
   *
   * @return The size of the board.
   */
  public int getSize() {
    return size;
  }

  /**
   * Checks if the specified player has any valid moves available on the board.
   * This method creates a copy of the current game board and iterates through
   * all possible moves to determine if at least one valid move exists for the player.
   *
   * @param playerDisc The {@link Disc} representing the player to check for valid moves.
   * @return True if there are valid moves available for the player, false otherwise.
   */
  protected boolean hasValidMoves(Disc playerDisc) {
    Turn current;
    if (playerDisc.equals(Disc.BLACK)) {
      current = Turn.BLACK;
    } else {
      current = Turn.WHITE;
    }
    for (Position coord : grid.keySet()) {
      Board dupe = new Board(size, this.createCopyOfBoard(), current);
      if (this.createCopyOfBoard().get(coord).getContent().equals(Disc.EMPTY)) {
        try {
          dupe.makeMove(coord);
          return true;
        } catch (IllegalArgumentException e) {
          // Ignore and continue checking other moves
        }
      }
    }
    return false;
  }

  /**
   * A helper method to check who wins based on who has more pieces on the board at the end of game.
   * Changes the state of the game to reflect and is only called in a situation where.
   * IsGameOver would indicate the game is over (returns true).
   */
  protected void whoWins() {
    int blackScore = getScore(Disc.BLACK);
    int whiteScore = getScore(Disc.WHITE);

    if (blackScore > whiteScore) {
      gameState = GameState.BLACKWIN;
    } else if (blackScore == whiteScore) {
      gameState = GameState.TIE;
    } else {
      gameState = GameState.WHITEWIN;
    }
  }

  /**
   * Checks if the game is over. Adjusts the gameState enum to reflect who wins.
   *
   * @return True if the game is over, otherwise false.
   */
  public boolean isGameOver() {
    if (gameState == GameState.PRE) {
      throw new IllegalStateException("The game has not been started this cannot be checked");
    }
    // Check for consecutive passes
    if (consecPasses == 2) {
      whoWins();
      return true;
    }

    boolean allCellsFilled = true;
    for (Cell cell : grid.values()) {
      if (cell.getContent() == Disc.EMPTY) {
        allCellsFilled = false;
        break;
      }
    }
    if (allCellsFilled) {
      whoWins();
      return true;
    }

    if (getScore(Disc.BLACK) == 0 || getScore(Disc.WHITE) == 0){
      whoWins();
      return true;
    }

    // Check for available moves for each player
    boolean player1 = hasValidMoves(Disc.BLACK);
    boolean player2 = hasValidMoves(Disc.WHITE);

    if (!player1 && !player2) {
      whoWins();
      return true;
    }

    return false;
  }

  /**
   * Retrieves the score for the inputted player by checking through how many pieces are placed.
   *
   * @return The number of pieces (score) of a certain player.
   */
  public int getScore(Disc player) {
    int scoreCounter = 0;
    for (Cell cell : grid.values()) {
      if (cell.getContent().equals(player)) {
        scoreCounter += 1;
      }
    }
    return scoreCounter;
  }

  /**
   * Returns a list of all possible moves for the current player.
   *
   * @return A list of all possible moves for the current player.
   */
  public ArrayList<Position> getPossibleMoves() {
    ArrayList<Position> possibleMoves = new ArrayList<>();
    Board og = new Board(size, createCopyOfBoard(), whoseTurn);
    for (Position coord : grid.keySet()) {
      if (grid.get(coord).getContent().equals(Disc.EMPTY)) {
        try {
          Board og1 = new Board(size, og.createCopyOfBoard(), whoseTurn);
          og1.makeMove(coord);
          possibleMoves.add(coord);
        } catch (IllegalArgumentException e) {
          // Ignore and continue checking other moves
        }
      }
    }
    return possibleMoves;
  }

  /**
   * Evaluates and returns the change in score after a hypothetical move.
   * This method simulates making a move on a copy of the current game board
   * and calculates the change in resulting score from before the move is made.
   * It's useful for strategy and AI decision-making as well as for hint feature.
   * If the move is invalid, it returns 0.
   *
   * @param model The {@link ReversiReadOnly} game model representing the current state.
   * @param move  The {@link Coordinate} representing the move to be evaluated.
   * @return The change in score after making the move.
   */
  public int checkMove(ReversiReadOnly model, Position move) {
    Turn turn = null;
    if (model.currentColor().equals(Disc.BLACK)) {
      turn = Turn.BLACK;
    } else {
      turn = Turn.WHITE;
    }
    Board copy = new Board(model.getSize(), model.createCopyOfBoard(), turn);
    int score = 0;
    int oldScore = copy.getScore(model.currentColor());
    try {
      copy.makeMove(move);
      score = copy.getScore(model.currentColor()) - oldScore - 1;
      return score;
    } catch (IllegalArgumentException e) {
      return 0;
    }
  }

  /**
   * Creates a deep copy of the board's map which contains all board positions.
   *
   * @return A deep copy of the board.
   */
  public HashMap<Position, Cell> createCopyOfBoard() {
    HashMap<Position, Cell> copy = new HashMap<Position, Cell>();
    for (Position coord : grid.keySet()) {
      Cell originalCell = grid.get(coord);
      Cell newCell = new Cell(originalCell.getContent());
      copy.put(coord, newCell);
    }
    return copy;
  }

  /**
   * Retrieves the state of the game.
   *
   * @return The state of the board.
   */
  public GameState getState() {
    return this.gameState;
  }

  /**
   * Determines if a move is valid in the current game state.
   * This method creates a copy of the current game board and attempts
   * to make the specified move. If the move is successfully made without
   * throwing an exception, the move is considered valid.
   *
   * @param coor        The {@link Position} where the move is to be made.
   * @param currentTurn The {@link Disc} representing the player making the move.
   * @return True if the move is valid, false otherwise.
   */
  public boolean validMove(Position coor, Disc currentTurn) {
    Turn turn = null;

    if (currentColor().equals(Disc.BLACK)) {
      turn = Turn.BLACK;
    } else {
      turn = Turn.WHITE;
    }
    Board copy = new Board(getSize(), createCopyOfBoard(), turn);
    try {
      copy.makeMove(coor);
      return true;
    } catch (IllegalArgumentException e) {
      return false;
    }
  }

  /**
   * Returns the color of the disc of the opponent player.
   *
   * @return The disc color of the opponent.
   */
  protected Disc oppositeColor() {
    if (gameState != GameState.PRE) {
      if (whoseTurn == Turn.BLACK) {
        return Disc.WHITE;
      } else {
        return Disc.BLACK;
      }
    } else {
      throw new IllegalStateException("This cannot be checked yet");
    }
  }

  /**
   * Places a disc at the specified cell coordinates.
   *
   * @param q    The first coordinate of the cell.
   * @param r    The second coordinate of the cell.
   * @param disc The disc to be placed.
   * @throws IllegalArgumentException If the cell doesn't exist in the grid.
   */
  public void placeDisc(int q, int r, Disc disc) {
    if (gameState != GameState.PRE) {
      if (!(grid.keySet().contains(new Coordinate(q, r)))) {
        throw new IllegalArgumentException("This cell doesn't exist in the above grid ");
      }
      grid.get(new Coordinate(q, r)).setContent(disc);
    } else {
      throw new IllegalStateException("The game has not been started yet this cannot be done");
    }
  }

  /**
   * Retrieves the Disc at the specified coordinates on the Reversi game board.
   *
   * @param q The first coordinate (column) of the cell.
   * @param r The second coordinate (row) of the cell.
   * @return The Disc (Disc.BLACK, Disc.WHITE, or Disc.EMPTY) at the specified coordinates.
   * @throws IllegalArgumentException if the provided coordinates are invalid.
   * @throws IllegalStateException    if the game has not been started yet.
   */
  public Disc getDiscAt(int q, int r) {
    if (gameState != GameState.PRE) {
      if (!(grid.keySet().contains(new Coordinate(q, r)))) {
        throw new IllegalArgumentException("This cell doesn't exist in the above grid ");
      }
      return grid.get(new Coordinate(q, r)).getContent();
    } else {
      throw new IllegalStateException("The game has not been started yet this cannot be done");
    }
  }

  /**
   * Checks if the cell at the specified coordinates on the Reversi game board is empty.
   *
   * @param q The first coordinate (column) of the cell.
   * @param r The second coordinate (row) of the cell.
   * @return True if the cell at the specified coordinates is empty (Disc.EMPTY), false otherwise.
   * @throws IllegalArgumentException if the provided coordinates are invalid.
   * @throws IllegalStateException    if the game has not been started yet.
   */
  public boolean isCellEmpty(int q, int r) {
    if (gameState != GameState.PRE) {
      if (!(grid.keySet().contains(new Coordinate(q, r)))) {
        throw new IllegalArgumentException("This cell doesn't exist in the above grid ");
      }
      return grid.get(new Coordinate(q, r)).getContent() == Disc.EMPTY;
    } else {
      throw new IllegalStateException("The game has not been started this cannot be checked");
    }
  }


}


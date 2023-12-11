package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import controller.ControllerFeatures;

public class SquareBoardMock extends AbstractModel{

  protected final HashMap<String, Integer> compassX = new HashMap<>();
  // used to represent all the changes in the X coordinate to check capture
  // outcomes in the different capturing directions
  protected final HashMap<String, Integer> compassY = new HashMap<>();
  // used to represent all the changes in the Y coordinate to check capture
  // outcomes in the different capturing directions

  protected StringBuilder log;


  /**
   * Initializes a new square game board of the specified size.
   *
   * @param size The size of the board.
   */
  public SquareBoardMock(int size){
    super(size);
    if (size <= 0 || size % 2 != 0){
      throw new IllegalArgumentException("Board size must be positive and even");
    }
    compassX.put("left", -1);
    compassX.put("right", 1);
    compassX.put("up", 0);
    compassX.put("down", 0);
    compassX.put("ne", 1);
    compassX.put("nw", -1);
    compassX.put("se", 1);
    compassX.put("sw", -1);


    compassY.put("left", 0);
    compassY.put("right", 0);
    compassY.put("up", -1);
    compassY.put("down", 1);
    compassY.put("ne", -1);
    compassY.put("nw", -1);
    compassY.put("se", 1);
    compassY.put("sw", 1);
    playGame();
    log = new StringBuilder();
  }

  /**
   * Initializes a new square game board of the specified size, and overwrites the board using a.
   * Given map of disc placements. Useful for creating deep copies for trying moves on the.
   * current gamestate without actually mutuating the main model.
   *
   * @param size       The size of the board.
   * @param grid1      The hashmap of discs to be overwritten onto the old grid.
   * @param whoseTuren The turn that's to be instantiated in the new game.
   */
  public SquareBoardMock(int size, HashMap<Position, Cell> grid1, Turn whoseTuren) {
    super(size);
    if (size <= 0 || size % 2 != 0){
      throw new IllegalArgumentException("Board size must be positive and even");
    }
    this.whoseTurn = whoseTuren;
    compassX.put("left", -1);
    compassX.put("right", 1);
    compassX.put("up", 0);
    compassX.put("down", 0);
    compassX.put("ne", 1);
    compassX.put("nw", -1);
    compassX.put("se", 1);
    compassX.put("sw", -1);


    compassY.put("left", 0);
    compassY.put("right", 0);
    compassY.put("up", -1);
    compassY.put("down", 1);
    compassY.put("ne", -1);
    compassY.put("nw", -1);
    compassY.put("se", 1);
    compassY.put("sw", 1);
    log = new StringBuilder();
    playGame();
    for (Position coord : grid1.keySet()) {
      Cell originalCell = grid1.get(coord);
      Cell newCell = new Cell(originalCell.getContent());
      this.grid.put(coord, newCell);
    }
  }

  /**
   * Sets up the game board, initializes the grid, and places the starting pieces for a square.
   * Game of reversi - primarily differs in the way the board is setup + where the starting pieces.
   * are placed.
   */
  private void playGame() {
    if (gameState == GameState.PRE) {
      gameState = GameState.INPROGRESS;

      for (int row = 0; row < size ; row++){
        for (int column = 0; column < size; column++){
          grid.put(new CartesianCoordinate(column, row), new Cell(Disc.EMPTY));
        }
      }


      grid.put(new CartesianCoordinate(size/2 - 1, size/2 - 1), new Cell(Disc.BLACK)); // top left
      grid.put(new CartesianCoordinate(size/2, size/2 - 1), new Cell(Disc.WHITE)); // top right
      grid.put(new CartesianCoordinate(size/2, size/2), new Cell(Disc.BLACK)); // bottom right
      grid.put(new CartesianCoordinate(size/2 - 1, size/2), new Cell(Disc.WHITE)); // bottom left
    } else {
      throw new IllegalStateException("A game has already been started");
    }

    notifyObservers();
  }


  /**
   * Helper method to determine the captured pieces in a specific direction from the given.
   * destination. Kept seperate to use CartesianCoordinates as those are what are used in a.
   * Square game - allows for proper .equals() and contains calls.
   *
   * @param dest The destination position to start capturing pieces from.
   * @param dir  The direction in which to capture pieces (e.g., "NE", "SW", etc.).
   * @return An ArrayList containing the coordinates of the captured pieces, or an empty list.
   * if no pieces are captured.
   */
  private ArrayList<Integer> moveHelper(Position dest, String dir) {
    ArrayList<Integer> captured = new ArrayList<>();
    boolean validMove = true;
    boolean endFound = false;
    CartesianCoordinate nextPiece = new CartesianCoordinate((dest.getFirstCoordinate() +
            compassX.get(dir)), (dest.getSecondCoordinate()
            + compassY.get(dir)));

    if (grid.containsKey(nextPiece) && grid.get(nextPiece).getContent() == this.oppositeColor()) {
      while (grid.containsKey(nextPiece) && !endFound) {
        if (grid.get(nextPiece).getContent() == this.currentColor()) {
          endFound = true;
        } else {
          captured.add(nextPiece.getFirstCoordinate());
          captured.add(nextPiece.getSecondCoordinate());
          nextPiece = new CartesianCoordinate((nextPiece.getFirstCoordinate() + compassX.get(dir)),
                  (nextPiece.getSecondCoordinate()
                          + compassY.get(dir)));
        }
      }

      if (!endFound) {
        captured.clear();
        return captured;
      }

      ArrayList<Integer> capturedCopy = new ArrayList<>();

      for (Integer element : captured) {
        capturedCopy.add(element);
      }


      while (!capturedCopy.isEmpty()) {
        int x = capturedCopy.get(0);
        int y = capturedCopy.get(1);
        if (!grid.get(new CartesianCoordinate(x, y)).getContent().equals(this.oppositeColor())) {
          validMove = false;
        }
        capturedCopy.remove(0);
        capturedCopy.remove(0);
      }

      if (validMove) {
        captured.add(dest.getFirstCoordinate());
        captured.add(dest.getSecondCoordinate());
        return captured;
      } else {
        captured.clear();
        return captured;
      }
    } else {
      captured.clear();
      return captured;
    }
  }

  @Override
  // override as a square game the grid only holds CartesianCoordinates, so it must be
  // specified when doing the contains and working with the game's map/grid.
  public void makeMove(Position dest) {
    CartesianCoordinate dest1 = new CartesianCoordinate(dest.getFirstCoordinate(), dest.getSecondCoordinate());
    if (gameState != GameState.PRE) {
      if (!grid.keySet().contains(new CartesianCoordinate(dest.getFirstCoordinate(), dest.getSecondCoordinate()))) {
        throw new IllegalArgumentException("This space does not exist on the board");
      }
      if (grid.get(dest1).getContent() != Disc.EMPTY) {
        throw new IllegalArgumentException("This space is already occupied");
      }

      if (dest == null) {
        throw new IllegalArgumentException("Can't pass in a null coordinate");
      }

      boolean validMove = false;
      ArrayList<String> errors = new ArrayList<>();
      ArrayList<Integer> allcaptured = new ArrayList<>();

      for (String direction : compassX.keySet()) {
        try {
          ArrayList<Integer> caught = this.moveHelper(dest, direction);
          allcaptured.addAll(caught);
          validMove = true;
        } catch (IllegalArgumentException e) {
          errors.add(e.getMessage());
        }
      }

      if (!validMove) {
        throw new IllegalArgumentException("Invalid move. Reasons: "
                + String.join(", ", errors));
      }

      if (allcaptured.isEmpty()) {
        throw new IllegalArgumentException("Invalid move.");
      }

      while (!allcaptured.isEmpty()) {
        int q = allcaptured.remove(0);
        int r = allcaptured.remove(0);
        this.placeDisc(q, r, this.currentColor());
      }
      this.passTurn();
      this.consecPasses = 0;
      log.append("Successful move at ").append(dest).append("\n");
    } else {
      throw new IllegalStateException("The game has not been started yet no move can be made");
    }

    notifyObservers();
    notifyTurnChange();
  }

  @Override
  // override as a square game the grid only holds CartesianCoordinates, so it must be
  // specified when doing the contains

  public void placeDisc(int q, int r, Disc disc) {
    if (gameState != GameState.PRE) {
      if (!(grid.keySet().contains(new CartesianCoordinate(q, r)))) {
        throw new IllegalArgumentException("This cell doesn't exist in the above grid ");
      }
      grid.get(new CartesianCoordinate(q, r)).setContent(disc);
      log.append("Placed ").append(disc.toString()).append(" disc").append("\n");
    } else {
      throw new IllegalStateException("The game has not been started yet this cannot be done");
    }
  }

  @Override
  // override as a square game the grid only holds CartesianCoordinates, so it must be
  // specified when doing the contains and working with the game's map/grid.
  public Disc getDiscAt(int q, int r) {
    if (gameState != GameState.PRE) {
      if (!(grid.keySet().contains(new CartesianCoordinate(q, r)))) {
        throw new IllegalArgumentException("This cell doesn't exist in the above grid ");
      }
      log.append("Retrieved disc ");
      return grid.get(new CartesianCoordinate(q, r)).getContent();
    } else {
      throw new IllegalStateException("The game has not been started yet this cannot be done");
    }
  }

  @Override
  // override as a square game the grid only holds CartesianCoordinates, so the possible moves must.
  // maintain this same format for the AI strategies to implement effectively.
  public ArrayList<Position> getPossibleMoves() {
    ArrayList<Position> possibleMoves = new ArrayList<>();
    for (int row = 0; row < size; row++) {
      for (int column = 0; column < size; column++) {
        if (isCellEmpty(column, row) && validMove(new CartesianCoordinate(column, row),
                currentColor())) {
          possibleMoves.add(new CartesianCoordinate(column, row));
          log.append("Found possible move at " + column + " " + row).append("\n");
        }
      }
    }
    return possibleMoves;
  }

  @Override
  // override as a square game the grid only holds CartesianCoordinates, and when creating deep.
  // copies to test moves you need to do so on a Square copy.
  public boolean validMove(Position coor, Disc currentTurn) {
    boolean flag = true;
    Turn turn = null;

    if (currentColor().equals(Disc.BLACK)) {
      turn = Turn.BLACK;
    } else {
      turn = Turn.WHITE;
    }
    SquareBoard copy = new SquareBoard(getSize(), createCopyOfBoard(), turn);

    try {
      copy.makeMove(coor);
      return true;
    } catch (IllegalArgumentException e) {
      return false;
    }
  }

  @Override
  // override as a square game the grid only holds CartesianCoordinates, so it must be
  // specified when doing the contains and working with the game's map/grid.
  public boolean isCellEmpty(int q, int r) {
    if (gameState != GameState.PRE) {
      if (!(grid.keySet().contains(new CartesianCoordinate(q, r)))) {
        throw new IllegalArgumentException("This cell doesn't exist in the above grid ");
      }
      return grid.get(new CartesianCoordinate(q, r)).getContent() == Disc.EMPTY;
    } else {
      throw new IllegalStateException("The game has not been started this cannot be checked");
    }
  }

  /**
   * Calculates the score change for a player after making a move on a Reversi game model.
   * Note: This method is not used with a square board as there is no hint functionality,
   * so this method isn't used in that context.
   *
   * @param model  The Reversi game model representing the current state of the game.
   * @param move   The position where the player intends to make a move.
   * @param player The player for whom the score change is calculated (Disc.BLACK or Disc.WHITE).
   * @return The change in score for the specified player after making the move, or 0 if the move is invalid.
   */
  public int getScoreForPlayer(ReversiReadOnly model, Position move, Disc player){
    return 0;
  }

  @Override
  // override as a square game the grid only holds CartesianCoordinates, and when creating deep.
  // copies to test moves you need to do so on a Square copy.
  public int checkMove(ReversiReadOnly model, Position move) {
    Turn turn = null;
    if (model.currentColor().equals(Disc.BLACK)) {
      turn = Turn.BLACK;
    } else {
      turn = Turn.WHITE;
    }
    SquareBoard copy = new SquareBoard(model.getSize(), model.createCopyOfBoard(), turn);
    int score = 0;
    int oldScore = copy.getScore(model.currentColor());
    try {
      copy.makeMove(move);
      score = copy.getScore(model.currentColor()) - oldScore - 1;
      return score;
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException(e);
    }
  }

  @Override
  // override as a square game the grid only holds CartesianCoordinates, and when creating deep.
  // copies to test moves you need to do so on a Square copy.
  protected boolean hasValidMoves(Disc playerDisc) {
    log.append("Checking for valid moves for " + playerDisc.toString() + "\n");
    Turn current;
    if (playerDisc.equals(Disc.BLACK)) {
      current = Turn.BLACK;
    } else {
      current = Turn.WHITE;
    }
    for (Position coord : grid.keySet()) {
      SquareBoard dupe = new SquareBoard(size, this.createCopyOfBoard(), current);
      if (this.createCopyOfBoard().get(coord).getContent().equals(Disc.EMPTY)) {
        try {
          dupe.makeMove(coord);
          log.append("Valid moves were found "+ "\n");
          return true;
        } catch (IllegalArgumentException e) {
          // Ignore and continue checking other moves
        }
      }
    }
    log.append("No Valid moves were found "+ "\n");
    return false;
  }

  /**
   * Used to return a map representation of the current game board, which holds what pieces.
   * are placed at what positions.
   *
   * @return returns a map representation of the current game board.
   */
  @Override
  public Map<Position, Cell> getMap() {
    log.append("Grid was retrived \n");
    return grid;
  }

  /**
   * Returns the color of the disc of the current player.
   *
   * @return The disc color of the current player.
   */
  @Override
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
  @Override
  public void addObserver(ControllerFeatures controller) {
    log.append("Controller added to observer catalog \n");
    observers.add(controller);
  }

  /**
   * Notifies all registered observers of a change in the object's state.
   * This method is typically called to inform the observers about an update or change in the state
   * that requires their attention, usually resulting in a change or refresh of their view or data.
   * Each observer's `updateView` method is called to perform these updates.
   */
  @Override
  public void notifyObservers() {
    for (ControllerFeatures controller : observers) {
      controller.updateView();
      log.append("A controller was notified \n");

    }
  }

  /**
   * Notifies all registered observers of a change in the current player's turn.
   * This method is invoked to communicate to observers that the turn has switched
   * from one player to another in the game. It triggers the `handleTurnChange`
   * method in each observer, allowing them to respond appropriately to the new
   * game state, such as updating the UI or initiating AI moves.
   */
  @Override
  public void notifyTurnChange() {
    for (ControllerFeatures controller : observers) {
      controller.handleTurnChange(currentColor());
      log.append("A controller was notified of a turn change \n");
    }
  }

  /**
   * Passes the turn to the next player.
   * used to swap turns either when a player passes their turn or at the end of their move and then.
   * updates all subcribed listeners about the change in game state.
   */
  @Override
  public void passTurn() {
    if (gameState != GameState.PRE) {
      if (whoseTurn == Turn.BLACK) {
        whoseTurn = Turn.WHITE;
        log.append("Turn was passed to White \n");
      } else {
        whoseTurn = Turn.BLACK;
        log.append("Turn was passed to Black \n");
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
  @Override
  public int getSize() {
    return size;
  }

  /**
   * A helper method to check who wins based on who has more pieces on the board at the end of game.
   * Changes the state of the game to reflect and is only called in a situation where.
   * IsGameOver would indicate the game is over (returns true).
   */
  @Override
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
  @Override
  public boolean isGameOver() {
    log.append("Game over being checked \n");
    if (gameState == GameState.PRE) {
      throw new IllegalStateException("The game has not been started this cannot be checked");
    }
    // Check for consecutive passes
    if (consecPasses == 2) {
      whoWins();
      log.append("Game is over \n");
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
      log.append("Game is over \n");
      return true;
    }

    if (getScore(Disc.BLACK) == 0 || getScore(Disc.WHITE) == 0){
      whoWins();
      log.append("Game is over \n");
      return true;
    }

    // Check for available moves for each player
    boolean player1 = hasValidMoves(Disc.BLACK);
    boolean player2 = hasValidMoves(Disc.WHITE);

    if (!player1 && !player2) {
      whoWins();
      log.append("Game is over \n");
      return true;
    }

    log.append("Game was not over \n");
    return false;
  }

  /**
   * Retrieves the score for the inputted player by checking through how many pieces are placed.
   *
   * @return The number of pieces (score) of a certain player.
   */
  @Override
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
   * Creates a deep copy of the board's map which contains all board positions.
   *
   * @return A deep copy of the board.
   */
  @Override
  public HashMap<Position, Cell> createCopyOfBoard() {
    HashMap<Position, Cell> copy = new HashMap<Position, Cell>();
    for (Position coord : grid.keySet()) {
      Cell originalCell = grid.get(coord);
      Cell newCell = new Cell(originalCell.getContent());
      copy.put(coord, newCell);
    }
    log.append("Deep copy of board was made \n");
    return copy;
  }

  /**
   * Retrieves the state of the game.
   *
   * @return The state of the board.
   */
  @Override
  public GameState getState() {
    return this.gameState;
  }


  /**
   * Returns the color of the disc of the opponent player.
   *
   * @return The disc color of the opponent.
   */
  @Override
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
   * Helps in the creation of an output log to ensure code runs as expected, and we can see the.
   * finer details of the AI decisions
   * @return the string output of the log of actions
   */
  public String getLog(){
    return log.toString();
  }
}
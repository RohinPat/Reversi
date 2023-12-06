package model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.util.ArrayList;

import controller.ControllerFeatures;
import controller.ReversiController;

/**
 * Represents the game board for a hexagonal grid-based game.
 */
public class Board implements Reversi {
  private final Map<Coordinate, Cell> grid;

  //INVARIANT: should always be positive
  private final int size;
  private Turn whoseTurn;
  private final HashMap<String, Integer> compassQ = new HashMap<>();
  private final HashMap<String, Integer> compassR = new HashMap<>();

  //INVARIANT: should always be positive (never subtracted from, initialized at 0)
  private int consecPasses;
  private GameState gameState;

  private List<ControllerFeatures> observers = new ArrayList<>();

  /**
   * Adds a {@link ReversiController} observer to the list of observers.
   * This method is used to register a controller as an observer that should be notified
   * of changes to the state of this object.
   *
   * @param controller The {@link ReversiController} instance to be added as an observer.
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

  public void notifyTurnChange() {
    for (ControllerFeatures controller : observers) {
      controller.handleTurnChange(currentColor());
    }
  }

  /**
   * Initializes a new game board of the specified size.
   *
   * @param size The size of the board.
   */
  public Board(int size) {
    this.consecPasses = 0;
    if (size <= 0) {
      throw new IllegalArgumentException("Size must be positive");
    }
    this.size = size;
    this.grid = new HashMap<>();
    this.whoseTurn = Turn.BLACK;
    compassQ.put("east", 1);
    compassQ.put("west", -1);
    compassQ.put("ne", 1);
    compassQ.put("nw", 0);
    compassQ.put("se", 0);
    compassQ.put("sw", -1);
    compassR.put("east", 0);
    compassR.put("west", 0);
    compassR.put("ne", -1);
    compassR.put("nw", -1);
    compassR.put("se", 1);
    compassR.put("sw", 1);
    this.gameState = GameState.PRE;
    playGame();
  }

  /**
   * Initializes a new game board of the specified size, and overwrites the board using a.
   * Given map of disc placements.
   *
   * @param size       The size of the board.
   * @param grid1      The hashmap of discs to be overwritten onto the old grid.
   * @param whoseTuren The turn that's to be instantiated in the new game.
   */
  public Board(int size, HashMap<Coordinate, Cell> grid1, Turn whoseTuren) {
    this.consecPasses = 0;
    if (size <= 0) {
      throw new IllegalArgumentException("Size must be positive");
    }
    this.size = size;
    this.grid = new HashMap<>();
    this.whoseTurn = whoseTuren;
    compassQ.put("east", 1);
    compassQ.put("west", -1);
    compassQ.put("ne", 1);
    compassQ.put("nw", 0);
    compassQ.put("se", 0);
    compassQ.put("sw", -1);
    compassR.put("east", 0);
    compassR.put("west", 0);
    compassR.put("ne", -1);
    compassR.put("nw", -1);
    compassR.put("se", 1);
    compassR.put("sw", 1);
    this.gameState = GameState.PRE;
    playGame();
    for (Coordinate coord : grid1.keySet()) {
      Cell originalCell = grid1.get(coord);
      Cell newCell = new Cell(originalCell.getContent());
      this.grid.put(coord, newCell);
    }
  }

  /**
   * Sets up the game board, initializes the grid, and places the starting pieces.
   */
  private void playGame() {
    if (gameState == GameState.PRE) {
      gameState = GameState.INPROGRESS;

      // this first loop sets up the first half of rows not including the middle row
      for (int upperRow = 0; upperRow < size - 1; upperRow++) {
        for (int index = -upperRow; index < size; index++) {
          grid.put(new Coordinate(index, -(size - 1 - upperRow)), new Cell(Disc.EMPTY));
        }
      }
      // this second loop sets up the rest of the rows INCLUDING the middle row - intializes every
      // cell to be empty at first
      for (int lowerRow = 0; lowerRow < size; lowerRow++) {
        for (int index = -(size - 1); index < size - lowerRow; index++) {
          grid.put(new Coordinate(index, lowerRow), new Cell(Disc.EMPTY));
        }
      }

      grid.put(new Coordinate(1, 0), new Cell(Disc.BLACK));
      grid.put(new Coordinate(1, -1), new Cell(Disc.WHITE));
      grid.put(new Coordinate(0, -1), new Cell(Disc.BLACK));
      grid.put(new Coordinate(-1, 0), new Cell(Disc.WHITE));
      grid.put(new Coordinate(-1, 1), new Cell(Disc.BLACK));
      grid.put(new Coordinate(0, 1), new Cell(Disc.WHITE));
    } else {
      throw new IllegalStateException("A game has already been started");
    }

    notifyObservers();
  }

  /**
   * Returns the color of the disc of the current player.
   *
   * @return The disc color of the current player.
   */
  public Disc currentColor() {
    if (gameState != GameState.PRE) {
      if (this.whoseTurn == Turn.BLACK) {
        return Disc.BLACK;
      } else {
        return Disc.WHITE;
      }
    } else {
      throw new IllegalStateException("This cannot be checked yet");
    }
  }

  /**
   * Returns the color of the disc of the opponent player.
   *
   * @return The disc color of the opponent.
   */
  private Disc oppositeColor() {
    if (gameState == GameState.INPROGRESS) {
      if (this.whoseTurn == Turn.BLACK) {
        return Disc.WHITE;
      } else {
        return Disc.BLACK;
      }
    } else {
      throw new IllegalStateException("This cannot be checked yet");
    }
  }

  /**
   * Passes the turn to the next player.
   * used to swap turns either when a player passes their turn or at the end of their move
   */
  public void passTurn() {
    if (gameState == GameState.INPROGRESS) {
      if (this.whoseTurn == Turn.BLACK) {
        this.whoseTurn = Turn.WHITE;
      } else {
        this.whoseTurn = Turn.BLACK;
      }
      consecPasses += 1;
    } else {
      throw new IllegalStateException("The game has not been started yet no move can be made");
    }

    notifyObservers();
    notifyTurnChange();
  }

  private ArrayList<Integer> moveHelper(Coordinate dest, String dir) {
    ArrayList<Integer> captured = new ArrayList<>();
    boolean validMove = true;
    boolean endFound = false;
    Coordinate nextPiece = new Coordinate((dest.getQ() + compassQ.get(dir)), (dest.getR()
            + compassR.get(dir)));

    if (grid.containsKey(nextPiece) && grid.get(nextPiece).getContent() == this.oppositeColor()) {
      while (grid.containsKey(nextPiece) && !endFound) {
        if (grid.get(nextPiece).getContent() == this.currentColor()) {
          endFound = true;
        } else {
          captured.add(nextPiece.getQ());
          captured.add(nextPiece.getR());
          nextPiece = new Coordinate((nextPiece.getQ() + compassQ.get(dir)), (nextPiece.getR()
                  + compassR.get(dir)));
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
        int q = capturedCopy.get(0);
        int r = capturedCopy.get(1);
        if (!grid.get(new Coordinate(q, r)).getContent().equals(this.oppositeColor())) {
          validMove = false;
        }
        capturedCopy.remove(0);
        capturedCopy.remove(0);
      }

      if (validMove) {
        captured.add(dest.getQ());
        captured.add(dest.getR());
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

  /**
   * Attempts to make a move on the board by placing the current
   * player's disc at the specified coordinate.
   * The method validates the move, flips any captured opponent discs,
   * and switches the turn to the next player.
   *
   * @param dest The target coordinate where the current player's disc should be placed.
   * @throws IllegalArgumentException If the move is invalid, such as when the target
   *                                  cell is already occupied or doesn't result in any opponent
   *                                  disc captures.
   */
  public void makeMove(Coordinate dest) {
    if (gameState != GameState.PRE) {
      if (!grid.keySet().contains(new Coordinate(dest.getQ(), dest.getR()))) {
        throw new IllegalArgumentException("This space does not exist on the board");
      }

      if (grid.get(dest).getContent() != Disc.EMPTY) {
        throw new IllegalArgumentException("This space is already occupied");
      }

      if (dest == null) {
        throw new IllegalArgumentException("Can't pass in a null coordinate");
      }

      boolean validMove = false;
      ArrayList<String> errors = new ArrayList<>();
      ArrayList<Integer> allcaptured = new ArrayList<>();

      for (String direction : compassQ.keySet()) {
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
      this.changeTurn();
      this.consecPasses = 0;
    } else {
      throw new IllegalStateException("The game has not been started yet no move can be made");
    }

    notifyObservers();
    notifyTurnChange();
  }

  public boolean validMove(Coordinate coor, Disc currentTurn){
    boolean flag = true;
    Turn turn = null;

    if (this.currentColor().equals(Disc.BLACK)){
      turn = Turn.BLACK;
    }
    else{
      turn = Turn.WHITE;
    }
    Board copy = new Board(this.getSize(), this.createCopyOfBoard(), turn);
    try{
      copy.makeMove(coor);
      return true;
    } catch (IllegalArgumentException e) {
      return false;
    }
  }


  public int checkMove(ReversiReadOnly model, Coordinate move){
    Turn turn = null;
    if (model.currentColor().equals(Disc.BLACK)){
      turn = Turn.BLACK;
    }
    else{
      turn = Turn.WHITE;
    }
    Board copy = new Board(model.getSize(), model.createCopyOfBoard(), turn);
    int score = 0;
    try{
      copy.makeMove(move);
      score = copy.getScore(model.currentColor());
      return score;
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException(e);
    }
  }

  /**
   * Places a disc at the specified cell coordinates.
   *
   * @param q    The q-coordinate of the cell.
   * @param r    The r-coordinate of the cell.
   * @param disc The disc to be placed.
   * @throws IllegalArgumentException If the cell doesn't exist in the grid.
   */
  public void placeDisc(int q, int r, Disc disc) {
    if (gameState == GameState.INPROGRESS) {
      if (!(grid.keySet().contains(new Coordinate(q, r)))) {
        throw new IllegalArgumentException("This cell doesn't exist in the above grid ");
      }
      grid.get(new Coordinate(q, r)).setContent(disc);
    } else {
      throw new IllegalStateException("The game has not been started yet this cannot be done");
    }
  }

  /**
   * Retrieves the disc at the specified cell coordinates.
   *
   * @param q The q-coordinate of the cell.
   * @param r The r-coordinate of the cell.
   * @return The disc present at the specified coordinates.
   * @throws IllegalArgumentException If the cell doesn't exist in the grid.
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
   * Checks if the cell at the specified coordinates is empty.
   *
   * @param q The q-coordinate of the cell.
   * @param r The r-coordinate of the cell.
   * @return True if the cell is empty, otherwise false.
   * @throws IllegalArgumentException If the cell doesn't exist in the grid.
   */
  public boolean isCellEmpty(int q, int r) {
    if (gameState == GameState.INPROGRESS) {
      if (!(grid.keySet().contains(new Coordinate(q, r)))) {
        throw new IllegalArgumentException("This cell doesn't exist in the above grid ");
      }
      return grid.get(new Coordinate(q, r)).getContent() == Disc.EMPTY;
    } else {
      throw new IllegalStateException("The game has not been started this cannot be checked");
    }
  }

  /**
   * Retrieves the size of the game board.
   *
   * @return The size of the board.
   */
  public int getSize() {
    return size;
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
   * A helper method to check who wins based on who has more pieces on the board at the end of game.
   * Changes the state of the game to reflect and is only called in a situation where.
   * IsGameOver would indicate the game is over (returns true).
   */
  private void whoWins() {
    int blackScore = this.getScore(Disc.BLACK);
    int whiteScore = this.getScore(Disc.WHITE);

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

      this.whoWins();
      return true;
    }

    // Check if all cells are filled
    boolean allCellsFilled = true;
    for (Cell cell : grid.values()) {
      if (cell.getContent() == Disc.EMPTY) {
        allCellsFilled = false;
        break;
      }
    }
    if (allCellsFilled) {
      this.whoWins();
      return true;
    }

    // Check for available moves for each player
    boolean player1 = hasValidMoves(Disc.BLACK);
    boolean player2 = hasValidMoves(Disc.WHITE);

    if (!player1 && !player2) {
      this.whoWins();
      return true;
    }

    return false;
  }

  private boolean hasValidMoves(Disc playerDisc) {
    Turn current;
    if (playerDisc.equals(Disc.BLACK)) {
      current = Turn.BLACK;
    } else {
      current = Turn.WHITE;
    }
    Board dupe = new Board(size, this.createCopyOfBoard(), current);

    for (Coordinate coord : grid.keySet()) {
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
   * Creates a copy of the board.
   *
   * @return A copy of the board.
   */
  public HashMap<Coordinate, Cell> createCopyOfBoard() {
    HashMap<Coordinate, Cell> copy = new HashMap<Coordinate, Cell>();
    for (Coordinate coord : this.grid.keySet()) {
      Cell originalCell = this.grid.get(coord);
      Cell newCell = new Cell(originalCell.getContent());
      copy.put(coord, newCell);
    }
    return copy;
  }

  /**
   * Returns a list of all possible moves for the current player.
   *
   * @return A list of all possible moves for the current player.
   */
  public ArrayList<Coordinate> getPossibleMoves() {
    ArrayList<Coordinate> possibleMoves = new ArrayList<>();
    Board og = new Board(size, this.createCopyOfBoard(), this.whoseTurn);
    for (Coordinate coord : grid.keySet()) {
      if (grid.get(coord).getContent().equals(Disc.EMPTY)) {
        try {
          Board og1 = new Board(size, og.createCopyOfBoard(), this.whoseTurn);
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
   * Changes the current turn to the next player's turn in the Reversi game.
   * If the game is in progress, this method toggles the turn between black and white players.
   * Additionally, it increments the count of consecutive passes.
   *
   * @throws IllegalStateException if the game has not been started yet, indicating that
   *                               no move can be made until the game begins.
   */
  public void changeTurn() {
    if (gameState == GameState.INPROGRESS) {
      if (this.whoseTurn == Turn.BLACK) {
        this.whoseTurn = Turn.WHITE;
      } else {
        this.whoseTurn = Turn.BLACK;
      }
      consecPasses += 1;
    } else {
      throw new IllegalStateException("The game has not been started yet no move can be made");
    }
  }

  public Map<Coordinate, Cell> getMap(){
    return grid;
  }
}


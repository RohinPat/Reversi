package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import controller.ControllerFeatures;
import controller.ReversiController;
import controller.aistrat.CaptureCorners;


public class SquareBoard implements Reversi{
  private final Map<Position, Cell> grid1;
  private int consecPasses;
  private GameState gameState;
  private Turn whoseTurn;
  private int size;
  private List<ControllerFeatures> observers = new ArrayList<>();
  private final HashMap<String, Integer> compassX = new HashMap<>();
  private final HashMap<String, Integer> compassY = new HashMap<>();

  private List<ControllerFeatures> observersSquare = new ArrayList<>();

/**
   * Notifies all registered observers of a change in the object's state.
   * This method is typically called to inform the observers about an update or change in the state
   * that requires their attention, usually resulting in a change or refresh of their view or data.
   * Each observer's `updateView` method is called to perform these updates.
   */

  public void notifyObservers() {
    for (ControllerFeatures controller : observersSquare) {
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
    for (ControllerFeatures controller : observersSquare) {
      controller.handleTurnChange(currentColor());
    }
  }

  public SquareBoard(int size){
    if (size <= 0 || size % 2 != 0){
      throw new IllegalArgumentException("Board size must be positive and even");
    }
    this.size = size;
    this.grid1 = new HashMap<>();
    this.whoseTurn = Turn.BLACK;
    this.gameState = GameState.PRE;
    this.consecPasses = 0;
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
  }

  private void playGame() {
    if (gameState == GameState.PRE) {
      gameState = GameState.INPROGRESS;

      for (int row = 0; row < size ; row++){
        for (int column = 0; column < size; column++){
          grid1.put(new CartesianCoordinate(column, row), new Cell(Disc.EMPTY));
        }
      }


      grid1.put(new CartesianCoordinate(size/2 - 1, size/2 - 1), new Cell(Disc.BLACK)); // top left
      grid1.put(new CartesianCoordinate(size/2, size/2 - 1), new Cell(Disc.WHITE)); // top right
      grid1.put(new CartesianCoordinate(size/2, size/2), new Cell(Disc.BLACK)); // bottom right
      grid1.put(new CartesianCoordinate(size/2 - 1, size/2), new Cell(Disc.WHITE)); // bottom left
    } else {
      throw new IllegalStateException("A game has already been started");
    }

    notifyObservers();
  }


  @Override
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

  @Override
/**
   * Passes the turn to the next player.
   * used to swap turns either when a player passes their turn or at the end of their move
   */

  public void passTurn() {
    if (gameState != GameState.PRE) {
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

  private ArrayList<Integer> moveHelper(Position dest, String dir) {
    ArrayList<Integer> captured = new ArrayList<>();
    boolean validMove = true;
    boolean endFound = false;
    CartesianCoordinate nextPiece = new CartesianCoordinate((dest.getFirstCoordinate() + compassX.get(dir)), (dest.getSecondCoordinate()
            + compassY.get(dir)));

    if (grid1.containsKey(nextPiece) && grid1.get(nextPiece).getContent() == this.oppositeColor()) {
      while (grid1.containsKey(nextPiece) && !endFound) {
        if (grid1.get(nextPiece).getContent() == this.currentColor()) {
          endFound = true;
        } else {
          captured.add(nextPiece.getFirstCoordinate());
          captured.add(nextPiece.getSecondCoordinate());
          nextPiece = new CartesianCoordinate((nextPiece.getFirstCoordinate() + compassX.get(dir)), (nextPiece.getSecondCoordinate()
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
        if (!grid1.get(new CartesianCoordinate(x, y)).getContent().equals(this.oppositeColor())) {
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
  public void makeMove(Position dest) {
    if (gameState != GameState.PRE) {
      if (!grid1.keySet().contains(new CartesianCoordinate(dest.getFirstCoordinate(), dest.getSecondCoordinate()))) {
        throw new IllegalArgumentException("This space does not exist on the board");
      }

      if (grid1.get(dest).getContent() != Disc.EMPTY) {
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
    } else {
      throw new IllegalStateException("The game has not been started yet no move can be made");
    }

    notifyObservers();
    notifyTurnChange();
  }


  @Override
  public void placeDisc(int q, int r, Disc disc) {
    if (gameState != GameState.PRE) {
      if (!(grid1.keySet().contains(new CartesianCoordinate(q, r)))) {
        throw new IllegalArgumentException("This cell doesn't exist in the above grid1 ");
      }
      grid1.get(new CartesianCoordinate(q, r)).setContent(disc);
    } else {
      throw new IllegalStateException("The game has not been started yet this cannot be done");
    }
  }

  @Override
  public Disc getDiscAt(int q, int r) {
    if (gameState != GameState.PRE) {
      if (!(grid1.keySet().contains(new CartesianCoordinate(q, r)))) {
        throw new IllegalArgumentException("This cell doesn't exist in the above grid1 ");
      }
      return grid1.get(new CartesianCoordinate(q, r)).getContent();
    } else {
      throw new IllegalStateException("The game has not been started yet this cannot be done");
    }
  }

  @Override
  public boolean isCellEmpty(int q, int r) {
    if (gameState == GameState.INPROGRESS) {
      if (!(grid1.keySet().contains(new CartesianCoordinate(q, r)))) {
        throw new IllegalArgumentException("This cell doesn't exist in the above grid1 ");
      }
      return grid1.get(new CartesianCoordinate(q, r)).getContent() == Disc.EMPTY;
    } else {
      throw new IllegalStateException("The game has not been started this cannot be checked");
    }
  }

  @Override
  public int getSize() {
    return size;
  }

  @Override
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
    for (Cell cell : grid1.values()) {
      if (cell.getContent() == Disc.EMPTY) {
        allCellsFilled = false;
        break;
      }
    }
    if (allCellsFilled) {
      this.whoWins();
      return true;
    }

    if (this.getScore(Disc.BLACK) == 0 || this.getScore(Disc.WHITE) == 0){
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

  /**
   * Checks if the specified player has any valid moves available on the board.
   * This method creates a copy of the current game board and iterates through
   * all possible moves to determine if at least one valid move exists for the player.
   *
   * @param playerDisc The {@link Disc} representing the player to check for valid moves.
   * @return True if there are valid moves available for the player, false otherwise.
   */
  private boolean hasValidMoves(Disc playerDisc) {
    Turn current;
    if (playerDisc.equals(Disc.BLACK)) {
      current = Turn.BLACK;
    } else {
      current = Turn.WHITE;
    }
    for (Position coord : grid1.keySet()) {
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

  @Override
  public int getScore(Disc player) {
    int scoreCounter = 0;
    for (Cell cell : grid1.values()) {
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

  @Override
  public HashMap<Position, Cell> createCopyOfBoard() {
    HashMap<Position, Cell> copy = new HashMap<Position, Cell>();
    for (Position coord : this.grid1.keySet()) {
      Cell originalCell = this.grid1.get(coord);
      Cell newCell = new Cell(originalCell.getContent());
      copy.put(coord, newCell);
    }
    return copy;
  }

  @Override
  public ArrayList<Position> getPossibleMoves() {
    ArrayList<Position> possibleMoves = new ArrayList<>();
    Board og = new Board(size, this.createCopyOfBoard(), this.whoseTurn);
    for (Position coord : grid1.keySet()) {
      if (grid1.get(coord).getContent().equals(Disc.EMPTY)) {
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

  @Override
  public int checkMove(ReversiReadOnly model, Position move) {
    Turn turn = null;
    if (model.currentColor().equals(Disc.BLACK)) {
      turn = Turn.BLACK;
    } else {
      turn = Turn.WHITE;
    }
    Board copy = new Board(model.getSize(), model.createCopyOfBoard(), turn);
    int score = 0;
    try {
      copy.makeMove(move);
      score = copy.getScore(model.currentColor());
      return score;
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException(e);
    }
  }

  @Override
  public boolean validMove(Position coor, Disc currentTurn) {
    boolean flag = true;
    Turn turn = null;

    if (this.currentColor().equals(Disc.BLACK)) {
      turn = Turn.BLACK;
    } else {
      turn = Turn.WHITE;
    }
    Board copy = new Board(this.getSize(), this.createCopyOfBoard(), turn);
    try {
      copy.makeMove(coor);
      return true;
    } catch (IllegalArgumentException e) {
      return false;
    }
  }

  @Override
  public Map<Position, Cell> getMap() {
    return grid1;
  }

  @Override
  public GameState getState() {
    return this.gameState;
  }

  @Override
  public void addObserver(ControllerFeatures controller) {
    observersSquare.add(controller);
  }
}

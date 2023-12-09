package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import controller.ControllerFeatures;
import controller.ReversiController;
import controller.aistrat.CaptureCorners;


public class SquareBoard implements Reversi{
  private final Map<Position, Cell> grid;
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
    this.grid = new HashMap<>();
    this.whoseTurn = Turn.BLACK; // or Turn.WHITE, depending on your game's starting player
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
    compassY.put("up", 1);
    compassY.put("down", -1);
    compassY.put("ne", 1);
    compassY.put("nw", 1);
    compassY.put("se", -1);
    compassY.put("sw", -1);
    playGame();
  }

  private void playGame() {
    if (gameState == GameState.PRE) {
      gameState = GameState.INPROGRESS;

      for (int row = 0; row < size ; row++){
        for (int column = 0; row < size; column++){
          grid.put(new CartesianCoordinate(column, row), new Cell(Disc.EMPTY));
        }
      }


      grid.put(new Coordinate(size/2 - 1, size/2), new Cell(Disc.BLACK)); // top left
      grid.put(new Coordinate(size/2, size/2), new Cell(Disc.WHITE)); // top right
      grid.put(new Coordinate(size/2, size/2- 1), new Cell(Disc.BLACK)); // bottom right
      grid.put(new Coordinate(size/2 - 1, size/2 - 1), new Cell(Disc.WHITE)); // bottom left
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

    if (grid.containsKey(nextPiece) && grid.get(nextPiece).getContent() == this.oppositeColor()) {
      while (grid.containsKey(nextPiece) && !endFound) {
        if (grid.get(nextPiece).getContent() == this.currentColor()) {
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
  public void makeMove(Position dest) {
    if (gameState != GameState.PRE) {
      if (!grid.keySet().contains(new Coordinate(dest.getFirstCoordinate(), dest.getSecondCoordinate()))) {
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
  public void placeDisc(int q, int r, Disc d) {

  }

  @Override
  public Disc getDiscAt(int q, int r) {
    return null;
  }

  @Override
  public boolean isCellEmpty(int q, int r) {
    return false;
  }

  @Override
  public int getSize() {
    return 0;
  }

  @Override
  public boolean isGameOver() {
    return false;
  }

  @Override
  public int getScore(Disc player) {
    return 0;
  }

  @Override
  public HashMap<Position, Cell> createCopyOfBoard() {
    return null;
  }

  @Override
  public ArrayList<Position> getPossibleMoves() {
    return null;
  }

  @Override
  public int checkMove(ReversiReadOnly model, Position move) {
    return 0;
  }

  @Override
  public boolean validMove(Position coor, Disc currentTurn) {
    return false;
  }

  @Override
  public Map<Position, Cell> getMap() {
    return null;
  }

  @Override
  public GameState getState() {
    return null;
  }

  @Override
  public void addObserver(ControllerFeatures controller) {

  }
}

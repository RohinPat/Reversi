package model;

import java.util.ArrayList;
import java.util.HashMap;

public class SquareBoard extends AbstractModel{

  protected final HashMap<String, Integer> compassX = new HashMap<>();
  // used to represent all the changes in the X coordinate to check capture
  // outcomes in the different capturing directions
  protected final HashMap<String, Integer> compassY = new HashMap<>();
  // used to represent all the changes in the Y coordinate to check capture
  // outcomes in the different capturing directions


  /**
   * Initializes a new square game board of the specified size.
   *
   * @param size The size of the board.
   */
  public SquareBoard(int size){
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
  public SquareBoard(int size, HashMap<Position, Cell> grid1, Turn whoseTuren) {
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
          return true;
        } catch (IllegalArgumentException e) {
          // Ignore and continue checking other moves
        }
      }
    }
    return false;
  }

}
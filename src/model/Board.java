package model;

import java.util.HashMap;
import java.util.ArrayList;

/**
 * Represents the game board for a hexagonal grid-based game.
 */
public class Board extends AbstractModel {
  protected final HashMap<String, Integer> compassQ = new HashMap<>();
  // used to represent all the changes in the Q coordinate to check capture
  // outcomes in the different capturing directions

  protected final HashMap<String, Integer> compassR = new HashMap<>();
  // used to represent all the changes in the R coordinate to check capture
  // outcomes in the different capturing directions

  /**
   * Initializes a new game board of the specified size.
   *
   * @param size The size of the board.
   */
  public Board(int size) {
    super(size);
    if (size <= 0) {
      throw new IllegalArgumentException("Size must be positive");
    }
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
  public Board(int size, HashMap<Position, Cell> grid1, Turn whoseTuren) {
    super(size);
    if (size <= 0) {
      throw new IllegalArgumentException("Size must be positive");
    }
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
    playGame();
    for (Position coord : grid1.keySet()) {
      Cell originalCell = grid1.get(coord);
      Cell newCell = new Cell(originalCell.getContent());
      this.grid.put(coord, newCell);
    }
  }

  /**
   * Sets up the game board, initializes the grid, and places the starting pieces for a hexagonal.
   * Game of reversi.
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
   * Helper method to determine the captured pieces in a specific direction from the given.
   * destination.
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
    Coordinate nextPiece = new Coordinate((dest.getFirstCoordinate() + compassQ.get(dir)),
            (dest.getSecondCoordinate()
            + compassR.get(dir)));

    if (grid.containsKey(nextPiece) && grid.get(nextPiece).getContent() == this.oppositeColor()) {
      while (grid.containsKey(nextPiece) && !endFound) {
        if (grid.get(nextPiece).getContent() == this.currentColor()) {
          endFound = true;
        } else {
          captured.add(nextPiece.getFirstCoordinate());
          captured.add(nextPiece.getSecondCoordinate());
          nextPiece = new Coordinate((nextPiece.getFirstCoordinate() + compassQ.get(dir)),
                  (nextPiece.getSecondCoordinate()
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
  public void makeMove(Position dest) {
    if (gameState != GameState.PRE) {
      if (!grid.keySet().contains(new Coordinate(dest.getFirstCoordinate(),
              dest.getSecondCoordinate()))) {
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
      this.passTurn();
      this.consecPasses = 0;
    } else {
      throw new IllegalStateException("The game has not been started yet no move can be made");
    }

    notifyObservers();
    notifyTurnChange();
  }

  /**
   * Calculates the score change for a player after making a move on a Reversi game model.
   * Doesn't account for current turn of the game - useful for the hints.
   *
   * @param model  The Reversi game model representing the current state of the game.
   * @param move   The position where the player intends to make a move.
   * @param player The player for whom the score change is calculated (Disc.BLACK or Disc.WHITE).
   * @return The change in score for the specified player after making the move, or 0 if the move.
   * is invalid.
   */
  public int getScoreForPlayer(ReversiReadOnly model, Position move, Disc player) {
    Turn turn = null;
    if (player.equals(Disc.BLACK)) {
      turn = Turn.BLACK;
    } else {
      turn = Turn.WHITE;
    }
    Board copy = new Board(model.getSize(), model.createCopyOfBoard(), turn);
    int score = 0;
    int oldScore = copy.getScore(player);
    try {
      copy.makeMove(move);
      score = copy.getScore(player) - oldScore - 1;
      return score;
    } catch (IllegalArgumentException e) {
      return 0;
    }
  }

}


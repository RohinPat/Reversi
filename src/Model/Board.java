package model;

import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;

/**
 * Represents the game board for a hexagonal grid-based game.
 */
public class Board {
  private Map<Coordinate, Cell> grid;
  private final int size;
  private Turn whoseTurn;
  private HashMap<String, Integer> compassQ = new HashMap<>();
  private HashMap<String, Integer> compassR = new HashMap<>();
  private int consecPasses;

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
  }

  /**
   * Sets up the game board, initializes the grid, and places the starting pieces.
   */
  public void playGame() {
    // this first loop sets up the first half of rows not including the middle row
    for (int i = 0; i < size - 1; i++) {
      for (int j = -i; j < size; j++) {
        grid.put(new Coordinate(j, -(size - 1 - i)), new Cell(Disc.EMPTY));
      }
    }

    // this second loop sets up the rest of the rows INCLUDING the middle row - intializes every
    // cell to be empty at first
    for (int i = 0; i < size; i++) {
      for (int j = -(size - 1); j < size - i; j++) {
        grid.put(new Coordinate(j, i), new Cell(Disc.EMPTY));
      }
    }

    //i'm thinking of adding the starting pieces in the constructor as well but this should be
    // easily moveable
    grid.put(new Coordinate(1, 0), new Cell(Disc.BLACK));
    grid.put(new Coordinate(1, -1), new Cell(Disc.WHITE));
    grid.put(new Coordinate(0, -1), new Cell(Disc.BLACK));
    grid.put(new Coordinate(-1, 0), new Cell(Disc.WHITE));
    grid.put(new Coordinate(-1, 1), new Cell(Disc.BLACK));
    grid.put(new Coordinate(0, 1), new Cell(Disc.WHITE));
  }

  /**
   * Returns the color of the disc of the current player.
   *
   * @return The disc color of the current player.
   */
  private Disc currentColor() {
    if (this.whoseTurn == Turn.BLACK) {
      return Disc.BLACK;
    } else {
      return Disc.WHITE;
    }
  }

  /**
   * Returns the color of the disc of the opponent player.
   *
   * @return The disc color of the opponent.
   */
  private Disc oppositeColor() {
    if (this.whoseTurn == Turn.BLACK) {
      return Disc.WHITE;
    } else {
      return Disc.BLACK;
    }
  }

  /**
   * Passes the turn to the next player.
   * used to swap turns either when a player passes their turn or at the end of their move
   */
  public void passTurn() {
    if (this.whoseTurn == Turn.BLACK) {
      this.whoseTurn = Turn.WHITE;
    } else {
      this.whoseTurn = Turn.BLACK;
    }

    consecPasses += 1;
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
   *                                  cell is already occupied or doesn't result in any opponent disc captures.
   */
  public void makeMove(Coordinate dest) {
    if (!grid.keySet().contains(new Coordinate(dest.getQ(), dest.getR()))) {
      throw new IllegalArgumentException("This space does not exist on the board");
    }

    if (grid.get(dest).getContent() != Disc.EMPTY) {
      throw new IllegalArgumentException("This space is already occupied");
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
      grid.put(new Coordinate(q, r), new Cell(this.currentColor()));
    }

    this.passTurn();
    consecPasses = 0;
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
    if (!(grid.keySet().contains(new Coordinate(q, r)))) {
      throw new IllegalArgumentException("This cell doesn't exist in the above grid ");
    }
    grid.get(new Coordinate(q, r)).setContent(disc);
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
    if (!(grid.keySet().contains(new Coordinate(q, r)))) {
      throw new IllegalArgumentException("This cell doesn't exist in the above grid ");
    }
    return grid.get(new Coordinate(q, r)).getContent();
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
    if (!(grid.keySet().contains(new Coordinate(q, r)))) {
      throw new IllegalArgumentException("This cell doesn't exist in the above grid ");
    }
    return grid.get(new Coordinate(q, r)).getContent() == Disc.EMPTY;
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
   * Retrieves the cell at the given coordinates.
   *
   * @param coord The coordinates of the cell.
   * @return The cell at the specified coordinates.
   */
  public Cell getGridCell(Coordinate coord) {
    return grid.get(coord);
  }

  /**
   * Checks if the game is over.
   *
   * @return True if the game is over, otherwise false.
   */
  public boolean isGameOver() {
    if (consecPasses == 2) {
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
      return true;
    }

    for (Disc playerDisc : Disc.values()) {
      if (playerDisc != Disc.EMPTY) { // Assuming Disc.EMPTY is the state for an empty cell
        for (Coordinate coord : grid.keySet()) {
          Board dupe = new Board(size);
          for (Coordinate coor1 : this.grid.keySet()) {
            dupe.grid.put(coor1, this.grid.get(coor1));
          }
          try {
            dupe.makeMove(coord);
            return false; // A valid move was found
          } catch (IllegalArgumentException e) {
            // Illegal move, try the next one
          }
        }
      }
    }
    return true; // No valid moves found
  }
}
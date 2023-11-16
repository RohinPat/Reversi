package model;

import java.util.HashMap;
import java.util.Map;

import view.BoardRenderer;

import java.util.ArrayList;

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

  public Board(int size, HashMap<Coordinate, Cell> grid, Turn whoseTuren) {
    this.consecPasses = 0;
    if (size <= 0) {
      throw new IllegalArgumentException("Size must be positive");
    }
    this.size = size;
    this.grid = new HashMap<>();
    for (Coordinate coord : grid.keySet()) {
      Cell originalCell = grid.get(coord);
      Cell newCell = new Cell(originalCell.getContent());
      this.grid.put(coord, newCell);
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
    this.gameState = GameState.PRE;
    playGame();
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
  }

  /**
   * Returns the color of the disc of the current player.
   *
   * @return The disc color of the current player.
   */
  private Disc currentColor() {
    if (gameState == GameState.INPROGRESS) {
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
    if (gameState == GameState.INPROGRESS) {
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

      this.passTurn();
      consecPasses = 0;
    } else {
      throw new IllegalStateException("The game has not been started yet no move can be made");
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
    if (gameState == GameState.INPROGRESS) {
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
    if (gameState != GameState.INPROGRESS) {
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
    Turn current = null;
    if (playerDisc.equals(Disc.BLACK)){
      current = Turn.BLACK;
    }
    else{
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


  /*
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

  /*
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
}
package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import controller.ControllerFeatures;

public abstract class AbstractModel implements Reversi {
  protected Map<Position, Cell> grid;
  protected int consecPasses;
  protected GameState gameState;
  protected Turn whoseTurn;
  protected int size;
  protected List<ControllerFeatures> observers;

  public AbstractModel(int size) {
    this.size = size;
    this.grid = new HashMap<>();
    this.consecPasses = 0;
    this.observers = new ArrayList<>();
    this.gameState = GameState.PRE;
    this.whoseTurn = Turn.BLACK;
  }


  public Map<Position, Cell> getMap() {
    return grid;
  }

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

  public void addObserver(ControllerFeatures controller) {
    observers.add(controller);
  }

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

  public int getSize() {
    return size;
  }

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

  public boolean isGameOver() {
    if (gameState == GameState.PRE) {
      throw new IllegalStateException("The game has not been started this cannot be checked");
    }
    // Check for consecutive passes
    if (consecPasses == 2) {
      whoWins();
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

  public int getScore(Disc player) {
    int scoreCounter = 0;
    for (Cell cell : grid.values()) {
      if (cell.getContent().equals(player)) {
        scoreCounter += 1;
      }
    }
    return scoreCounter;
  }

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
      throw new IllegalArgumentException(e);
    }
  }

  public HashMap<Position, Cell> createCopyOfBoard() {
    HashMap<Position, Cell> copy = new HashMap<Position, Cell>();
    for (Position coord : grid.keySet()) {
      Cell originalCell = grid.get(coord);
      Cell newCell = new Cell(originalCell.getContent());
      copy.put(coord, newCell);
    }
    return copy;
  }

  public GameState getState() {
    return this.gameState;
  }

  public boolean validMove(Position coor, Disc currentTurn) {
    boolean flag = true;
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
}


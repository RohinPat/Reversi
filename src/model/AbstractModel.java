package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import controller.ControllerFeatures;

public abstract class AbstractModel implements Reversi {
  protected Map<Coordinate, Cell> grid;
  protected int consecPasses;
  protected GameState gameState;
  protected Turn whoseTurn;
  protected int size;
  protected List<ControllerFeatures> observers;
  protected Reversi currentModel;

  public AbstractModel(int size) {
    this.size = size;
    this.grid = new HashMap<>();
    this.consecPasses = 0;
    this.observers = new ArrayList<>();
    this.gameState = GameState.PRE;
  }

  public void addObserver(ControllerFeatures controller) {
    observers.add(controller);
  }

  public Turn getTurn(){
    return whoseTurn;
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

  private Disc oppositeColor() {
    return (currentColor() == Disc.BLACK) ? Disc.WHITE : Disc.BLACK;
  }

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

  public Map<Coordinate, Cell> getMap() {
    return grid;
  }

  public boolean validMove(Coordinate coor, Disc currentTurn) {
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


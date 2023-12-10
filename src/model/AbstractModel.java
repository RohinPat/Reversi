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

  public Map<Coordinate, Cell> getMap() {
    return grid;
  }

}


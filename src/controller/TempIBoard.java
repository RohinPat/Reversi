package controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import provider.model.HexCoord;
import provider.model.HexDirection;
import provider.model.HexagonTile;
import provider.model.IBoard;

public class TempIBoard implements IBoard {
  private int width;
  private int height;
  private HashMap<HexCoord, HexagonTile> map;

  public TempIBoard(int width, int height, HashMap<HexCoord, HexagonTile> map){
    this.width = width;
    this.height = height;
    this.map = map;
  }

  @Override
  public Map<HexCoord, HexagonTile> getMap() {
    return this.map;
  }

  @Override
  public int getWidth() {
    return this.width;
  }

  @Override
  public int getHeight() {
    return this.height;
  }

  @Override
  public List<HexDirection> getDirections() {
    return null;
  }

  @Override
  public IBoard clone() {
    return null;
  }
}

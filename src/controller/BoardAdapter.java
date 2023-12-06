package controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.Cell;
import model.Coordinate;
import model.Disc;
import model.Reversi;
import provider.model.HexCoord;
import provider.model.HexDirection;
import provider.model.HexagonTile;
import provider.model.IBoard;
import provider.model.PlayerOwnership;

public class BoardAdapter implements IBoard {
  private final Reversi currentBoard;

  public BoardAdapter(Reversi currentBoard){
    this.currentBoard = currentBoard;
  }

  @Override
  public Map<HexCoord, HexagonTile> getMap() {
    Map<Coordinate, Cell> map = currentBoard.getMap();
    Map<HexCoord, HexagonTile> outputMap = new HashMap<>();


    for (Coordinate coord : map.keySet()){
      PlayerOwnership ownership = null;
      if (map.get(coord).equals(Disc.BLACK)){
        ownership = PlayerOwnership.PLAYER_1;
      } else if (map.get(coord).equals(Disc.WHITE)) {
        ownership = PlayerOwnership.PLAYER_2;
      }  else if (map.get(coord).equals(Disc.EMPTY)) {
        ownership = PlayerOwnership.UNOCCUPIED;
      }
      else{
        throw new IllegalArgumentException("Invalid disc");
      }
      outputMap.put(new HexCoord(coord.getQ(), coord.getR(), coord.getS()), new HexagonTile(ownership));
    }

    return outputMap;
  }

  @Override
  public int getWidth() {
    return (2 * currentBoard.getSize()) - 1;
  }

  @Override
  public int getHeight() {
    return (2 * currentBoard.getSize()) - 1;
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

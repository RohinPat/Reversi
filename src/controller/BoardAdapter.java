package controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.Board;
import model.Cell;
import model.Coordinate;
import model.Disc;
import model.Coordinate;
import model.Position;
import model.Reversi;
import model.Turn;
import provider.model.HexCoord;
import provider.model.HexDirection;
import provider.model.HexagonTile;
import provider.model.IBoard;
import provider.model.PlayerOwnership;


/**
 * The {@code BoardAdapter} class serves as an adapter that allows a {@link Reversi} board to be.
 * used where an {@link IBoard} is required. This class implements the {@link IBoard} interface and.
 * delegates calls to the underlying {@link Reversi} board. It acts as a bridge between the Reversi.
 * game model and other components or systems that interact with game boards through the.
 * {@link IBoard} interface.
 * This class ensures that the specific details and methods of the {@link Reversi} board are.
 * correctly translated and presented in a form compatible with the {@link IBoard} interface.
 * It also provides additional methods that are specific to the adapter itself, such as cloning the.
 * board for independent manipulation.
 */
public class BoardAdapter implements IBoard {
  private final Reversi currentBoard;

  /**
   * Constructs a new {@code BoardAdapter} which adapts a {@link Reversi} board to the.
   * {@link IBoard} interface. This allows for the use of Reversi board functionalities in contexts.
   * that require an {@link IBoard} instance.
   *
   * @param currentBoard The {@link Reversi} board instance that this adapter will represent.
   */
  public BoardAdapter(Reversi currentBoard) {
    this.currentBoard = currentBoard;
  }

  @Override
  public Map<HexCoord, HexagonTile> getMap() {
    Map<Position, Cell> map = currentBoard.getMap();
    Map<HexCoord, HexagonTile> outputMap = new HashMap<>();
    for (Position coord : map.keySet()) {
      PlayerOwnership ownership = null;
      if (map.get(coord).getContent().equals(Disc.BLACK)) {
        ownership = PlayerOwnership.PLAYER_1;
      } else if (map.get(coord).getContent().equals(Disc.WHITE)) {
        ownership = PlayerOwnership.PLAYER_2;
      } else if (map.get(coord).getContent().equals(Disc.EMPTY)) {
        ownership = PlayerOwnership.UNOCCUPIED;
      } else {
        throw new IllegalArgumentException("Invalid disc");
      }
      Coordinate tempCoor = (Coordinate)coord;
      outputMap.put(new HexCoord(coord.getFirstCoordinate(), coord.getSecondCoordinate(), tempCoor.getS()),
              new HexagonTile(ownership));
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
    HexDirection hd1 = HexDirection.UPLEFT;
    HexDirection hd2 = HexDirection.UPRIGHT;
    HexDirection hd3 = HexDirection.DOWNLEFT;
    HexDirection hd4 = HexDirection.DOWNRIGHT;
    HexDirection hd5 = HexDirection.LEFT;
    HexDirection hd6 = HexDirection.RIGHT;

    List<HexDirection> hexDirectionList = new ArrayList<>();

    hexDirectionList.add(hd1);
    hexDirectionList.add(hd2);
    hexDirectionList.add(hd3);
    hexDirectionList.add(hd4);
    hexDirectionList.add(hd5);
    hexDirectionList.add(hd6);

    return hexDirectionList;


  }

  @Override
  public IBoard clone() {
    // Create a deep copy of the Reversi board
    HashMap<Position, Cell> boardCopy = new HashMap<>();
    for (Position coor : currentBoard.getMap().keySet()) {
      boardCopy.put(new Coordinate(coor.getFirstCoordinate(), coor.getSecondCoordinate()),
              new Cell(currentBoard.getMap().get(coor).getContent()));
    }
    // Create a new Reversi instance with the copied board state
    Turn t = null;
    if (currentBoard.currentColor().equals(Disc.BLACK)) {
      t = Turn.BLACK;
    } else {
      t = Turn.WHITE;
    }
    Reversi clonedBoard = new Board(currentBoard.getSize(), boardCopy, t);
    return new BoardAdapter(clonedBoard);
  }
}

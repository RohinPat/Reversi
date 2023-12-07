package controller;

import java.util.HashMap;

import controller.BoardAdapter;
import model.Board;
import model.Cell;
import model.Coordinate;
import model.Disc;
import model.Reversi;
import model.Turn;
import provider.model.HexCoord;
import provider.model.IBoard;
import provider.model.PlayerOwnership;
import provider.model.ReversiMutableModel;
import provider.model.ReversiReadOnlyModel;

public class ReversiModelAdapter implements ReversiReadOnlyModel {

  private final Reversi currentModel;

  public ReversiModelAdapter(Reversi currentModel){
    this.currentModel = currentModel;
  }

  @Override
  public IBoard getBoard() throws IllegalStateException {
    return new BoardAdapter(currentModel);
  }

  @Override
  public boolean isGameOver() throws IllegalStateException {
    return currentModel.isGameOver();
  }

  @Override
  public int countClaimedTiles(PlayerOwnership player) throws IllegalStateException, IllegalArgumentException {
    Disc disc = null;
    if (player.equals(PlayerOwnership.PLAYER_1)){
      disc = Disc.WHITE;
    } else if (player.equals(PlayerOwnership.PLAYER_2)) {
      disc = Disc.BLACK;
    }  else if (player.equals(PlayerOwnership.UNOCCUPIED)) {
      disc = Disc.EMPTY;
    }
    else{
      throw new IllegalArgumentException("Invalid disc");
    }
    return currentModel.getScore(disc);
  }

  @Override
  public boolean isPlayerTurn(PlayerOwnership player) throws IllegalArgumentException, IllegalStateException {
    Disc disc = null;
    if (player.equals(PlayerOwnership.PLAYER_1)){
      disc = Disc.WHITE;
    } else if (player.equals(PlayerOwnership.PLAYER_2)) {
      disc = Disc.BLACK;
    }  else if (player.equals(PlayerOwnership.UNOCCUPIED)) {
      disc = Disc.EMPTY;
    }
    else{
      throw new IllegalArgumentException("Invalid disc");
    }

    if (disc.equals(currentModel.currentColor())){
      return true;
    }
    else{
      return false;
    }

  }

  @Override
  public boolean isMoveAllowed(HexCoord hc, PlayerOwnership player) throws IllegalArgumentException, IllegalStateException {
    Disc disc = null;
    if (player.equals(PlayerOwnership.PLAYER_1)){
      disc = Disc.WHITE;
    } else if (player.equals(PlayerOwnership.PLAYER_2)) {
      disc = Disc.BLACK;
    }  else if (player.equals(PlayerOwnership.UNOCCUPIED)) {
      disc = Disc.EMPTY;
    }
    else{
      throw new IllegalArgumentException("Invalid disc");
    }
    return currentModel.validMove(new Coordinate(hc.q, hc.r), disc);
  }

  @Override
  public ReversiMutableModel cloneModel() {
    // Create a deep copy of the Reversi board
    HashMap<Coordinate, Cell> boardCopy = new HashMap<>();
    for (Coordinate coor : currentModel.getMap().keySet()) {
      boardCopy.put(new Coordinate(coor.getQ(), coor.getR()), new Cell(currentModel.getMap().get(coor).getContent()));
    }
    // Create a new Reversi instance with the copied board state

    Turn t = null;
    if (currentModel.currentColor().equals(Disc.BLACK)){
      t = Turn.BLACK;
    } else {
      t = Turn.WHITE;
    }


    Reversi clonedBoard = new Board(currentModel.getSize(), boardCopy, t);
    return new BoardAdapter2(clonedBoard);
  }

  @Override
  public boolean isMoveAllowedTurnIndependent(HexCoord hc, PlayerOwnership playerOwnership) throws IllegalArgumentException, IllegalStateException {
    throw new UnsupportedOperationException("This is unsupported");
  }
}

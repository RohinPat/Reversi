package controller;

import controller.BoardAdapter;
import model.Coordinate;
import model.Disc;
import model.Reversi;
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
      disc = Disc.BLACK;
    } else if (player.equals(PlayerOwnership.PLAYER_2)) {
      disc = Disc.WHITE;
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
      disc = Disc.BLACK;
    } else if (player.equals(PlayerOwnership.PLAYER_2)) {
      disc = Disc.WHITE;
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
      disc = Disc.BLACK;
    } else if (player.equals(PlayerOwnership.PLAYER_2)) {
      disc = Disc.WHITE;
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
    throw new UnsupportedOperationException("This is unsupported");
  }

  @Override
  public boolean isMoveAllowedTurnIndependent(HexCoord hc, PlayerOwnership playerOwnership) throws IllegalArgumentException, IllegalStateException {
    throw new UnsupportedOperationException("This is unsupported");
  }
}

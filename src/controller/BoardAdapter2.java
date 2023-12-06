package controller;

import java.util.Map;

import model.Coordinate;
import model.Disc;
import model.Reversi;
import provider.controller.ModelTurnListener;
import provider.model.HexCoord;
import provider.model.IBoard;
import provider.model.PlayerOwnership;
import provider.model.ReversiMutableModel;

public class BoardAdapter2 implements ReversiMutableModel {
  private final Reversi board;

  public BoardAdapter2 (Reversi board){
    this.board = board;
  }


  @Override
  public void startGame(IBoard board) throws IllegalStateException, IllegalArgumentException {
    // get a map of the stuff in their board
    // cycle through their map to create our map
    // use our map board constructor to create a reversi of our own
    // play game on that
  }

  @Override
  public void placeDisk(HexCoord hc, PlayerOwnership player) throws IllegalStateException, IllegalArgumentException {
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

    board.makeMove(new Coordinate(hc.q, hc.r));
  }

  @Override
  public void pass(PlayerOwnership player) throws IllegalStateException, IllegalArgumentException {
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
    if (disc.equals(board.currentColor())){
      board.passTurn();
    }
    else{
      throw new IllegalStateException("Error 2");
    }
  }

  @Override
  public void addTurnListener(ModelTurnListener listener) {
    throw new UnsupportedOperationException("Not good");
  }

  @Override
  public IBoard getBoard() throws IllegalStateException {
    return new BoardAdapter(board);
  }

  @Override
  public boolean isGameOver() throws IllegalStateException {
    return board.isGameOver();
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
    return board.getScore(disc);
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

    if (disc.equals(board.currentColor())){
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
    return board.validMove(new Coordinate(hc.q, hc.r), disc);
  }

  @Override
  public ReversiMutableModel cloneModel() {
    return new BoardAdapter2(board);
  }

  @Override
  public boolean isMoveAllowedTurnIndependent(HexCoord hc, PlayerOwnership playerOwnership) throws IllegalArgumentException, IllegalStateException {
    throw new UnsupportedOperationException("Not good 3233232");
  }
}

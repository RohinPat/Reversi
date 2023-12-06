package controller;

import java.util.HashMap;
import java.util.Map;

import model.Board;
import model.Cell;
import model.Coordinate;
import model.Disc;
import model.Reversi;
import provider.controller.ModelTurnListener;
import provider.model.HexCoord;
import provider.model.IBoard;
import provider.model.PlayerOwnership;
import provider.model.ReversiMutableModel;
import model.Turn;

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
  public void placeDisk(HexCoord hc, PlayerOwnership player){
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
    // Create a deep copy of the Reversi board
    HashMap<Coordinate, Cell> boardCopy = new HashMap<>();
    for (Coordinate coor : board.getMap().keySet()) {
      boardCopy.put(new Coordinate(coor.getQ(), coor.getR()), new Cell(board.getMap().get(coor).getContent()));
    }
    // Create a new Reversi instance with the copied board state

    Turn t = null;
    if (board.currentColor().equals(Disc.BLACK)){
      t = Turn.BLACK;
    } else {
      t = Turn.WHITE;
    }


    Reversi clonedBoard = new Board(board.getSize(), boardCopy, t);
    return new BoardAdapter2(clonedBoard);
  }

  @Override
  public boolean isMoveAllowedTurnIndependent(HexCoord hc, PlayerOwnership playerOwnership) throws IllegalArgumentException, IllegalStateException {
    Disc disc = null;
    if (playerOwnership.equals(PlayerOwnership.PLAYER_1)){
      disc = Disc.BLACK;
    } else if (playerOwnership.equals(PlayerOwnership.PLAYER_2)) {
      disc = Disc.WHITE;
    }  else if (playerOwnership.equals(PlayerOwnership.UNOCCUPIED)) {
      disc = Disc.EMPTY;
    }
    else{
      throw new IllegalArgumentException("Invalid disc");
    }
    return board.validMove(new Coordinate(hc.q, hc.r), disc);
  }
}

package controller;

import java.util.HashMap;

import model.Board;
import model.Cell;
import model.Coordinate;
import model.Disc;
import model.Coordinate;
import model.Reversi;
import provider.controller.ModelTurnListener;
import provider.model.HexCoord;
import provider.model.IBoard;
import provider.model.PlayerOwnership;
import provider.model.ReversiMutableModel;
import model.Turn;

/**
 * The {@code BoardAdapter2} class is an adapter that conforms a {@link Reversi} board to the.
 * {@link ReversiMutableModel} interface, allowing the Reversi game logic to be used in contexts.
 * requiring this interface. This adapter enables the integration of the Reversi game model with.
 * different systems or frameworks that operate through the {@link ReversiMutableModel} interface.
 * It acts as a bridge between the Reversi model and these systems, translating the calls and data.
 * between them.
 * This class implements all the methods of the {@link ReversiMutableModel} interface, delegating.
 * most of its functionality to an underlying {@link Reversi} board instance. It is responsible for.
 * converting the data and calls between the {@link Reversi} model and the interface methods.
 * ensuring compatibility and correct behavior.
 */
public class BoardAdapter2 implements ReversiMutableModel {
  private final Reversi board;

  /**
   * Constructs a new {@code BoardAdapter2} which adapts a {@link Reversi} board to conform to the.
   * {@link ReversiMutableModel} interface. This adapter allows the provided Reversi board to be.
   * used in any context that requires a {@link ReversiMutableModel}, facilitating interoperability.
   * with systems built around this interface.
   *
   * @param board The {@link Reversi} board that this adapter will represent and adapt to the.
   *     {@link ReversiMutableModel} interface.
   */
  public BoardAdapter2(Reversi board) {
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
  public void placeDisk(HexCoord hc, PlayerOwnership player) {
    Disc disc = null;
    if (player.equals(PlayerOwnership.PLAYER_1)) {
      disc = Disc.BLACK;
    } else if (player.equals(PlayerOwnership.PLAYER_2)) {
      disc = Disc.WHITE;
    } else if (player.equals(PlayerOwnership.UNOCCUPIED)) {
      disc = Disc.EMPTY;
    } else {
      throw new IllegalArgumentException("Invalid disc");
    }

    board.makeMove(new Coordinate(hc.q, hc.r));
  }

  @Override
  public void pass(PlayerOwnership player) throws IllegalStateException, IllegalArgumentException {
    Disc disc = null;
    if (player.equals(PlayerOwnership.PLAYER_1)) {
      disc = Disc.BLACK;
    } else if (player.equals(PlayerOwnership.PLAYER_2)) {
      disc = Disc.WHITE;
    } else if (player.equals(PlayerOwnership.UNOCCUPIED)) {
      disc = Disc.EMPTY;
    } else {
      throw new IllegalArgumentException("Invalid disc");
    }
    if (disc.equals(board.currentColor())) {
      board.passTurn();
    } else {
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
  public int countClaimedTiles(PlayerOwnership player)
          throws IllegalStateException, IllegalArgumentException {
    Disc disc = null;
    if (player.equals(PlayerOwnership.PLAYER_1)) {
      disc = Disc.BLACK;
    } else if (player.equals(PlayerOwnership.PLAYER_2)) {
      disc = Disc.WHITE;
    } else if (player.equals(PlayerOwnership.UNOCCUPIED)) {
      disc = Disc.EMPTY;
    } else {
      throw new IllegalArgumentException("Invalid disc");
    }
    return board.getScore(disc);
  }

  @Override
  public boolean isPlayerTurn(PlayerOwnership player)
          throws IllegalArgumentException, IllegalStateException {
    Disc disc = null;
    if (player.equals(PlayerOwnership.PLAYER_1)) {
      disc = Disc.BLACK;
    } else if (player.equals(PlayerOwnership.PLAYER_2)) {
      disc = Disc.WHITE;
    } else if (player.equals(PlayerOwnership.UNOCCUPIED)) {
      disc = Disc.EMPTY;
    } else {
      throw new IllegalArgumentException("Invalid disc");
    }

    return disc.equals(board.currentColor());
  }

  @Override
  public boolean isMoveAllowed(HexCoord hc, PlayerOwnership player)
          throws IllegalArgumentException, IllegalStateException {
    Disc disc = null;
    if (player.equals(PlayerOwnership.PLAYER_1)) {
      disc = Disc.BLACK;
    } else if (player.equals(PlayerOwnership.PLAYER_2)) {
      disc = Disc.WHITE;
    } else if (player.equals(PlayerOwnership.UNOCCUPIED)) {
      disc = Disc.EMPTY;
    } else {
      throw new IllegalArgumentException("Invalid disc");
    }
    return board.validMove(new Coordinate(hc.q, hc.r), disc);
  }

  @Override
  public ReversiMutableModel cloneModel() {
    // Create a deep copy of the Reversi board
    HashMap<Coordinate, Cell> boardCopy = new HashMap<>();
    for (Coordinate coor : board.getMap().keySet()) {
      boardCopy.put(new Coordinate(coor.getFirstCoordinate(), coor.getSecondCoordinate()),
              new Cell(board.getMap().get(coor).getContent()));
    }
    // Create a new Reversi instance with the copied board state

    Turn t = null;
    if (board.currentColor().equals(Disc.BLACK)) {
      t = Turn.BLACK;
    } else {
      t = Turn.WHITE;
    }


    Reversi clonedBoard = new Board(board.getSize(), boardCopy, t);
    return new BoardAdapter2(clonedBoard);
  }

  @Override
  public boolean isMoveAllowedTurnIndependent(HexCoord hc, PlayerOwnership playerOwnership)
          throws IllegalArgumentException, IllegalStateException {
    Disc disc = null;
    if (playerOwnership.equals(PlayerOwnership.PLAYER_1)) {
      disc = Disc.BLACK;
    } else if (playerOwnership.equals(PlayerOwnership.PLAYER_2)) {
      disc = Disc.WHITE;
    } else if (playerOwnership.equals(PlayerOwnership.UNOCCUPIED)) {
      disc = Disc.EMPTY;
    } else {
      throw new IllegalArgumentException("Invalid disc");
    }
    return board.validMove(new Coordinate(hc.q, hc.r), disc);
  }
}

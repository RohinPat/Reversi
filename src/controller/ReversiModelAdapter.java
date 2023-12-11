package controller;

import java.util.HashMap;

import model.Board;
import model.Cell;
import model.Coordinate;
import model.Disc;
import model.Coordinate;
import model.Position;
import model.Reversi;
import model.Turn;
import provider.model.HexCoord;
import provider.model.IBoard;
import provider.model.PlayerOwnership;
import provider.model.ReversiMutableModel;
import provider.model.ReversiReadOnlyModel;

/**
 * Adapter class that enables interaction between the Reversi game model and a provider's view.
 * model.
 * It serves as a bridge to convert and relay information from the Reversi game model to a format
 * compatible with the provider's view requirements, specifically for read-only operations.
 */
public class ReversiModelAdapter implements ReversiReadOnlyModel {

  private final Reversi currentModel;

  /**
   * Constructs a ReversiModelAdapter with a given Reversi model.
   * This adapter facilitates access to game state information such as board configuration,
   * game over status, and player turns, translating it to the provider's model format.
   * @param currentModel The Reversi game model to be adapted for the provider's view.
   */
  public ReversiModelAdapter(Reversi currentModel) {
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
  public int countClaimedTiles(PlayerOwnership player)
          throws IllegalStateException, IllegalArgumentException {
    Disc disc = null;
    if (player.equals(PlayerOwnership.PLAYER_1)) {
      disc = Disc.WHITE;
    } else if (player.equals(PlayerOwnership.PLAYER_2)) {
      disc = Disc.BLACK;
    } else if (player.equals(PlayerOwnership.UNOCCUPIED)) {
      disc = Disc.EMPTY;
    } else {
      throw new IllegalArgumentException("Invalid disc");
    }
    return currentModel.getScore(disc);
  }

  @Override
  public boolean isPlayerTurn(PlayerOwnership player)
          throws IllegalArgumentException, IllegalStateException {
    Disc disc = null;
    if (player.equals(PlayerOwnership.PLAYER_1)) {
      disc = Disc.WHITE;
    } else if (player.equals(PlayerOwnership.PLAYER_2)) {
      disc = Disc.BLACK;
    } else if (player.equals(PlayerOwnership.UNOCCUPIED)) {
      disc = Disc.EMPTY;
    } else {
      throw new IllegalArgumentException("Invalid disc");
    }

    return (disc.equals(currentModel.currentColor()));

  }

  @Override
  public boolean isMoveAllowed(HexCoord hc, PlayerOwnership player)
          throws IllegalArgumentException, IllegalStateException {
    Disc disc = null;
    if (player.equals(PlayerOwnership.PLAYER_1)) {
      disc = Disc.WHITE;
    } else if (player.equals(PlayerOwnership.PLAYER_2)) {
      disc = Disc.BLACK;
    } else if (player.equals(PlayerOwnership.UNOCCUPIED)) {
      disc = Disc.EMPTY;
    } else {
      throw new IllegalArgumentException("Invalid disc");
    }
    return currentModel.validMove(new Coordinate(hc.q, hc.r), disc);
  }

  @Override
  public ReversiMutableModel cloneModel() {
    // Create a deep copy of the Reversi board
    HashMap<Position, Cell> boardCopy = new HashMap<>();
    for (Position coor : currentModel.getMap().keySet()) {
      boardCopy.put(new Coordinate(coor.getFirstCoordinate(), coor.getSecondCoordinate()),
              new Cell(currentModel.getMap().get(coor).getContent()));
    }
    // Create a new Reversi instance with the copied board state

    Turn t = null;
    if (currentModel.currentColor().equals(Disc.BLACK)) {
      t = Turn.BLACK;
    } else {
      t = Turn.WHITE;
    }


    Reversi clonedBoard = new Board(currentModel.getSize(), boardCopy, t);
    return new BoardAdapter2(clonedBoard);
  }

  @Override
  public boolean isMoveAllowedTurnIndependent(HexCoord hc, PlayerOwnership playerOwnership)
          throws IllegalArgumentException, IllegalStateException {
    throw new UnsupportedOperationException("This is unsupported");
  }
}

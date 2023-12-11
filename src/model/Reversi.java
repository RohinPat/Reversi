package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import controller.ControllerFeatures;

/**
 * Represents the game board for a hexagonal grid-based game.
 */
public interface Reversi extends ReversiReadOnly {

  /**
   * Returns current color.
   */
  Disc currentColor();

  /**
   * Passes the turn to the next player.
   */
  void passTurn();

  /**
   * Attempts to make a move on the board by placing the current
   * player's disc at the specified Position.
   * The method validates the move, flips any captured opponent discs,
   * and switches the turn to the next player.
   *
   * @param dest The target Position where the current player's disc should be placed.
   * @throws IllegalArgumentException If the move is invalid, such as when the target
   *                                  cell is already occupied or doesn't result in any opponent
   *                                  disc captures.
   */
  void makeMove(Position dest);

  /**
   * Places a disc at the specified cell Positions.
   *
   * @param q The q-Position of the cell.
   * @param r The r-Position of the cell.
   * @param d The disc to be placed.
   * @throws IllegalArgumentException If the cell doesn't exist in the grid.
   */
  void placeDisc(int q, int r, Disc d);

  /**
   * Retrieves the disc at the specified cell Positions.
   *
   * @param q The q-Position of the cell.
   * @param r The r-Position of the cell.
   * @return The disc present at the specified Positions.
   * @throws IllegalArgumentException If the cell doesn't exist in the grid.
   */
  Disc getDiscAt(int q, int r);

  /**
   * Checks if the cell at the specified Positions is empty.
   *
   * @param q The q-Position of the cell.
   * @param r The r-Position of the cell.
   * @return True if the cell is empty, otherwise false.
   * @throws IllegalArgumentException If the cell doesn't exist in the grid.
   */
  boolean isCellEmpty(int q, int r);

  /**
   * Retrieves the size of the game board.
   *
   * @return The size of the board.
   */
  int getSize();

  /**
   * Checks if the game is over.
   *
   * @return True if the game is over, otherwise false.
   */
  boolean isGameOver();

  /**
   * Creates a deep copy of the current game board.
   *
   * @return A {@link HashMap} representing a copy of the board. Each {@link Position}.
   *     key is mapped to a {@link Cell} value.
   */
  HashMap<Position, Cell> createCopyOfBoard();

  /**
   * Calculates and returns a list of all possible moves for the current player.
   *
   * @return An {@link ArrayList} of {@link Position} objects representing all possible moves.
   *     that the current player can make.
   */
  ArrayList<Position> getPossibleMoves();

  /**
   * Checks if a given move is valid on the current game board.
   * This method simulates a move on a copy of the game board and evaluates if it's legally.
   * possible.
   *
   * @param model The read-only view of the game board to check the move against.
   * @param move  The Position of the move to be evaluated.
   * @return The score resulting from the move, if valid.
   * @throws IllegalArgumentException If the move is invalid.
   */
  int checkMove(ReversiReadOnly model, Position move);

  /**
   * Determines if a move is valid for the given player at the specified Positions.
   * It evaluates the legality of the move without actually making the move on the board.
   *
   * @param coor        The Positions where the move is being considered.
   * @param currentTurn The current turn's disc to evaluate the move for.
   * @return True if the move is valid, false otherwise.
   */
  boolean validMove(Position coor, Disc currentTurn);

  /**
   * Retrieves a map representation of the current game board.
   * Each Position in the grid is associated with a cell, which may contain a disc or be empty.
   *
   * @return A map where each key is a {@link Position} and each value is the.
   *     corresponding {@link Cell}.
   */
  Map<Position, Cell> getMap();

  /**
   * Adds an observer, typically a controller, to be notified of changes in the game state.
   * This allows the observer to update its view or state in response to game changes.
   *
   * @param controller The observer to be added, which implements the.
   *     {@link ControllerFeatures} interface.
   */
  void addObserver(ControllerFeatures controller);

  void notifyTurnChange();

  int getScoreForPlayer(ReversiReadOnly model, Position move, Disc player);
}

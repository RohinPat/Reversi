package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * An interface to create a version of Board in which you can only call observer.
 * Methods upon it to prevent any mutation.
 */
public interface ReversiReadOnly {

  /**
   * Retrieves the disc at the specified cell coordinates.
   *
   * @param q The q-coordinate of the cell.
   * @param r The r-coordinate of the cell.
   * @return The disc present at the specified coordinates.
   * @throws IllegalArgumentException If the cell doesn't exist in the grid.
   */
  model.Disc getDiscAt(int q, int r);

  /**
   * Checks if the cell at the specified coordinates is empty.
   *
   * @param q The q-coordinate of the cell.
   * @param r The r-coordinate of the cell.
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
   * Returns the current score of a specified player.
   *
   * @return True if the game is over, otherwise false.
   */
  int getScore(Disc player);

  /**
   * Generates a list of all possible moves for the current player.
   *
   * @return An ArrayList of Coordinates representing all possible moves.
   */
  ArrayList<Coordinate> getPossibleMoves();

  /**
   * Checks the validity and potential score of a specific move on a read-only board model.
   *
   * @param model The read-only model of the board.
   * @param move  The move to check.
   * @return The score resulting from the move if it's valid.
   */
  int checkMove(ReversiReadOnly model, Coordinate move);

  /**
   * Retrieves the disc color of the player whose turn is currently active.
   *
   * @return The disc color of the current player.
   */
  Disc currentColor();

  /**
   * Creates a deep copy of the board's current state, maintaining a read-only perspective.
   *
   * @return A HashMap representing a copy of the board's current state.
   */
  HashMap<Coordinate, Cell> createCopyOfBoard();

  /**
   * Validates a potential move on the board from a read-only perspective.
   *
   * @param coor        The coordinates of the move to validate.
   * @param currentTurn The disc color of the player making the move.
   * @return True if the move is valid, false otherwise.
   */
  boolean validMove(Coordinate coor, Disc currentTurn);

  /**
   * Retrieves a map representation of the current game board in a read-only manner.
   *
   * @return A Map where each key is a Coordinate and each value is the corresponding Cell.
   */
  Map<Coordinate, Cell> getMap();

  /**
   * Returns the current state of the game, such as in-progress, ended, etc.
   *
   * @return The current state of the game.
   */
  GameState getState();
}

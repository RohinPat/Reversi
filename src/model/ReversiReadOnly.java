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
   * Retrieves the disc at the specified cell Positions.
   *
   * @param q The first coordinate of the Position of the cell.
   * @param r The second coordinate of the Position of the cell.
   * @return The disc present at the specified Positions.
   * @throws IllegalArgumentException If the cell doesn't exist in the grid.
   */
  model.Disc getDiscAt(int q, int r);

  /**
   * Checks if the cell at the specified Positions is empty.
   *
   * @param q The first coordinate of the Position of the cell.
   * @param r The second coordinate of the Position of the cell.
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
   * @return An ArrayList of Positions representing all possible moves.
   */
  ArrayList<Position> getPossibleMoves();

  /**
   * Checks the validity and potential score of a specific move on a read-only board model.
   *
   * @param model The read-only model of the board.
   * @param move  The move to check.
   * @return The score resulting from the move if it's valid.
   */
  int checkMove(ReversiReadOnly model, Position move);

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
  HashMap<Position, Cell> createCopyOfBoard();

  /**
   * Validates a potential move on the board from a read-only perspective.
   *
   * @param coor        The Positions of the move to validate.
   * @param currentTurn The disc color of the player making the move.
   * @return True if the move is valid, false otherwise.
   */
  boolean validMove(Position coor, Disc currentTurn);

  /**
   * Retrieves a map representation of the current game board in a read-only manner.
   *
   * @return A Map where each key is a Position and each value is the corresponding Cell.
   */
  Map<Position, Cell> getMap();

  /**
   * Returns the current state of the game, such as in-progress, ended, etc.
   *
   * @return The current state of the game.
   */
  GameState getState();

  /**
   * Calculates the score change for a player after making a move on a Reversi game model from.
   * an observer perspective only - doesn't modify the board.
   *
   * @param model  The Reversi game model representing the current state of the game.
   * @param move   The position where the player intends to make a move.
   * @param player The player for whom the score change is calculated (Disc.BLACK or Disc.WHITE).
   * @return The change in score for the specified player after making the move, or 0 if the move.
   * is invalid.
   */
  int getScoreForPlayer(ReversiReadOnly model, Position move, Disc player);
}

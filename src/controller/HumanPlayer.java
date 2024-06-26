package controller;

import model.Position;
import model.Reversi;
import model.Disc;
import model.Coordinate;

/**
 * The {@code HumanPlayer} class represents a human player in the Reversi game.
 * It implements the {@link Player} interface and provides methods for making moves
 * and checking if it's the player's turn.
 */
public class HumanPlayer implements Player {
  private final Disc playerDisc; // The disc type this player controls (BLACK or WHITE)

  /**
   * Constructs a {@code HumanPlayer} with the specified player disc color.
   *
   * @param playerDisc The {@link Disc} representing the color of the player's pieces
   *                   (BLACK or WHITE).
   */
  public HumanPlayer(Disc playerDisc) {
    this.playerDisc = playerDisc;
  }

  /**
   * Method which checks if it is the turn of the player assigned to this class.
   *
   * @param model The {@link Reversi} game model representing the current game state.
   * @return true if it is this player's turn and false if it isn't.
   */
  @Override
  public boolean isPlayerTurn(Reversi model) {
    return model.currentColor() == this.playerDisc;
  }


  @Override
  public void makeAMove(Reversi model, Position move) {
    if (isPlayerTurn(model)) {
      model.makeMove(move);
    }
  }

  @Override
  public Disc getDisc() {
    return playerDisc;
  }

  @Override
  public void passTurn(Reversi model) {
    // unused but needs to be overwritten
  }
}

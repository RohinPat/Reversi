package controller;

import model.Reversi;
import model.Disc;
import model.Coordinate;

public class HumanPlayer implements Player{
  private Disc playerDisc; // The disc type this player controls (BLACK or WHITE)

  public HumanPlayer(Disc playerDisc) {
    this.playerDisc = playerDisc;
  }

  // Method to check if it's this player's turn
  public boolean isPlayerTurn(Reversi model) {
    return model.currentColor() == this.playerDisc;
  }

  // Method to initiate a move
  public void makeAMove(Reversi model, Coordinate move) {
    if (isPlayerTurn(model)) {
      model.makeMove(move);
    }
  }

  @Override
  public void passTurn() {
    //
  }

  // Getters, setters, and other relevant methods...
}

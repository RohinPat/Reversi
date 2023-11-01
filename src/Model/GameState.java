package model;

/**
 * An enumerated type that keeps track of the state of the game.
 * Also used to ensure certain moves can only be called during the game and not outside.
 */
public enum GameState {
  PRE, INPROGRESS, WHITEWIN, BLACKWIN, TIE
}

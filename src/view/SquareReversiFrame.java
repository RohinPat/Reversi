package view;

import javax.swing.JFrame;

import model.ReversiReadOnly;

/**
 * The {@code SquareReversiFrame} class extends {@link JFrame} and is used to create the main window
 * for a Reversi game with a square board. It contains a {@link SquareBoardPanel} that displays the game board.
 */
public class SquareReversiFrame extends JFrame implements IFrame{
  SquareBoardPanel bp;

  /**
   * Constructs a new {@code SquareReversiFrame} with the specified ReversiReadOnly board.
   *
   * @param board The ReversiReadOnly board representing the current state of the game.
   */
  public SquareReversiFrame(ReversiReadOnly board) {
      bp = (new SquareBoardPanel(board, 600, 600));
      this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      this.add(bp);
      this.pack();
      this.setLocationRelativeTo(null); // Center the frame on the screen
  }

  /**
   * Retrieves the {@link SquareBoardPanel} associated with this {@code SquareReversiFrame}.
   *
   * @return The {@link SquareBoardPanel} object that displays the Reversi game board
   *     within this frame. This panel allows access to the current state
   *     of the game, including the positions of all pieces on the board.
   */
  public IBoardPanel getBoardPanel() {
    return bp;
  }

  /**
   * Makes the frame visible or invisible based on the specified boolean value.
   *
   * @param isVisible {@code true} to make the frame visible, {@code false} to make it invisible.
   */
  public void makeVisible(boolean isVisible){
    setVisible(true);
  }
}

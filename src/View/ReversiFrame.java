package view;

import javax.swing.JFrame;

import model.ReversiReadOnly;

/**
 * The {@code ReversiFrame} class extends {@link JFrame} and is used to create
 * the main window for a Reversi game. It contains a {@link BoardPanel} that
 * displays the game board.
 */
public class ReversiFrame extends JFrame {
  private final BoardPanel boardPanel;

  /**
   * Constructs a {@code ReversiFrame} with the specified {@link ReversiReadOnly} board.
   * Initializes the game board and sets up the frame properties including size,
   * close operation, and default layout.
   *
   * @param board The {@link ReversiReadOnly} board to be displayed inside this frame.
   *              This object represents the current state of the Reversi game,
   *              including the positions of all pieces on the board.
   */
  public ReversiFrame(ReversiReadOnly board) {
    boardPanel = new BoardPanel(board, 600, 600);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.add(boardPanel);
    this.pack();
    this.setLocationRelativeTo(null); // Center the frame on the screen
  }
}

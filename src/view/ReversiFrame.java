package view;

import javax.swing.JFrame;

import model.ReversiReadOnly;

/**
 * The {@code ReversiFrame} class extends {@link JFrame} and is used to create
 * the main window for a Reversi game. It contains a {@link BoardPanel} that
 * displays the game board.
 */
public class ReversiFrame extends JFrame implements IFrame{

  BoardPanel bp;

  /**
   * Constructs a new {@code ReversiFrame} with a game board panel.
   *
   * @param board The ReversiReadOnly object representing the game board.
   * @param hint  A boolean indicating whether to enable hints in the game.
   *              If true, a HintDecorator is applied to the board panel to display hints.
   */
  public ReversiFrame(ReversiReadOnly board, boolean hint) {
    if (hint) {
      bp = new HintDecorator(new BoardPanel(board, 600, 600));
    }
    else{
      bp = new BoardPanel(board, 600, 600);
    }

    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.add(bp);
    this.pack();
    this.setLocationRelativeTo(null); // Center the frame on the screen
  }

  /**
   * Constructs a new {@code ReversiFrame} with a provided board panel.
   *
   * @param panel The BoardPanel to be displayed within the frame.
   */
  public ReversiFrame(BoardPanel panel) {
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.add(panel);
    this.pack();
    this.setLocationRelativeTo(null); // Center the frame on the screen
  }

  /**
   * Retrieves the {@link BoardPanel} associated with this {@code ReversiFrame}.
   *
   * @return The {@link BoardPanel} object that displays the Reversi game board
   *     within this frame. This panel allows access to the current state
   *     of the game, including the positions of all pieces on the board.
   */
  public IBoardPanel getBoardPanel() {
    return bp;
  }

  /**
   * Sets the visibility of the frame.
   *
   * @param isVisible True to make the frame visible, false to hide it.
   */
  public void makeVisible(boolean isVisible){
    setVisible(true);
  }
}

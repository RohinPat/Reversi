package view;

import javax.swing.JFrame;
import model.Board;

public class ReversiFrame extends JFrame {
  private BoardPanel boardPanel;

  public ReversiFrame(Board board) {
    boardPanel = new BoardPanel(board);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.add(boardPanel);
    this.pack();
    this.setLocationRelativeTo(null); // Center the frame on the screen
  }
}

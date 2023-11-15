package view;

import javax.swing.JFrame;

import model.ReversiReadOnly;

public class ReversiFrame extends JFrame {
  private final BoardPanel boardPanel;

  public ReversiFrame(ReversiReadOnly board) {
    boardPanel = new BoardPanel(board, 600, 600);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.add(boardPanel);
    this.pack();
    this.setLocationRelativeTo(null); // Center the frame on the screen
  }
}

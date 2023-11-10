package view;

import javax.swing.JFrame;

public class ReversiFrame extends JFrame {
  private BoardPanel boardPanel;

  public ReversiFrame(int size) {
    boardPanel = new BoardPanel(size);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.add(boardPanel);
    this.pack();
    this.setLocationRelativeTo(null); // Center the frame on the screen
  }
}

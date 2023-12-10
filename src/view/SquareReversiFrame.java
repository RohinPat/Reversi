package view;

import javax.swing.*;

import model.ReversiReadOnly;
import model.SquareBoard;

public class SquareReversiFrame extends JFrame implements IFrame{
  SquareBoardPanel bp;

  public SquareReversiFrame(ReversiReadOnly board) {
      bp = (new SquareBoardPanel(board, 600, 600));
      this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      this.add(bp);
      this.pack();
      this.setLocationRelativeTo(null); // Center the frame on the screen
  }

  public IBoardPanel getBoardPanel() {
    return bp;
  }

  public void makeVisible(boolean bool){
    setVisible(true);
  }
}

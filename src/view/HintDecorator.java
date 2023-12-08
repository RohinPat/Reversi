package view;

import java.awt.*;

import model.Coordinate;
import model.ReversiReadOnly;

public class HintDecorator {
  private BoardPanel boardPanel;

  public HintDecorator(BoardPanel boardPanel) {
    this.boardPanel = boardPanel;
  }

  public void drawHints(Graphics2D g2d, Hexagon selected, int q, int r, ReversiReadOnly board) {
    if (selected != null) {
      Coordinate dest = new Coordinate(q, r);
      int change = board.checkMove(board, dest);

      g2d.setColor(Color.YELLOW); // Choose an appropriate color
      g2d.fillOval((int)selected.x - 10, (int)selected.y - 10, 20, 20); // Draw a small circle
      g2d.setColor(Color.BLACK); // Color for text
      g2d.drawString(String.valueOf(change), (int)selected.x, (int)selected.y);
    }
  }




}

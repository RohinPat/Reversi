package view;

import java.awt.*;

import model.Coordinate;
import model.ReversiReadOnly;

public class HintDecorator {
  private BoardPanel boardPanel;

  public HintDecorator(BoardPanel boardPanel) {
    this.boardPanel = boardPanel;
  }

  public void drawHints(Graphics2D g2d, Hexagon selected, int q, int r, ReversiReadOnly board, int bwidth, int bheight) {
    if (selected != null) {
      Coordinate dest = new Coordinate(q, r);
      int change = board.checkMove(board, dest);

      int boardSize = board.getSize();

      double maxHexWidth = (double) bwidth / (boardSize * 2);

      double maxHexHeight = (double) bheight / ((boardSize) * 2);

      int fontSize = (int)Math.min(maxHexWidth / 2, maxHexHeight / 2);


      g2d.setFont(new Font("Default", Font.BOLD, fontSize)); // Set the font to the dynamically calculated size

      g2d.setColor(Color.BLACK); // Color for text
      g2d.drawString(String.valueOf(change), (int)selected.x, (int)selected.y);
    }
  }




}

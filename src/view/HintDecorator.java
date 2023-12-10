package view;

import java.awt.*;
import java.awt.event.ComponentEvent;

import javax.naming.ldap.Control;

import controller.ControllerFeatures;
import model.Coordinate;
import model.Disc;
import model.ReversiReadOnly;

public class HintDecorator extends BoardPanel {
  private BoardPanel boardPanel;

  public HintDecorator(BoardPanel boardPanel) {
    super(boardPanel.getReadReversi(), boardPanel.getBWidth(), boardPanel.getBHeight());
    this.boardPanel = boardPanel;
  }

  public void drawHints(Graphics2D g2d, Hexagon selected, int q, int r, ReversiReadOnly board, int bwidth, int bheight, Disc player) {
    if (selected != null) {
      Coordinate dest = new Coordinate(q, r);

      int change = board.getScoreForPlayer(board, dest, player);

      int boardSize = board.getSize();

      double maxHexWidth = (double) bwidth / (boardSize * 2);

      double maxHexHeight = (double) bheight / ((boardSize) * 2);

      int fontSize = (int)Math.min(maxHexWidth / 2, maxHexHeight / 2);


      g2d.setFont(new Font("Default", Font.BOLD, fontSize)); // Set the font to the dynamically calculated size

      g2d.setColor(Color.BLACK); // Color for text
      g2d.drawString(String.valueOf(change), (int)selected.x, (int)selected.y);
    }
  }

  @Override
  public void paintComponent(Graphics g){
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;

    for (Hexagon hex : hexagons.keySet()){
      if (hex.equals(selected) && hintsEnabled){
        drawHints(g2d, hex, hexagons.get(hex).getFirstCoordinate(), hexagons.get(hex).getSecondCoordinate(), boardPanel.getReadReversi(), boardPanel.getBWidth(), boardPanel.getBHeight(), player);
      }
    }
  }





}

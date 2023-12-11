package view;

import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.Font;
import java.awt.Color;

import model.Coordinate;
import model.Disc;
import model.ReversiReadOnly;

/**
 * The HintDecorator class decorates a BoardPanel to add hint view functionality.
 * It displays hints on the game board to indicate the potential score changes when placing a disc.
 * This decorator is used to enhance the visual representation of hints on the board panel.
 * To use this decorator, create an instance by passing an existing BoardPanel as a parameter,
 * and then call its methods to draw hints on the board.
 */
public class HintDecorator extends BoardPanel {
  private BoardPanel boardPanel;

  /**
   * Creates a new HintDecorator by decorating an existing BoardPanel.
   *
   * @param boardPanel The BoardPanel to be decorated with hint functionality.
   */
  public HintDecorator(BoardPanel boardPanel) {
    super(boardPanel.getReadReversi(), boardPanel.getBWidth(), boardPanel.getBHeight());
    this.boardPanel = boardPanel;
  }

  /**
   * Draws hints on the game board to indicate potential score changes when placing a disc at.
   * a specific position.
   *
   * @param g2d     The Graphics2D context for drawing.
   * @param selected The selected hexagon where hints should be displayed.
   * @param q       The first coordinate of the selected position.
   * @param r       The second coordinate of the selected position.
   * @param board   The ReversiReadOnly game board.
   * @param bwidth  The width of the board panel.
   * @param bheight The height of the board panel.
   * @param player  The player (Disc) for whom the hints are displayed.
   */
  public void drawHints(Graphics2D g2d, Hexagon selected, int q, int r, ReversiReadOnly board,
                        int bwidth, int bheight, Disc player) {
    if (selected != null) {
      Coordinate dest = new Coordinate(q, r);

      int change = board.getScoreForPlayer(board, dest, player);

      int boardSize = board.getSize();

      double maxHexWidth = (double) bwidth / (boardSize * 2);

      double maxHexHeight = (double) bheight / ((boardSize) * 2);

      int fontSize = (int)Math.min(maxHexWidth / 2, maxHexHeight / 2);


      g2d.setFont(new Font("Default", Font.BOLD, fontSize));

      g2d.setColor(Color.BLACK); // Color for text
      g2d.drawString(String.valueOf(change), (int)selected.x, (int)selected.y);
    }
  }

  /**
   * Overrides the paintComponent method to add hint drawing functionality to the decorated.
   * BoardPanel. It checks if a hexagon is selected and if hints are enabled, then calls drawHints.
   * to display hints.
   *
   * @param g The Graphics context for painting.
   */
  @Override
  public void paintComponent(Graphics g){
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;

    for (Hexagon hex : hexagons.keySet()){
      if (hex.equals(selected) && hintsEnabled){
        drawHints(g2d, hex, hexagons.get(hex).getFirstCoordinate(),
                hexagons.get(hex).getSecondCoordinate(), boardPanel.getReadReversi(),
                boardPanel.getBWidth(), boardPanel.getBHeight(), player);
      }
    }
  }





}

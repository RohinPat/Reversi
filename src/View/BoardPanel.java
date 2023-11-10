package view;

import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.util.ArrayList;

public class BoardPanel extends JPanel {
  private ArrayList<Hexagon> hexagons;
  private final int hexSize = 30; // Assuming a fixed hexagon size for simplicity

  public BoardPanel(int size) {
    hexagons = new ArrayList<>();
    this.setBackground(Color.WHITE);
    initializeHexagons(size);
  }

  private int calculateTotalHexagons(int size) {
    int total = 0;
    for (int i = 0; i < size; i++) {
      total += size + i;
    }
    for (int i = size - 2; i >= 0; i--) {
      total += size + i;
    }
    return total;
  }

  private void initializeHexagons(int size) {
    int y = 100; // Start position for y
    int startX = 1000 ; // Start position for x
    int totalHexagons = calculateTotalHexagons(size);

    // Upper Half (Before Middle Row)
    for (int row = 0; row < size - 1; row++) {
      int hexagonsInRow = size + row;
      int x = (getWidth() - hexagonsInRow * hexSize); // Centering each row

      for (int col = 0; col < hexagonsInRow; col++) {
        hexagons.add(new Hexagon(x + startX, y, hexSize));
        x += hexSize * 2; // Positioning for the next hexagon in the row
      }
      y += hexSize * 2; // Move down to the next row
    }

    // Middle and Lower Half (Middle Row and After)
    for (int row = size; row < 2 * size; row++) {
      int hexagonsInRow = 2 * size - 1 - (row - size);
      int x = (getWidth() - hexagonsInRow * hexSize); // Centering each row

      for (int col = 0; col < hexagonsInRow; col++) {
        hexagons.add(new Hexagon(x + startX, y, hexSize));
        x += hexSize * 2; // Positioning for the next hexagon in the row
      }
      y += hexSize * 2; // Move down to the next row
    }
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;

    // Draw hexagons
    for (Hexagon hex : hexagons) {
      g2d.setColor(Color.BLACK); // Set hexagon border color
      g2d.draw(hex); // Draw the hexagon (assuming Hexagon extends Polygon)
    }
  }
}

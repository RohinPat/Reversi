package view;

import javax.swing.JPanel;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import model.Board;
import model.Cell;
import model.Coordinate;
import model.Disc;

public class BoardPanel extends JPanel {
  private ArrayList<Hexagon> hexagons;
  private final double hexSize = 30; // Assuming a fixed hexagon size for simplicity

  public BoardPanel(LayoutManager layout, boolean isDoubleBuffered, ArrayList<Hexagon> hexagons) {
    super(layout, isDoubleBuffered);
    this.hexagons = hexagons;
  }

  public BoardPanel(Board board) {
    hexagons = new ArrayList<>();
    this.setBackground(Color.DARK_GRAY);
    initializeHexagons(board);

    addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e);
        handleHexagonClick(e.getX(), e.getY());
      }
    });
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

  private void handleHexagonClick(int mouseX, int mouseY) {
    for (Hexagon hex : hexagons) {
      if (hex.contains(mouseX, mouseY)) {
        // Check if the hexagon is already SELECTED
        if (hex.color.equals(Disc.SELECTED)) {
          // If it is, then set the color to EMPTY (unfilled)
          hex.color = Disc.EMPTY;
        } else {
          for (Hexagon hex1 : hexagons) {
            if (hex1.color.equals(Disc.SELECTED)) {
              hex1.color = Disc.EMPTY;
            }
          }
          hex.color = Disc.SELECTED;
        }
        repaint(); // Request a repaint so the color change is displayed
        break; // Exit the loop once we've found our hexagon
      }
    }
  }




  private void initializeHexagons(Board board) {
    double y = 100; // Start position for y
    double startX = 750; // Start position for x
    double hexWidth = hexSize * Math.sqrt(3);
    double hexHeight = hexSize * 1.5;
    int boardSize = board.getSize();

    // Calculate the offset for centering based on the widest row
    double offsetX = (getWidth() - (boardSize * hexWidth)) / 2;

    for (int upperRow = 0; upperRow < board.getSize() - 1; upperRow++) {
      int hexagonsInRow = (2 * board.getSize() - 1) - (board.getSize() - upperRow);
      double x = offsetX + (hexWidth / 2) * (boardSize - 1 - upperRow);
      for (int index = -upperRow; index < board.getSize(); index++) {
        hexagons.add(new Hexagon(x + startX, y, hexSize, board.getDiscAt(index, -(board.getSize() - 1 - upperRow))));
        x += hexSize * Math.sqrt(3); // Positioning for the next hexagon in the row
      }
      y += hexSize * 3 / 2; // Move down to the next row
    }

    // this second loop sets up the rest of the rows INCLUDING the middle row - intializes every
    // cell to be empty at first
    for (int lowerRow = 0; lowerRow < board.getSize(); lowerRow++) {
      int hexagonsInRow = (2 * board.getSize() - 1) - lowerRow;
      double x = offsetX + (hexWidth * lowerRow) / 2; // This line is changed
      for (int index = -(board.getSize() - 1); index < board.getSize() - lowerRow; index++) {
        hexagons.add(new Hexagon(x + startX, y, hexSize, board.getDiscAt(index, lowerRow)));
        x += hexSize * Math.sqrt(3); // Positioning for the next hexagon in the row
      }
      y += hexSize * 3 / 2; // Move down to the next row
    }
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;

    // Draw hexagons
    for (Hexagon hex : hexagons) {
      if (hex.color.equals(Disc.SELECTED)) {
        g2d.setColor(Color.CYAN);
        g2d.fill(hex);
      }
      else{
        g2d.setColor(Color.LIGHT_GRAY);
        g2d.fill(hex);
      }
      g2d.setColor(Color.BLACK);
      g2d.draw(hex); // Draw the hexagon border

      int circleDiameter = (int) (hexSize * 1.25); // Set the circle diameter to be smaller than the hexagon size

      // Calculate the center of the hexagon
      int centerX = (int) (hex.x);
      int centerY = (int) (hex.y);

      // Calculate the top-left corner of the circle by subtracting the radius from the hexagon's center
      int circleX = centerX - circleDiameter / 2;
      int circleY = centerY - circleDiameter / 2;

      if (hex.color.equals(Disc.BLACK)) {
        g2d.setColor(Color.BLACK);
        g2d.fillOval(circleX, circleY, circleDiameter, circleDiameter); // Draw the black filled circle
      } else if (hex.color.equals(Disc.WHITE)) {
        g2d.setColor(Color.WHITE);
        g2d.fillOval(circleX, circleY, circleDiameter, circleDiameter); // Draw the white filled circle
      }
      // If hex.color is Disc.EMPTY, we don't add anything inside, just the hexagon border is drawn
    }
  }


}

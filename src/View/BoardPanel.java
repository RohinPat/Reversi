package view;

import javax.swing.JPanel;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import model.Board;
import model.ReversiReadOnly;

public class BoardPanel extends JPanel {
  private ArrayList<Hexagon> hexagons;
  private Hexagon selected;
  private double hexSize = 30;
  private int maxLength;
  private int boardWidth;
  private int boardHeight;


  public BoardPanel(ReversiReadOnly board, int width, int height) {
    hexagons = new ArrayList<>();
    this.setBackground(Color.DARK_GRAY);
    this.setPreferredSize(new Dimension(width, height));
    this.boardWidth = width;
    this.boardHeight = height;
    maxLength = (board.getSize() * 2) - 1;
    initializeHexagons(board);

    addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e);
        handleHexagonClick(e.getX(), e.getY());
      }
    });

    this.addComponentListener(new ComponentAdapter() {
      @Override
      public void componentResized(ComponentEvent e) {
        // Store the coordinates of the selected hexagon (if any)
        int selectedQ = selected != null ? selected.q : -1;
        int selectedR = selected != null ? selected.r : -1;

        // Adjust hexagon size and reinitialize hexagons
        adjustHexagonSize(board);
        boardWidth = getWidth();
        boardHeight = getHeight();
        initializeHexagons(board);

        // Reselect the previously selected hexagon
        if (selectedQ != -1 && selectedR != -1) {
          for (Hexagon hex : hexagons) {
            if (hex.q == selectedQ && hex.r == selectedR) {
              selected = hex;
              break;
            }
          }
        }

        repaint();
      }
    });


  }

  private void adjustHexagonSize(ReversiReadOnly board) {

    // Calculate board dimensions in terms of hexagons
    int boardSize = board.getSize();
    int longestRowHexCount = (2 * boardSize) - 1; // Number of hexagons in the longest row

    // Horizontal space calculation
    // The length of the longest row of hexagons cannot exceed the panel width
    double maxHexWidth = (double) boardWidth / (longestRowHexCount + 1);

    // Vertical space calculation
    // The total height of all hexagon rows cannot exceed the panel height
    double maxHexHeight = (double) boardHeight / ((boardSize * 2 - 1) + 1);

    // Set hexSize to the smaller of maxHexWidth and maxHexHeight calculations
    hexSize = Math.min(maxHexWidth / Math.sqrt(3), maxHexHeight / 1.5);
  }


  private void handleHexagonClick(int mouseX, int mouseY) {
    boolean inBounds = false;
    for (Hexagon hex : hexagons) {
      if (hex.contains(mouseX, mouseY)) {
        if (!hex.equals(selected)) {
          selected = hex;
          System.out.println("(q : " + hex.q + ", r: " + hex.r + ")");
        } else {
          selected = null;
        }
        inBounds = true;
        repaint(); // Request a repaint so the color change is displayed
        break; // Exit the loop once we've found our hexagon
      }
    }
    if (!inBounds) {
      selected = null;
      repaint();
    }

  }


  private void initializeHexagons(ReversiReadOnly board) {
    hexagons.clear();
    double hexWidth = hexSize * Math.sqrt(3);
    double hexHeight = hexSize * 1.5;
    int boardSize = board.getSize();

    // Calculate the total width and height needed for the hexagon grid
    double totalWidth = ((boardSize * 2 - 1) * hexWidth) - hexWidth;
    double totalHeight = ((boardSize * 2 - 1) * hexHeight) - hexHeight;

    // Calculate the starting X and Y positions to center the grid
    double startX = (boardWidth - totalWidth) / 2;
    double startY = (boardHeight - totalHeight) / 2;

    double y = startY; // Initialize y with startY

    // Loop for upper part of the grid
    for (int upperRow = 0; upperRow < boardSize - 1; upperRow++) {
      double x = startX + (hexWidth / 2) * (boardSize - 1 - upperRow);
      for (int index = -upperRow; index < boardSize; index++) {
        hexagons.add(new Hexagon(x, y, hexSize, board.getDiscAt(index, -(boardSize - 1 - upperRow)), index, -(boardSize - 1 - upperRow)));
        x += hexSize * Math.sqrt(3); // Positioning for the next hexagon in the row
      }
      y += hexSize * 3 / 2; // Move down to the next row
    }

    // Loop for lower part of the grid including the middle row
    for (int lowerRow = 0; lowerRow < boardSize; lowerRow++) {
      double x = startX + (hexWidth * lowerRow) / 2;
      for (int index = -(boardSize - 1); index < boardSize - lowerRow; index++) {
        hexagons.add(new Hexagon(x, y, hexSize, board.getDiscAt(index, lowerRow), index, lowerRow));
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
      if (hex.equals(selected)) {
        g2d.setColor(Color.CYAN);
        g2d.fill(hex);
      } else {
        g2d.setColor(Color.LIGHT_GRAY);
        g2d.fill(hex);
      }
      g2d.setColor(Color.BLACK);
      g2d.draw(hex); // Draw the hexagon border

      int circleDiameter = (int) (hexSize); // Set the circle diameter to be smaller than the hexagon size

      // Calculate the center of the hexagon
      int centerX = (int) (hex.x);
      int centerY = (int) (hex.y);

      // Calculate the top-left corner of the circle by subtracting the radius from the hexagon's center
      int circleX = centerX - circleDiameter / 2;
      int circleY = centerY - circleDiameter / 2;
      if (!(hex.color == null)) {
        g2d.setColor(hex.color);
        g2d.fillOval(circleX, circleY, circleDiameter, circleDiameter); // Draw the white filled circle
      }
    }
    // If hex.color is Disc.EMPTY, we don't add anything inside, just the hexagon border is drawn
  }
}


package view;

import javax.swing.JPanel;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import model.ReversiReadOnly;

public class BoardPanel extends JPanel {
  private ArrayList<Hexagon> hexagons;
  private Hexagon selected;
  private final double hexSize = 30; // Assuming a fixed hexagon size for simplicity
  private int maxLength;

  public BoardPanel(ReversiReadOnly board) {
    hexagons = new ArrayList<>();
    this.setBackground(Color.DARK_GRAY);
    maxLength = (board.getSize() * 2) - 1;
    initializeHexagons(board);
    addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e);
        handleHexagonClick(e.getX(), e.getY());
      }
    });
  }

  public Dimension getPreferredSize() {
    double hexWidth = hexSize * Math.sqrt(3);
    double hexHeight = hexSize * 1.5;
    double boardSize = maxLength + 5;

    // Calculate the width and height needed to display the board
    int width = (int) (boardSize * hexWidth + 50); // Adding some margin
    int height = (int) (boardSize * hexHeight + 50); // Adding some margin

    return new Dimension(width, height);
  }

  private void handleHexagonClick(int mouseX, int mouseY) {
    boolean inBounds = false;
    for (Hexagon hex : hexagons) {
      if (hex.contains(mouseX, mouseY)) {
        if (!hex.equals(selected)){
          selected = hex;
          System.out.println("(q : " + hex.q + ", r: " + hex.r + ")");
        }
        else{
          selected = null;
        }
        inBounds = true;
        repaint(); // Request a repaint so the color change is displayed
        break; // Exit the loop once we've found our hexagon
      }
    }
    if (!inBounds){
      selected = null;
      repaint();
    }

  }




  private void initializeHexagons(ReversiReadOnly board) {
    double y = 100; // Start position for y
    double startX = 300; // Start position for x
    double hexWidth = hexSize * Math.sqrt(3);
    double hexHeight = hexSize * 1.5;
    int boardSize = board.getSize();

    // Calculate the offset for centering based on the widest row
    double offsetX = (getWidth() - (boardSize * hexWidth)) / 2;

    for (int upperRow = 0; upperRow < board.getSize() - 1; upperRow++) {
      double x = offsetX + (hexWidth / 2) * (boardSize - 1 - upperRow);
      for (int index = -upperRow; index < board.getSize(); index++) {
        hexagons.add(new Hexagon(x + startX, y, hexSize, board.getDiscAt(index, -(board.getSize() - 1 - upperRow)), index, -(board.getSize() - 1 - upperRow)));
        x += hexSize * Math.sqrt(3); // Positioning for the next hexagon in the row
      }
      y += hexSize * 3 / 2; // Move down to the next row
    }

    // this second loop sets up the rest of the rows INCLUDING the middle row - intializes every
    // cell to be empty at first
    for (int lowerRow = 0; lowerRow < board.getSize(); lowerRow++) {
      double x = offsetX + (hexWidth * lowerRow) / 2; // This line is changed
      for (int index = -(board.getSize() - 1); index < board.getSize() - lowerRow; index++) {
        hexagons.add(new Hexagon(x + startX, y, hexSize, board.getDiscAt(index, lowerRow), index, lowerRow));
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
      }
      else{
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


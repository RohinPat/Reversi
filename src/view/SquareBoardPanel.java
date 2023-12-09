package view;

import java.awt.*;
import java.awt.event.ComponentListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;

import javax.swing.JPanel;
import model.Board; // Assuming you have a Board model representing the game's board.
import model.Disc; // Your Disc enum (BLACK, WHITE, EMPTY).
import model.ReversiReadOnly;

public class SquareBoardPanel extends JPanel {
  private ReversiReadOnly board; // The game board.
  private double squareSize; // The size of each square.
  private int boardWidth;
  private int boardHeight;

  public SquareBoardPanel(ReversiReadOnly board, int width, int height) {
    this.board = board;
    this.squareSize = 30;

    this.setBackground(Color.DARK_GRAY);
    this.setPreferredSize(new Dimension(width, height));
    this.boardWidth = width;
    this.boardHeight = height;
    this.setFocusable(true);
    this.requestFocusInWindow();
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;

    // Draw the grid of squares based on the board state
    for (int row = 0; row < board.getSize(); row++) {
      for (int col = 0; col < board.getSize(); col++) {
        Disc disc = board.getDiscAt(row, col); // Method to get the disc at a specific position
        double x = col * squareSize;
        double y = row * squareSize;

        Square square = new Square(x, y, squareSize, disc);
        g2d.setColor(square.color);
        g2d.fill(square);
      }
    }
  }
}

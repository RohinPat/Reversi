package view;

import java.awt.*;
import java.awt.event.ComponentListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.swing.JPanel;
import model.Board; // Assuming you have a Board model representing the game's board.
import model.CartesianCoordinate;
import model.Coordinate;
import model.Disc; // Your Disc enum (BLACK, WHITE, EMPTY).
import model.Position;
import model.ReversiReadOnly;

public class SquareBoardPanel extends JPanel {
  private ReversiReadOnly board; // The game board.
  private double squareSize; // The size of each square.
  private int boardWidth;
  private int boardHeight;
  private ConcurrentMap<Square, Position> squares;

  public SquareBoardPanel(ReversiReadOnly board, int width, int height) {
    this.board = board;
    this.squareSize = 30;
    this.squares = new ConcurrentHashMap<>();

    this.setBackground(Color.DARK_GRAY);
    this.setPreferredSize(new Dimension(width, height));
    this.boardWidth = width;
    this.boardHeight = height;
    this.setFocusable(true);
    this.requestFocusInWindow();
  }

  public void drawSquare(ReversiReadOnly board){
    squares.clear();
    double squareWidth = squareSize;
    double squareHeight = squareSize;
    int boardSize = board.getSize();

    double startX = 10;
    double startY = 10;

    for (int row = 0; row < boardSize; row++) {
      for (int col = 0; col < boardSize; col++) {
        int x = (int) Math.round(startX + (col * squareSize));
        int y = (int) Math.round(startY + (row * squareSize));
        squares.put(new Square(x, y, squareSize, Disc.EMPTY), new CartesianCoordinate(row, col));
      }
    }
  }


  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;

    drawSquare(board);

    float thickness = 2; // Set the thickness you want for the squares' outline
    g2d.setStroke(new BasicStroke(thickness));

    for (Square square : squares.keySet()) {
      g2d.setColor(Color.WHITE);
      g2d.fill(square);
      g2d.setColor(Color.BLACK);
      g2d.draw(square);
    }
  }
}

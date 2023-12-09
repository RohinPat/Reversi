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

    squares.put(new Square(100, 100, squareSize, Disc.BLACK), new CartesianCoordinate(1, 1));
    /*for (int row = 0; row < boardSize; row++) {
      for (int col = 0; col < boardSize; col++) {

      }
    }*/
  }


  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;

    drawSquare(board);

    for (Square hex : squares.keySet()) {
      g2d.setColor(Color.BLACK);
      g2d.draw(hex);
    }
  }
}

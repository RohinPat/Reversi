package view;

import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.awt.event.KeyListener;

import javax.swing.*;

import controller.ControllerFeatures;
import model.Board; // Assuming you have a Board model representing the game's board.
import model.CartesianCoordinate;
import model.Coordinate;
import model.Disc; // Your Disc enum (BLACK, WHITE, EMPTY).
import model.Position;
import model.Reversi;
import model.ReversiReadOnly;

public class SquareBoardPanel extends JPanel implements IBoardPanel {
  private ReversiReadOnly board; // The game board.
  private double squareSize; // The size of each square.
  private int boardWidth;
  private int boardHeight;
  private ConcurrentMap<Square, Position> squares;
  private Square selected;
  private ControllerFeatures controller;
  private JLabel helloLabel1;
  private JLabel scoreLabel1;
  private JLabel turnLabel1;


  public SquareBoardPanel(ReversiReadOnly board, int width, int height) {
    this.board = board;
    this.squareSize = 30;

    helloLabel1 = new JLabel();
    helloLabel1.setForeground(Color.WHITE);
    helloLabel1.setFont(new Font(helloLabel1.getFont().getName(), Font.PLAIN, 5));
    this.add(helloLabel1);

    scoreLabel1 = new JLabel();
    scoreLabel1.setForeground(Color.WHITE);
    scoreLabel1.setHorizontalAlignment(JLabel.RIGHT);
    scoreLabel1.setFont(new Font(helloLabel1.getFont().getName(), Font.PLAIN, 5));
    this.add(scoreLabel1);

    turnLabel1 = new JLabel();
    turnLabel1.setForeground(Color.WHITE);
    turnLabel1.setFont(new Font(helloLabel1.getFont().getName(), Font.PLAIN, 5));
    this.add(turnLabel1);
    
    this.squares = new ConcurrentHashMap<>();
    
    this.setBackground(Color.DARK_GRAY);
    this.setPreferredSize(new Dimension(width, height));
    this.boardWidth = width;
    this.boardHeight = height;
    ComponentListener resize = new SquareBoardPanel.ResizeListener(board);
    this.addComponentListener(resize);
    this.setFocusable(true);
    MouseListener squareSelect = new SquareMouseListener();
    this.addMouseListener(squareSelect);
    KeyListener keyInputListener = new KeyInputListener();
    this.addKeyListener(keyInputListener);
    this.requestFocusInWindow();

    initializeBoard(board);
  }

  /**
   * Updates the player label to display the name of the current player's turn.
   * If a valid game controller is set, this method retrieves the current player's
   * identity and updates the label accordingly, showing either "Black" or "White"
   * as the current player.
   */
  private void updatePlayerLabel() {
    if (controller != null) {
      String playerName;
      Disc playerIdentity = controller.getPlayer();
      if (playerIdentity.equals(Disc.BLACK)) {
        playerName = "Black";
      } else {
        playerName = "White";
      }
      helloLabel1.setText("Player: " + playerName);
    }
  }

  /**
   * Updates the score label to display the current score of the player whose turn
   * it is in the Reversi game.
   *
   * @param board The {@link ReversiReadOnly} board representing the current state
   *              of the Reversi game, including the scores of both players.
   *              This board is used to retrieve the score of the current player.
   */
  private void updateScoreLabel1(ReversiReadOnly board) {
    if (controller != null) {
      int score;
      Disc playerIdentity = controller.getPlayer();

      if (playerIdentity.equals(Disc.BLACK)) {
        score = board.getScore(Disc.BLACK);
      } else {
        score = board.getScore(Disc.WHITE);
      }
      scoreLabel1.setText("Score: " + score);
    }
  }


  /**
   * Updates the turn label to display whether it is currently the player's turn
   * in the Reversi game. Also used at the end of the game to display the winner of the game.
   *
   * @param board2 The {@link ReversiReadOnly} board representing the current state
   *               of the Reversi game, used to determine the current turn.
   */
  private void updateTurnLabel1(ReversiReadOnly board2) {
    if (controller != null) {
      if (!board2.isGameOver()) {
        String turnMonitor;
        if (controller.getTurn().isPlayerTurn((Reversi) board2)) {
          turnMonitor = "Your Turn";
        } else {
          turnMonitor = "";
        }

        turnLabel1.setText(turnMonitor);
      } else {
        turnLabel1.setText(board2.getState().toString());
      }
    }

  }

  /**
   * Adjusts the size and font of the labels (helloLabel1, scoreLabel1, and turnLabel1)
   * based on the current dimensions of the game board and the desired layout.
   * This method calculates the appropriate font size and label positions to ensure
   * they fit within the specified board layout.
   */
  private void adjustLabelSizeAndFont() {
    double hexHeight = squareSize;
    int boardSize = board.getSize();

    double totalHeight = ((boardSize * 2 - 1) * hexHeight) - hexHeight;

    double startY = (boardHeight - totalHeight) / 2;

    int newFontSize = ((boardWidth + boardHeight) / 50);

    helloLabel1.setFont(new Font(helloLabel1.getFont().getName(), Font.PLAIN, newFontSize));
    scoreLabel1.setFont(new Font(scoreLabel1.getFont().getName(), Font.PLAIN, newFontSize));
    turnLabel1.setFont(new Font(turnLabel1.getFont().getName(), Font.PLAIN, newFontSize));

    helloLabel1.setBounds(10, 0, boardWidth / 3, (int) (startY));
    scoreLabel1.setBounds(
            (2 * boardWidth / 3) + 1, 0, boardWidth / 3 - 1, (int) (startY));
    turnLabel1.setBounds(10, boardHeight - (int) startY,
            boardWidth / 3 - 1, (int) (startY));
  }

  /**
   * This inner class implements the {@link MouseListener} interface and is used to
   * handle mouse click events on hexagonal game board elements. It overrides the
   * necessary methods for mouse click handling, while leaving the other mouse event
   * methods unimplemented.
   */
  private class SquareMouseListener implements MouseListener {

    /**
     * Invoked when a hexagon on the game board is clicked. This method delegates
     * the click event to the appropriate handler method.
     *
     * @param e The {@link MouseEvent} containing information about the click event,
     *          including the coordinates of the click.
     */
    @Override
    public void mouseClicked(MouseEvent e) {
      handleMouseClick(e.getX(), e.getY());

    }

    @Override
    public void mousePressed(MouseEvent e) {
      // not used but has to be overwritten
    }

    @Override
    public void mouseReleased(MouseEvent e) {
      // not used but has to be overwritten
    }

    @Override
    public void mouseEntered(MouseEvent e) {
      // not used but has to be overwritten
    }

    @Override
    public void mouseExited(MouseEvent e) {
      // not used but has to be overwritten
    }
  }

  /**
   * This inner class implements the {@link KeyListener} interface and is used to
   * handle keyboard input events for specific key presses. It responds to the "M" key
   * for confirming a move and the "Space" key for passing the turn in the Reversi game.
   */
  private class KeyInputListener implements KeyListener {

    @Override
    public void keyTyped(KeyEvent e) {
      //not used but has to be overwritten
    }

    /**
     * Invoked when a key is pressed. This method checks for specific key presses,
     * such as "M" for confirming a move and "Space" for passing the turn, and
     * triggers the corresponding actions.
     *
     * @param e The {@link KeyEvent} representing a key press event.
     */
    @Override
    public void keyPressed(KeyEvent e) {
      if (e.getKeyCode() == KeyEvent.VK_M) {
        if (selected == null) {
          showInvalidMoveDialog("No hexagon selected. " +
                  "Please select a hexagon before confirming the move.");
        }
        else {
          controller.confirmMove(squares.get(selected).getFirstCoordinate(), squares.get(selected).getSecondCoordinate());
        }
      } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
        controller.passTurn();
      }
    }

    @Override
    public void keyReleased(KeyEvent e) {
      //not used but has to be overwritten
    }
  }

  /**
   * Handles a mouse click event on a hexagon within the game board. The method determines
   * which hexagon, if any, has been clicked and updates the selection accordingly. It
   * also triggers the selection of the clicked hexagon in the game controller if one is
   * set.
   *
   * @param mouseX The x-coordinate of the mouse click.
   * @param mouseY The y-coordinate of the mouse click.
   */
  public void handleMouseClick(int mouseX, int mouseY) {
    boolean inBounds = false;
    for (Square hex : squares.keySet()) {
      if (hex.contains(mouseX, mouseY)) {
        if (!hex.equals(selected)) {
          selected = hex;
          inBounds = true;
          repaint();
          break;
        }
      }
    }
    if (!inBounds) {
      selected = null;
      repaint();
    }
  }

  @Override
  public void showInvalidMoveDialog(String message) {
    JOptionPane.showMessageDialog(this, message, "Invalid Move",
            JOptionPane.ERROR_MESSAGE);
  }

  @Override
  public void setController(ControllerFeatures cont) {
    this.controller = cont;
  }

  public void initializeBoard(ReversiReadOnly board){
    selected = null;
    squares.clear();
    double squareWidth = squareSize;
    double squareHeight = squareSize;
    int boardSize = board.getSize();

    double totalWidth = ((boardSize) * squareWidth);
    double totalHeight = ((boardSize) * squareHeight);

    double startX = (boardWidth - totalWidth) / 2;
    double startY = (boardHeight - totalHeight) / 2;

    for (int row = 0; row < boardSize; row++) {
      for (int col = 0; col < boardSize; col++) {
        double x = startX + (col * squareSize);
        double y = startY + (row * squareSize);
        squares.put(new Square(x, y, squareSize, board.getDiscAt(col, row)), new CartesianCoordinate(col, row));
      }
    }

    repaint();
  }

  /**
   * This inner class implements the {@link ComponentListener} interface and is used
   * to handle component resize events, specifically for the Reversi game board. It
   * adjusts the hexagon size, board dimensions, and label sizes/fonts when the
   * component (frame) is resized.
   */
  class ResizeListener implements ComponentListener {
    private final ReversiReadOnly board;

    /**
     * Constructs a {@code ResizeListener} with the specified {@link ReversiReadOnly}
     * board to be used for resizing calculations.
     *
     * @param board The {@link ReversiReadOnly} board representing the current state
     *              of the Reversi game, used for determining hexagon size and positions.
     */
    public ResizeListener(ReversiReadOnly board) {
      this.board = board;
    }

    /**
     * Adjusts the size of the hexagons on the game board based on the current dimensions
     * of the board and the desired layout. The method calculates the appropriate hexagon
     * size to fit within the specified board dimensions while maintaining the hexagon's
     * geometric proportions.
     *
     * @param board The {@link ReversiReadOnly} board representing the current state
     *              of the Reversi game. The board's size is used as a reference for
     *              determining the number of hexagons and their layout.
     */
    private void adjustHexagonSize(ReversiReadOnly board) {
      int boardSize = board.getSize();
      int longestRowHexCount = boardSize; // Number of hexagons in the longest row

      double maxHexWidth = (double) boardWidth / (longestRowHexCount + 1);

      double maxHexHeight = (double) boardHeight / ((boardSize) + 1);

      squareSize = Math.min(maxHexWidth / Math.sqrt(3), maxHexHeight / 1.5);
    }

    /**
     * Invoked when the component (frame) is resized. This method recalculates hexagon
     * sizes and positions, updates board dimensions, and adjusts label sizes/fonts
     * accordingly. It also ensures that the previously selected hexagon remains
     * selected if it still exists after the resize.
     *
     * @param e The {@link ComponentEvent} representing the component resize event.
     */
    @Override
    public void componentResized(ComponentEvent e) {
      int selectedQ = selected != null ? squares.get(selected).getFirstCoordinate() : -1;
      int selectedR = selected != null ? squares.get(selected).getSecondCoordinate() : -1;

      adjustHexagonSize(this.board);
      boardWidth = getWidth();
      boardHeight = getHeight();
      initializeBoard(this.board);

      if (selectedQ != -1 && selectedR != -1) {
        for (Square hex : squares.keySet()) {
          if (squares.get(hex).getFirstCoordinate() == selectedQ && squares.get(hex).getSecondCoordinate() == selectedR) {
            selected = hex;
            break;
          }
        }
      }
      adjustLabelSizeAndFont();

      repaint();
    }

    @Override
    public void componentMoved(ComponentEvent e) {
      //not used but has to be overwritten
    }

    @Override
    public void componentShown(ComponentEvent e) {
      //not used but has to be overwritten
    }

    @Override
    public void componentHidden(ComponentEvent e) {
      //not used but has to be overwritten
    }
  }


  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);

    updatePlayerLabel();
    updateScoreLabel1(board);
    updateTurnLabel1(board);

    Graphics2D g2d = (Graphics2D) g;

    float thickness = 1; // Set the thickness you want for the squares' outline
    g2d.setStroke(new BasicStroke(thickness));

    for (Square square : squares.keySet()) {
      if (square.equals(selected)) {
        g2d.setColor(Color.CYAN);
        g2d.fill(square);
      } else {
        g2d.setColor(Color.LIGHT_GRAY);
        g2d.fill(square);
      }
      g2d.setColor(Color.BLACK);
      g2d.draw(square);

      int circleDiameter = (int) (squareSize) / 2;

      int centerX = (int) (square.x);
      int centerY = (int) (square.y);

      int circleX = centerX + circleDiameter / 2;
      int circleY = centerY + circleDiameter / 2;
      if (square.color != null) {
        g2d.setColor(square.color);
        g2d.fillOval(circleX, circleY, circleDiameter, circleDiameter);
      }
    }


  }
}

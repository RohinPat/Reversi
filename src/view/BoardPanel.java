package view;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JOptionPane;

import java.awt.event.ComponentEvent;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import controller.ControllerFeatures;
import model.Coordinate;
import model.Disc;
import model.Reversi;
import model.ReversiReadOnly;

/**
 * Used for our panel which contains our hexagons to display in the GUI.
 * Starts by taking in a preset size, but allows for resizability with dynamic hexagon.
 * Sizing.
 */
public class BoardPanel extends JPanel {
  private final ConcurrentMap<Hexagon, Coordinate> hexagons;
  private Hexagon selected;
  private double hexSize = 30;
  private int boardWidth;
  private int boardHeight;
  private ControllerFeatures controller;
  private final JLabel helloLabel;
  private final JLabel scoreLabel;
  private final JLabel turnLabel;
  private final ReversiReadOnly board;

  public void setController(ControllerFeatures cont) {
    this.controller = cont;
  }


  /**
   * Instantiates the panel and also opens listeners to ensure functionality for mouse input.
   * And also for resizing of the board.
   *
   * @param board1 the board to be displayed.
   * @param width  the width of the current window.
   * @param height the height of the current window.
   */
  public BoardPanel(ReversiReadOnly board1, int width, int height) {

    board = board1;
    this.setLayout(null);

    helloLabel = new JLabel();
    helloLabel.setForeground(Color.WHITE);
    helloLabel.setFont(new Font(helloLabel.getFont().getName(), Font.PLAIN, 5));
    this.add(helloLabel);

    scoreLabel = new JLabel();
    scoreLabel.setForeground(Color.WHITE);
    scoreLabel.setHorizontalAlignment(JLabel.RIGHT);
    scoreLabel.setFont(new Font(helloLabel.getFont().getName(), Font.PLAIN, 5));
    this.add(scoreLabel);

    turnLabel = new JLabel();
    turnLabel.setForeground(Color.WHITE);
    turnLabel.setFont(new Font(helloLabel.getFont().getName(), Font.PLAIN, 5));
    this.add(turnLabel);

    hexagons = new ConcurrentHashMap<>();

    this.setBackground(Color.DARK_GRAY);
    this.setPreferredSize(new Dimension(width, height));
    this.boardWidth = width;
    this.boardHeight = height;
    MouseListener hexagonSelect = new HexagonMouseListener();
    ComponentListener resize = new ResizeListener(board);
    this.addMouseListener(hexagonSelect);
    this.addComponentListener(resize);
    KeyListener keyInputListener = new KeyInputListener();
    this.addKeyListener(keyInputListener);
    this.setFocusable(true);
    this.requestFocusInWindow();

    initializeHexagons(board);
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
      helloLabel.setText("Player: " + playerName);
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
  private void updateScoreLabel(ReversiReadOnly board) {
    if (controller != null) {
      int score;
      Disc playerIdentity = controller.getPlayer();

      if (playerIdentity.equals(Disc.BLACK)) {
        score = board.getScore(Disc.BLACK);
      } else {
        score = board.getScore(Disc.WHITE);
      }
      scoreLabel.setText("Score: " + score);
    }
  }


  /**
   * Updates the turn label to display whether it is currently the player's turn
   * in the Reversi game.
   *
   * @param board2 The {@link ReversiReadOnly} board representing the current state
   *               of the Reversi game, used to determine the current turn.
   */
  private void updateTurnLabel(ReversiReadOnly board2) {
    if (controller != null) {
      String turnMonitor;
      if (controller.getTurn().isPlayerTurn((Reversi) board2)) {
        turnMonitor = "Your Turn";
      } else {
        turnMonitor = "";
      }

      turnLabel.setText(turnMonitor);
    }
  }

  /**
   * Adjusts the size and font of the labels (helloLabel, scoreLabel, and turnLabel)
   * based on the current dimensions of the game board and the desired layout.
   * This method calculates the appropriate font size and label positions to ensure
   * they fit within the specified board layout.
   */
  private void adjustLabelSizeAndFont() {
    double hexHeight = hexSize * 1.5;
    int boardSize = board.getSize();

    double totalHeight = ((boardSize * 2 - 1) * hexHeight) - hexHeight;

    double startY = (boardHeight - totalHeight) / 2;

    int newFontSize = ((boardWidth + boardHeight) / 50);

    helloLabel.setFont(new Font(helloLabel.getFont().getName(), Font.PLAIN, newFontSize));
    scoreLabel.setFont(new Font(scoreLabel.getFont().getName(), Font.PLAIN, newFontSize));
    turnLabel.setFont(new Font(turnLabel.getFont().getName(), Font.PLAIN, newFontSize));

    helloLabel.setBounds(10, 0, boardWidth / 3, (int) (startY));
    scoreLabel.setBounds(
            (2 * boardWidth / 3) + 1, 0, boardWidth / 3 - 1, (int) (startY));
    turnLabel.setBounds(10, boardHeight - (int)startY,
            boardWidth / 3 - 1, (int) (startY));
  }

  /**
   * This inner class implements the {@link MouseListener} interface and is used to
   * handle mouse click events on hexagonal game board elements. It overrides the
   * necessary methods for mouse click handling, while leaving the other mouse event
   * methods unimplemented.
   */
  private class HexagonMouseListener implements MouseListener {

    /**
     * Invoked when a hexagon on the game board is clicked. This method delegates
     * the click event to the appropriate handler method.
     *
     * @param e The {@link MouseEvent} containing information about the click event,
     *          including the coordinates of the click.
     */
    @Override
    public void mouseClicked(MouseEvent e) {
      handleHexagonClick(e.getX(), e.getY());
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
   * This inner class implements the {@link ComponentListener} interface and is used
   * to handle component resize events, specifically for the Reversi game board. It
   * adjusts the hexagon size, board dimensions, and label sizes/fonts when the
   * component (frame) is resized.
   */
  private class ResizeListener implements ComponentListener {
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
     * Invoked when the component (frame) is resized. This method recalculates hexagon
     * sizes and positions, updates board dimensions, and adjusts label sizes/fonts
     * accordingly. It also ensures that the previously selected hexagon remains
     * selected if it still exists after the resize.
     *
     * @param e The {@link ComponentEvent} representing the component resize event.
     */
    @Override
    public void componentResized(ComponentEvent e) {
      int selectedQ = selected != null ? hexagons.get(selected).getQ() : -1;
      int selectedR = selected != null ? hexagons.get(selected).getR() : -1;

      adjustHexagonSize(this.board);
      boardWidth = getWidth();
      boardHeight = getHeight();
      initializeHexagons(this.board);

      if (selectedQ != -1 && selectedR != -1) {
        for (Hexagon hex : hexagons.keySet()) {
          if (hexagons.get(hex).getQ() == selectedQ && hexagons.get(hex).getR() == selectedR) {
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
        controller.confirmMove();
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
    int longestRowHexCount = (2 * boardSize) - 1; // Number of hexagons in the longest row

    double maxHexWidth = (double) boardWidth / (longestRowHexCount + 1);

    double maxHexHeight = (double) boardHeight / ((boardSize * 2 - 1) + 1);

    hexSize = Math.min(maxHexWidth / Math.sqrt(3), maxHexHeight / 1.5);
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
  private void handleHexagonClick(int mouseX, int mouseY) {
    boolean inBounds = false;
    for (Hexagon hex : hexagons.keySet()) {
      if (hex.contains(mouseX, mouseY)) {
        if (!hex.equals(selected)) {
          selected = hex;
        } else {
          selected = null;
        }
        inBounds = true;
        repaint();
        break;
      }
    }
    if (!inBounds) {
      selected = null;
      repaint();
    }
    if (controller != null && selected != null) {
      controller.selectHexagon(hexagons.get(selected).getQ(), hexagons.get(selected).getR());
    }
  }

  /**
   * Retrieves the coordinates of the currently selected hexagon on the game board.
   *
   * @return A {@link Coordinate} object representing the coordinates (q, r) of the
   *         currently selected hexagon. If no hexagon is currently selected, this
   *         method returns null.
   */
  public Coordinate getSelectedHexagon() {
    return new Coordinate(hexagons.get(selected).getQ(), hexagons.get(selected).getR());
  }



  /**
   * Initializes the hexagonal grid on the game board based on the current game state.
   * This method calculates the positions and creates hexagons for each cell on the game board.
   * The created hexagons are associated with their respective coordinates and the disc
   * values from the game board.
   *
   * @param board The {@link ReversiReadOnly} board representing the current state of
   *              the Reversi game. The hexagonal grid is initialized based on the board's
   *              size and the disc values it contains.
   */
  public void initializeHexagons(ReversiReadOnly board) {
    hexagons.clear();
    double hexWidth = hexSize * Math.sqrt(3);
    double hexHeight = hexSize * 1.5;
    int boardSize = board.getSize();

    double totalWidth = ((boardSize * 2 - 1) * hexWidth) - hexWidth;
    double totalHeight = ((boardSize * 2 - 1) * hexHeight) - hexHeight;

    double startX = (boardWidth - totalWidth) / 2;
    double startY = (boardHeight - totalHeight) / 2;

    double y = startY;

    // Loop for upper part of the grid
    for (int upperRow = 0; upperRow < boardSize - 1; upperRow++) {
      double x = startX + (hexWidth / 2) * (boardSize - 1 - upperRow);
      for (int index = -upperRow; index < boardSize; index++) {
        hexagons.put(new Hexagon(x, y, hexSize,
                        board.getDiscAt(index, -(boardSize - 1 - upperRow))),
                new Coordinate(index, -(boardSize - 1 - upperRow)));
        x += hexSize * Math.sqrt(3); // Positioning for the next hexagon in the row
      }
      y += hexSize * 3 / 2;
    }

    // Loop for lower part of the grid including the middle row
    for (int lowerRow = 0; lowerRow < boardSize; lowerRow++) {
      double x = startX + (hexWidth * lowerRow) / 2;
      for (int index = -(boardSize - 1); index < boardSize - lowerRow; index++) {
        hexagons.put(new Hexagon(x, y, hexSize, board.getDiscAt(index, lowerRow)),
                new Coordinate(index, lowerRow));
        x += hexSize * Math.sqrt(3); // Positioning for the next hexagon in the row
      }
      y += hexSize * 3 / 2;
    }

    repaint();
  }

  /**
   * Displays a dialog box to inform the user of an invalid move in the Reversi game.
   *
   * @param message The message to be displayed in the dialog box, providing information
   *                about the reason for the move being invalid.
   */
  public void showInvalidMoveDialog(String message) {
    JOptionPane.showMessageDialog(this, message, "Invalid Move",
            JOptionPane.ERROR_MESSAGE);
  }

  /**
   * Overrides the paintComponent method to customize the rendering of the Reversi game board.
   * This method updates labels displaying player information, score, and turn status. It also
   * draws hexagons and any associated game pieces on the board based on the current game state.
   *
   * @param g The {@link Graphics} object used for painting the game board.
   */
  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);

    updatePlayerLabel();
    updateScoreLabel(board);
    updateTurnLabel(board);

    Graphics2D g2d = (Graphics2D) g;

    // Draw hexagons
    for (Hexagon hex : hexagons.keySet()) {
      if (hex.equals(selected)) {
        g2d.setColor(Color.CYAN);
        g2d.fill(hex);
      } else {
        g2d.setColor(Color.LIGHT_GRAY);
        g2d.fill(hex);
      }
      g2d.setColor(Color.BLACK);
      g2d.draw(hex);

      int circleDiameter = (int) (hexSize);

      int centerX = (int) (hex.x);
      int centerY = (int) (hex.y);

      int circleX = centerX - circleDiameter / 2;
      int circleY = centerY - circleDiameter / 2;
      if (hex.color != null) {
        g2d.setColor(hex.color);
        g2d.fillOval(circleX, circleY, circleDiameter, circleDiameter);
      }
    }
  }
}


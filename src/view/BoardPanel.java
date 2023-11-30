package view;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

import controller.ControllerFeatures;
import model.Board;
import model.Coordinate;
import model.Disc;
import model.ReversiReadOnly;

/**
 * Used for our panel which contains our hexagons to display in the GUI.
 * Starts by taking in a preset size, but allows for resizability with dynamic hexagon.
 * Sizing.
 */
public class BoardPanel extends JPanel {
  private ConcurrentHashMap<Hexagon, Coordinate> hexagons;
  private Hexagon selected;
  private double hexSize = 30;
  private int boardWidth;
  private int boardHeight;
  private MouseListener hexagonSelect;
  private ComponentListener resize;
  private KeyListener keyInputListener;
  private ControllerFeatures controller;
  private JLabel helloLabel;
  private JLabel scoreLabel;
  private ReversiReadOnly board;

  public void setController(ControllerFeatures cont){
    this.controller = cont;
  }


  /**
   * Instantiates the panel and also opens listeners to ensure functionality for mouse input.
   * And also for resizing of the board.
   * @param board1 the board to be displayed.
   * @param width the width of the current window.
   * @param height the height of the current window.
   */
  public BoardPanel(ReversiReadOnly board1, int width, int height) {

    board = board1;
    // Set layout to null for absolute positioning
    this.setLayout(null);

    // Initialize the hello label
    helloLabel = new JLabel();
    helloLabel.setForeground(Color.WHITE); // Set text color to white
    helloLabel.setBounds(10, 10, width/2, 20); // Set position (x, y) and size (width, height)
    helloLabel.setFont(new Font(helloLabel.getFont().getName(), Font.PLAIN, 5));
    this.add(helloLabel);

    scoreLabel = new JLabel();
    scoreLabel.setForeground(Color.WHITE); // Set text color to white
    scoreLabel.setBounds(width/2 + 1, 10, width/2 - 1, 20); // Set position (x, y) and size (width, height)
    scoreLabel.setHorizontalAlignment(JLabel.RIGHT);
    scoreLabel.setFont(new Font(helloLabel.getFont().getName(), Font.PLAIN, 5));
    this.add(scoreLabel);

    hexagons = new ConcurrentHashMap<>();

    this.setBackground(Color.DARK_GRAY);
    this.setPreferredSize(new Dimension(width, height));
    this.boardWidth = width;
    this.boardHeight = height;
    this.hexagonSelect = new hexagonMouseListener();
    this.resize = new resizeListener(board);
    this.addMouseListener(this.hexagonSelect);
    this.addComponentListener(this.resize);
    this.keyInputListener = new KeyInputListener();
    this.addKeyListener(this.keyInputListener);
    this.setFocusable(true);
    this.requestFocusInWindow();

    initializeHexagons(board);
  }

  private void updatePlayerLabel() {
    if (controller != null) {
      String playerName;
      Disc playerIdentity = controller.getPlayer();

      if (playerIdentity.equals(Disc.BLACK)){
        playerName = "Black"; // Replace with actual method name
      }
      else{
        playerName = "White"; // Replace with actual method name
      }

      helloLabel.setText("Player: " + playerName);
    }
  }

  private void updateScoreLabel(ReversiReadOnly board) {
    if (controller != null) {
      int score;
      Disc playerIdentity = controller.getPlayer();

      if (playerIdentity.equals(Disc.BLACK)){
        score = board.getScore(Disc.BLACK);
      }
      else{
        score = board.getScore(Disc.WHITE);
      }

      scoreLabel.setText("Score: " + score);
    }
  }

  private void adjustLabelSizeAndFont() {
    // Calculate the new font size (for example, based on the height of the panel)
    int newFontSize = Math.max(5, getHeight() / 20); // Adjust this calculation as needed

    // Update the label's font size
    helloLabel.setFont(new Font(helloLabel.getFont().getName(), Font.PLAIN, newFontSize));
    scoreLabel.setFont(new Font(helloLabel.getFont().getName(), Font.PLAIN, newFontSize));

    // Update the label's position and size if necessary
    helloLabel.setBounds(10, 10, getWidth()/2, newFontSize + 10);
    scoreLabel.setBounds(boardWidth/2 + 1, 10, boardWidth/2 - 1, newFontSize + 10); // Set position (x, y) and size (width, height)
  }

  private class hexagonMouseListener implements MouseListener {

    @Override
    public void mouseClicked(MouseEvent e) {
      handleHexagonClick(e.getX(), e.getY());
    }

    @Override
    public void mousePressed(MouseEvent e) {
      // blank
    }

    @Override
    public void mouseReleased(MouseEvent e) {
      // blank
    }

    @Override
    public void mouseEntered(MouseEvent e) {
      // blank
    }

    @Override
    public void mouseExited(MouseEvent e) {
      // blank
    }
  }

  private class resizeListener implements ComponentListener{
    private ReversiReadOnly board;

    public resizeListener(ReversiReadOnly board) {
      this.board = board;
    }

    @Override
    public void componentResized(ComponentEvent e) {
      int selectedQ = selected != null ? selected.q : -1;
      int selectedR = selected != null ? selected.r : -1;

      adjustHexagonSize(this.board);
      boardWidth = getWidth();
      boardHeight = getHeight();
      initializeHexagons(this.board);

      if (selectedQ != -1 && selectedR != -1) {
        for (Hexagon hex : hexagons.keySet()) {
          if (hex.q == selectedQ && hex.r == selectedR) {
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
      //blank
    }

    @Override
    public void componentShown(ComponentEvent e) {
      //blank
    }

    @Override
    public void componentHidden(ComponentEvent e) {
      //blank
    }
  }

  private class KeyInputListener implements KeyListener {

    @Override
    public void keyTyped(KeyEvent e) {
      // blank
    }

    @Override
    public void keyPressed(KeyEvent e) {
      if (e.getKeyCode() == KeyEvent.VK_M) {
        controller.confirmMove();
        System.out.println("move selected");
      } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
        controller.passTurn();
        System.out.println("pass selected");
      }
    }

    @Override
    public void keyReleased(KeyEvent e) {
      //blank
    }
  }

  private void adjustHexagonSize(ReversiReadOnly board) {
    int boardSize = board.getSize();
    int longestRowHexCount = (2 * boardSize) - 1; // Number of hexagons in the longest row

    double maxHexWidth = (double) boardWidth / (longestRowHexCount + 1);

    double maxHexHeight = (double) boardHeight / ((boardSize * 2 - 1) + 1);

    hexSize = Math.min(maxHexWidth / Math.sqrt(3), maxHexHeight / 1.5);
  }


  private void handleHexagonClick(int mouseX, int mouseY) {
    boolean inBounds = false;
    for (Hexagon hex : hexagons.keySet()) {
      if (hex.contains(mouseX, mouseY)) {
        if (!hex.equals(selected)) {
          selected = hex;
          System.out.println("(q : " + hexagons.get(selected).getQ() + ", r: " + hexagons.get(selected).getQ() + ")");
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

  public Coordinate getSelectedHexagon(){
    return new Coordinate(hexagons.get(selected).getQ(), hexagons.get(selected).getR());
  }


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
        hexagons.put(new Hexagon(x, y, hexSize, board.getDiscAt(index, -(boardSize - 1 - upperRow))),
                new Coordinate(index, -(boardSize - 1 - upperRow)));
        x += hexSize * Math.sqrt(3); // Positioning for the next hexagon in the row
      }
      y += hexSize * 3 / 2;
    }

    // Loop for lower part of the grid including the middle row
    for (int lowerRow = 0; lowerRow < boardSize; lowerRow++) {
      double x = startX + (hexWidth * lowerRow) / 2;
      for (int index = -(boardSize - 1); index < boardSize - lowerRow; index++) {
        hexagons.put(new Hexagon(x, y, hexSize, board.getDiscAt(index, lowerRow)), new Coordinate( index, lowerRow));
        x += hexSize * Math.sqrt(3); // Positioning for the next hexagon in the row
      }
      y += hexSize * 3 / 2;
    }

    repaint();
  }

  public void showInvalidMoveDialog(String message) {
    JOptionPane.showMessageDialog(this, message, "Invalid Move", JOptionPane.ERROR_MESSAGE);
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);

    updatePlayerLabel();
    updateScoreLabel(board);

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
      g2d.draw(hex); // Draw the hexagon border

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
    // If hex.color is Disc.EMPTY, we don't add anything inside, just the hexagon border is drawn
  }
}


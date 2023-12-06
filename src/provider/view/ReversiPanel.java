package provider.view;
import cs3500.reversi.Pair;
import provider.model.HexCoord;
import provider.model.PlayerOwnership;
import provider.model.ReversiReadOnlyModel;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;

/**
 * The {@code ReversiPanel} class represents a graphical panel for displaying the Reversi game. It
 * extends {@code JPanel} and provides methods to draw the game board, handle mouse and key events,
 * and interact with the underlying model.
 */
public class ReversiPanel extends JPanel implements ReversiPanelView {

  // CONSTANTS that can be modified
  private static final int WINDOW_WIDTH = 700;
  private static final int WINDOW_HEIGHT = 700;
  private Color bgColor;
  private static final Color TILE_BACKGROUND_COLOR = new Color(114, 117, 117);
  private static final Color TILE_OUTLINE_COLOR = new Color(143, 189, 144);
  private static final Color HIGHLIGHT_COLOR = new Color(197, 83, 124);
  private static final Color PLAYER_1_COLOR = Color.BLACK;
  private static final Color PLAYER_2_COLOR = Color.WHITE;
  private static final int MARGIN = 10;

  /**
   * Our view will need to display a model, so it needs to get the current sequence from the model.
   */
  private final ReversiReadOnlyModel model;
  /**
   * We allow an arbitrary number of listeners for our events.
   */
  private final List<Features> featuresListeners;

  /**
   * The currently highlighted hexagonal tile, empty meaning no tile is highlighted.
   */
  private Optional<HexCoord> highlightedTile;

  /**
   * The radius of the hexagons on the game board, updated in {@code paintComponent}.
   */
  private double hexRadius;
  /**
   * The bounding rectangle for the panel, updated in {@code paintComponent}.
   */
  private Rectangle bounds;

  private Pair<Boolean, String> error;

  private Optional<JLabel> errorMessage;

  /**
   * Constructs a {@code ReversiPanel} with the specified Reversi model.
   *
   * @param model The ReversiReadOnlyModel representing the current state of the game.
   */
  public ReversiPanel(ReversiReadOnlyModel model) {
    this.model = Objects.requireNonNull(model);
    this.featuresListeners = new ArrayList<>();
    this.highlightedTile = Optional.empty();
    this.bgColor = new Color(128, 161, 138);
    this.setBackground(this.bgColor);
    // configure listeners
    MouseEventsListener listener = new MouseEventsListener();
    this.addMouseListener(listener);
    this.addMouseMotionListener(listener);
    this.addKeyListener(new KeyEventsListener());
    // Ensure the component can receive focus
    this.setFocusable(true);
    // Request focus for the component
    this.requestFocusInWindow();
    this.error = new Pair<>(false, "");
    this.errorMessage = Optional.empty();
  }

  /**
   * Gets the preferred size of the panel using our constants.
   *
   * @return A Dimension object representing the preferred size of the panel.
   */
  @Override
  public Dimension getPreferredSize() {
    return new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT);
  }

  /**
   * Paints the component. Overrides the paintComponent method of JPanel. Removes all of the
   * existing components of this panel so that they can be redrawn. Displays any present error
   * messages. If the game of reversi is over, draw and end game screen. If the game is ongoing,
   * draw the current state of the game.
   *
   * @param g The Graphics object to draw on.
   */
  @Override
  protected void paintComponent(Graphics g) {
    this.removeAll();
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g.create();

    this.bounds = this.getBounds();

    if (this.model.isGameOver()) {
      this.createGameOverScreen();
    } else {
      this.createGameScreen(g2d);
    }
  }

  /**
   * Creates the game over screen by displaying player's scores over a plain background.
   */
  private void createGameOverScreen() {
    JLabel gameOverLabel = new JLabel("Game over!");
    JLabel scoreboard = new JLabel(
        "Player 1 Score: " + this.model.countClaimedTiles(PlayerOwnership.PLAYER_1) +
            "\n Player 2 Score: " + this.model.countClaimedTiles(PlayerOwnership.PLAYER_2));

    gameOverLabel.setSize(this.bounds.width, this.bounds.height / 20);
    gameOverLabel.setHorizontalAlignment(JLabel.CENTER);
    gameOverLabel.setVerticalAlignment(JLabel.CENTER);
    gameOverLabel.setLocation(0, this.bounds.height / 2 - (gameOverLabel.getHeight() / 2));

    scoreboard.setSize(this.bounds.width, this.bounds.height / 15);
    scoreboard.setHorizontalAlignment(JLabel.CENTER);
    scoreboard.setVerticalAlignment(JLabel.CENTER);
    scoreboard.setLocation(0, this.bounds.height / 2 + (scoreboard.getHeight() / 2));

    this.add(gameOverLabel);
    this.add(scoreboard);
  }

  /**
   * Draws the current state of the model on this panel. This method adapts to the size of the
   * window, dynamically drawing the model.
   *
   * @param g2d the 2d graphics being drawn on.
   */
  private void createGameScreen(Graphics2D g2d) {
    int gameBoardWidth = this.model.getBoard().getWidth();

    double minBound = Math.min(bounds.width - MARGIN, bounds.height - MARGIN);

    this.hexRadius =
        minBound / (double) gameBoardWidth / Math.sqrt(3);

    for (HexCoord hc : this.model.getBoard().getMap().keySet()) {
      if (this.highlightedTile.isPresent() && this.highlightedTile.get().equals(hc)) {
        this.drawTile(g2d, hc.q, hc.r, hexRadius, bounds, TILE_OUTLINE_COLOR, HIGHLIGHT_COLOR);


      } else {
        this.drawTile(g2d, hc.q, hc.r, hexRadius, bounds, TILE_OUTLINE_COLOR,
            TILE_BACKGROUND_COLOR);
      }
    }

    this.drawErrors();
  }

  /**
   * Draws an error on this panel. The drawn error will go away after the view is clicked on or
   * changed in any way.
   */
  private void drawErrors() {
    if (this.error.value1) {

      this.errorMessage = Optional.of(new JLabel(this.error.value2));
      JLabel label = errorMessage.get();
      label.setSize(this.bounds.width, this.bounds.height / 20);
      label.setHorizontalAlignment(JLabel.CENTER);
      label.setVerticalAlignment(JLabel.CENTER);
      label.setBackground(Color.RED);
      label.setOpaque(true);
      label.setLocation(0, this.bounds.height / 2 - (label.getHeight() / 2));

      this.add(errorMessage.get());
      this.error = new Pair<>(false, "");
    }

  }


  /**
   * Draws a hexagonal tile on the panel.
   *
   * @param g2d          The Graphics2D object to draw on.
   * @param q            The q-coordinate of the hexagon.
   * @param r            The r-coordinate of the hexagon.
   * @param hexRadius    The radius of the hexagon.
   * @param bounds       The bounding rectangle of the panel.
   * @param outlineColor The color of the hexagon outline.
   * @param fillColor    The color to fill the hexagon.
   */
  private void drawTile(Graphics2D g2d, int q, int r, double hexRadius, Rectangle bounds,
      Color outlineColor, Color fillColor) {
    double centerX =
        hexRadius * (Math.sqrt(3) * q + Math.sqrt(3) / 2.0 * r) + (double) bounds.width / 2;
    double centerY = hexRadius * (3. / 2. * r) + (double) bounds.height / 2;
    this.drawPointyTopHexagon(g2d, centerX, centerY, outlineColor, fillColor);
    Optional<Color> potentialDC = this.getDiscColor(q, r);
    potentialDC.ifPresent(
        color -> this.drawDisc(g2d, (int) centerX, (int) centerY, (int) hexRadius / 2, color));
  }


  /**
   * Gets the color of the disc at the specified hexagonal coordinates.
   *
   * @param q The q-coordinate of the hexagon.
   * @param r The r-coordinate of the hexagon.
   * @return An optional Color representing the color of the disc, or empty if the hexagon is
   * unoccupied.
   */
  private Optional<Color> getDiscColor(int q, int r) {
    PlayerOwnership playerOwnership = this.model.getBoard().getMap().get(new HexCoord(q, r, -q - r))
        .getPlayerOwnership();

    switch (playerOwnership) {
      case UNOCCUPIED:
        return Optional.empty();
      case PLAYER_1:
        return Optional.of(PLAYER_1_COLOR);
      case PLAYER_2:
        return Optional.of(PLAYER_2_COLOR);
      default:
        throw new RuntimeException("Not one of the enumerated ownerships");
    }
  }

  /**
   * Draws a pointy-top hexagon on the panel.
   *
   * @param g2d       The Graphics2D object to draw on.
   * @param centerX   The x-coordinate of the center of the hexagon.
   * @param centerY   The y-coordinate of the center of the hexagon.
   * @param outline   The color of the hexagon outline.
   * @param fillColor The color to fill the hexagon.
   */
  private void drawPointyTopHexagon(Graphics2D g2d, double centerX,
      double centerY,
      Color outline, Color fillColor) {

    int[] xPoints = new int[6];
    int[] yPoints = new int[6];

    for (int i = 0; i < 6; i++) {
      double angle = 2 * Math.PI / 6 * i + Math.PI / 6; // Adjust the angle
      xPoints[i] = (int) (centerX + hexRadius * Math.cos(angle));
      yPoints[i] = (int) (centerY + hexRadius * Math.sin(angle));
    }

    g2d.setColor(fillColor);
    g2d.fillPolygon(xPoints, yPoints, 6);

    g2d.setColor(outline); // Set the outline color
    BasicStroke thickStroke = new BasicStroke(
        2.0f); // Adjust the line width here (5.0f for a thicker line)
    g2d.setStroke(thickStroke); // Set the stroke width
    g2d.drawPolygon(xPoints, yPoints, 6); // Draw the thicker outline

  }

  /**
   * Draws a disc at the specified coordinates on the panel.
   *
   * @param g2d     The Graphics2D object to draw on.
   * @param xCenter The x-coordinate of the center of the disc.
   * @param yCenter The y-coordinate of the center of the disc.
   * @param radius  The radius of the disc.
   * @param color   The color of the disc.
   */
  private void drawDisc(Graphics2D g2d, int xCenter, int yCenter, int radius, Color color) {
    g2d.setColor(color);
    g2d.fillOval(xCenter - radius, yCenter - radius, radius * 2, radius * 2);
  }

  /**
   * Adds a listener for handling various view features related to the Reversi game.
   *
   * @param features the {@code ViewFeatures} object that defines the methods to be invoked when
   *                 specific user interactions occur.
   */
  @Override
  public void addFeatureListener(Features features) {
    this.featuresListeners.add(Objects.requireNonNull(features));
  }

  @Override
  public void alertErrorMessage(String message) {
    this.error = new Pair<>(true, message);
  }

  /**
   * A MouseInputAdapter for handling mouse events on the ReversiPanel.
   */
  private class MouseEventsListener extends MouseInputAdapter {

    /**
     * Invoked when a mouse button is pressed on the ReversiPanel.
     *
     * @param e The MouseEvent containing information about the event.
     */
    @Override
    public void mousePressed(MouseEvent e) {
      HexCoord potentialHC = PixelToHexConverter.convertPixelToHex(e.getX(), e.getY(), bounds,
          hexRadius);

      if (ReversiPanel.this.model.getBoard().getMap().get(potentialHC) == null) {
        ReversiPanel.this.highlightedTile = Optional.empty();
      } else {
        if (ReversiPanel.this.highlightedTile.isPresent()) {
          // case: we already a highlighted tile
          // if it is a different tile make sure to indicate highlight to the new hex coord
          if (ReversiPanel.this.highlightedTile.get().equals(potentialHC)) {
            // if it is the same tile make sure to deselect it change to Optional.empty
            ReversiPanel.this.highlightedTile = Optional.empty();
          } else {
            // if it is a different tile make sure to indicate highlight to the new hex coord
            ReversiPanel.this.highlightedTile = Optional.of(potentialHC);
          }
        } else {
          // case: we dont already have a highlighted tile
          ReversiPanel.this.highlightedTile = Optional.of(potentialHC);
        }
      }
      repaint();
    }
  }

  /**
   * A KeyListener for handling key events on the ReversiPanel.
   */
  private class KeyEventsListener implements KeyListener {

    /**
     * Invoked when a key is pressed on the ReversiPanel.
     *
     * @param e The KeyEvent containing information about the event.
     */
    @Override
    public void keyPressed(KeyEvent e) {

      int keyCode = e.getKeyCode();
      // Check the key code and take appropriate action
      if (keyCode == KeyEvent.VK_M) {
        System.out.println(1);
        // Handle the m key press
        for (Features features : ReversiPanel.this.featuresListeners) {
          features.attemptMove(ReversiPanel.this.highlightedTile);
        }
      } else if (keyCode == KeyEvent.VK_P) {
        // Handle the p key press
        for (Features features : ReversiPanel.this.featuresListeners) {
          features.attemptPass();
        }
      }

      repaint();
    }

    /**
     * Forced to be implemented, placeholder for now.
     *
     * @param e the event to be processed
     */
    @Override
    public void keyTyped(KeyEvent e) {
      /*
       * Only need keypressed.
       */
    }

    /**
     * Forced to be implemented, placeholder for now.
     *
     * @param e the event to be processed
     */
    @Override
    public void keyReleased(KeyEvent e) {
      /*
       * Only need keypressed.
       */
    }

  }

}

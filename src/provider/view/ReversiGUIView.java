package provider.view;

import java.awt.*;

import provider.model.ReversiReadOnlyModel;

import javax.swing.*;

/**
 * The ReversiGUIView class represents a graphical user interface (GUI) view for the Reversi game.
 * It extends {@code JFrame} and implements the {@code ReversiView} interface, providing methods to
 * display the game interface and add feature listeners for user interactions.
 */
public class ReversiGUIView extends JFrame implements ReversiView {

  /**
   * The Reversi panel that contains the graphical representation of the game board and handles UI.
   */
  private final ReversiPanel panel;

  /**
   * Constructs a {@code ReversiGUIView} with the specified Reversi model.
   *
   * @param model The ReversiReadOnlyModel representing the current state of the game.
   */
  public ReversiGUIView(ReversiReadOnlyModel model) {
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setTitle("Reversi");
    this.panel = new ReversiPanel(model);
    this.add(panel);
    this.pack();
  }


  /**
   * Displays or hides the GUI view.
   *
   * @param show {@code true} to display the GUI, {@code false} to hide it.
   */
  @Override
  public void display(boolean show) {
    this.setVisible(show);
  }

  /**
   * Adds a listener for view-specific user interface features. The provided ViewFeatures object
   * defines the set of features that the view can interact with, such as handling player input and
   * updating the display based on game state changes.
   *
   * @param features The {@code ViewFeatures} object representing the set of view-specific
   *                 features.
   */
  @Override
  public void addFeaturesListener(Features features) {
    this.panel.addFeatureListener(features);
  }

  /**
   * Alerts the view of a given error message. Tells the panel stored within to display this error
   * message.
   *
   * @param message the error message to be displayed.
   */
  @Override
  public void alertErrorMessage(String message) {
    this.panel.alertErrorMessage(message);
    this.repaint();
  }

  /**
   * Alerts this view that it is its turn. Tells the panel within to display a yellow background
   * indicating turn.
   */
  @Override
  public void alertTurn() {
    this.display(true);
    this.panel.setBackground(Color.YELLOW);
    this.repaint();
  }

  /**
   * Alerts this view that it is NOT its turn. Tells the panel within to display a mundane
   * background indicating turn.
   */
  @Override
  public void alertNotTurn() {
    this.display(true);
    this.panel.setBackground(new Color(128, 161, 138));
    this.repaint();
  }

  /**
   * Alerts this view that the game of reversi being played is over. If this is the case, the view
   * tells itself that it is no longer its turn.
   */
  @Override
  public void alertGameOver() {
    this.alertNotTurn();
  }
}

package provider.view;

/**
 * The {@code ReversiPanelView} interface represents a view for the Reversi game that is designed to
 * be displayed within a panel. Implementations of this interface are expected to provide methods
 * for adding feature listeners to handle user interactions.
 */

public interface ReversiPanelView {

  /**
   * Adds a listener for handling various view features related to the Reversi game.
   *
   * @param features the {@code ViewFeatures} object that defines the methods to be invoked when
   *                 specific user interactions occur.
   */
  void addFeatureListener(Features features);

  /**
   * Tells the view to display the given error message. This message is usually a propogation of
   * some error down the line.
   *
   * @param message the message to be displayed.
   */
  void alertErrorMessage(String message);

}

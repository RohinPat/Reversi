package view;

/**
 * The IFrame interface defines the contract for frames in our graphical user interface.
 * Frames are typically used to display various graphical components in a GUI application.
 */
public interface IFrame {

  /**
   * Gets the board panel associated with this frame.
   *
   * @return An implementation of the IBoardPanel interface representing the board panel.
   */
  IBoardPanel getBoardPanel();

  /**
   * Sets the visibility of the frame.
   *
   * @param isVisible True to make the frame visible, false to hide it.
   */
  void makeVisible(boolean isVisible);
}

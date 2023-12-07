package view;

import controller.ControllerFeatures;
import model.ReversiReadOnly;
import controller.FeaturesAdapter;
import provider.view.ReversiGUIView;

/**
 * A view adapter that connects a ReversiGUIView to the controller, enabling interaction
 * between the view and the game logic.
 */
public class ViewAdapter implements IBoardPanel {
  private final ReversiGUIView reversiGUIView;

  /**
   * Constructs a ViewAdapter using a given ReversiGUIView.
   *
   * @param reversiGUIView The ReversiGUIView to be adapted for interaction with the game.
   *                      controller.
   */
  public ViewAdapter(ReversiGUIView reversiGUIView) {
    this.reversiGUIView = reversiGUIView;
  }

  @Override
  public void setController(ControllerFeatures cont) {
    reversiGUIView.addFeaturesListener(new FeaturesAdapter(cont));
  }

  @Override
  public void initializeHexagons(ReversiReadOnly board) {
    this.reversiGUIView.display(true);
    this.reversiGUIView.repaint();
  }

  @Override
  public void handleHexagonClick(int mouseX, int mouseY) {
    // nothing
  }

  @Override
  public void showInvalidMoveDialog(String message) {
    this.reversiGUIView.alertErrorMessage(message);
  }

  public void setVisible(boolean b) {
    // nothing
  }
}

package view;

import controller.ControllerFeatures;
import model.ReversiReadOnly;
import controller.FeaturesAdapter;
import provider.view.ReversiGUIView;

public class ViewAdapter implements IBoardPanel {
  private final ReversiGUIView reversiGUIView;

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
    //
  }
}

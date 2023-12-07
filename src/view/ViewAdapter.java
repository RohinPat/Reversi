package view;

import java.util.Optional;

import controller.ControllerFeatures;
import model.Coordinate;
import model.Reversi;
import model.ReversiReadOnly;
import provider.model.HexCoord;
import provider.view.Features;
import provider.view.ReversiGUIView;
import provider.view.ReversiPanel;

public class ViewAdapter implements IBoardPanel, Features {
  private final ReversiGUIView reversiGUIView;

  public ViewAdapter (ReversiGUIView reversiGUIView){
    this.reversiGUIView = reversiGUIView;
  }

  @Override
  public void setController(ControllerFeatures cont) {

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
  public Coordinate getSelectedHexagon() {
    throw new UnsupportedOperationException("very bad 223");
  }

  @Override
  public void showInvalidMoveDialog(String message) {
    this.reversiGUIView.alertErrorMessage(message);
  }

  @Override
  public void attemptMove(Optional<HexCoord> hc) {
    //
  }

  @Override
  public void attemptPass() {
    //
  }

  public void setVisible(boolean b) {
    //
  }
}
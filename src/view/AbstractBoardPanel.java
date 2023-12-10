package view;

import javax.swing.*;

import controller.ControllerFeatures;
import model.ReversiReadOnly;

public abstract class AbstractBoardPanel extends JPanel implements IBoardPanel {
  @Override
  public void setController(ControllerFeatures cont) {

  }

  @Override
  public void initializeBoard(ReversiReadOnly board) {

  }

  @Override
  public void handleMouseClick(int mouseX, int mouseY) {

  }

  @Override
  public void showInvalidMoveDialog(String message) {

  }
}

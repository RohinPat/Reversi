package view;

import javax.swing.*;

public interface IFrame {
  IBoardPanel getBoardPanel();

  void makeVisible(boolean bool);
}

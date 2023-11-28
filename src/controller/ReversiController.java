package controller;

import view.BoardPanel;
import model.Board;
import model.Coordinate;
import model.Disc;

public class ReversiController implements ControllerFeatures {
  private Board model;
  private BoardPanel view;
  private Player player;

  public ReversiController(Board model, BoardPanel view, Player player) {
    this.model = model;
    this.view = view;
    this.player = player;
    this.view.setController(this);

  }

  @Override
  public void selectHexagon(int q, int r) {
    // Logic when a hexagon is selected
    System.out.println("Hexagon selected at (" + q + ", " + r + ")");
    // You might want to highlight the selected hexagon in the view, for example
  }

  @Override
  public void confirmMove() {
    if (player.isPlayerTurn(model)) {
      Coordinate selectedHex = view.getSelectedHexagon();
      if (selectedHex != null && model.isCellEmpty(selectedHex.getQ(), selectedHex.getR())) {
        try {
          player.makeAMove(model, selectedHex);
          view.initializeHexagons(model); // Update the view to reflect the new board state
          System.out.println("Move made to (" + selectedHex.getQ() + ", " + selectedHex.getR() + ")");
        } catch (IllegalArgumentException e) {
          view.showInvalidMoveDialog(e.getMessage());
        }
      }
    }
    else{
      view.showInvalidMoveDialog("Not your turn right now");
    }
  }

  @Override
  public void passTurn() {
    // Logic for passing a turn
    model.passTurn();
    view.initializeHexagons(model); // Update the view if necessary
    System.out.println("Turn passed");
  }

  public void updateView(){
    view.initializeHexagons(model);
  }


  // Additional methods for future functionalities can be added here
}
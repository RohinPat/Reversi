package controller;

import view.BoardPanel;
import model.Board;
import model.Coordinate;
import model.Disc;

public class ReversiController implements ControllerFeatures {
  private Board model;
  private BoardPanel view;

  public ReversiController(Board model, BoardPanel view) {
    this.model = model;
    this.view = view;
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
    // Logic for confirming a move
    // Assuming you have a way to track the currently selected hexagon
    Coordinate selectedHex = view.getSelectedHexagon();
    if (selectedHex != null && model.isCellEmpty(selectedHex.getQ(), selectedHex.getR())) {
      try {
        model.makeMove(selectedHex);
        view.initializeHexagons(model); // Update the view to reflect the new board state
        System.out.println("Move made to (" + selectedHex.getQ() + ", " + selectedHex.getR() + ")");
      } catch (IllegalArgumentException e) {
        System.out.println("Invalid move: " + e.getMessage());
        // Handle invalid move, perhaps by displaying an error message in the view
      }
    }
  }

  @Override
  public void passTurn() {
    // Logic for passing a turn
    model.passTurn();
    view.initializeHexagons(model); // Update the view if necessary
    System.out.println("Turn passed");
  }

  // Additional methods for future functionalities can be added here
}
